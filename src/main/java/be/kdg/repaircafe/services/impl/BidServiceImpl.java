package be.kdg.repaircafe.services.impl;

import be.kdg.repaircafe.dom.repairs.*;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.dom.users.roles.Repairer;
import be.kdg.repaircafe.dom.users.roles.Role;
import be.kdg.repaircafe.persistence.api.BidRepository;
import be.kdg.repaircafe.persistence.api.RepairRepository;
import be.kdg.repaircafe.persistence.api.UserRepository;
import be.kdg.repaircafe.services.api.BidService;
import be.kdg.repaircafe.services.exceptions.BidServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("bidService")
@Transactional
public class BidServiceImpl implements BidService
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-service-layer/

    private final UserRepository userRepository;
    private final RepairRepository repairRepository;
    private final BidRepository bidRepository;

    @Autowired
    public BidServiceImpl(final UserRepository userRepository,
                          final RepairRepository repairRepository,
                          final BidRepository bidRepository)
    {
        this.userRepository = userRepository;
        this.repairRepository = repairRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public Bid findBidById(Integer bidId)
    {
        return bidRepository.findOne(bidId);
    }

    /**
     * Return all bids for a certain repair. Utility function that can be used
     * by clients to get bids for a particular repair.
     *
     * @param repairId id of repair for which bids are requested
     * @return bids that are attached to this repair
     */
    @Override
    public List<Bid> findBidsByRepair(final Integer repairId)
    {
        Repair repair = repairRepository.findOne(repairId);
        return repair.getBids();
    }

    /**
     * Get all bids for this repairer.
     *
     * @param userId id of of repairer
     * @return all bids offered by this repairer
     */
    @Override
    public List<Bid> findBidsByUser(final Integer userId)
    {
        User user = userRepository.findOne(userId);
        if (user == null)
            throw new BidServiceException("User not found");

        Repairer repairer = Role.loadRole(user, Repairer.class);
        if (repairer == null)
            throw new BidServiceException("User is not a repairer");

        return repairer.getBids();
    }

    @Override
    public Bid saveBid(final Bid bid)
    {
        Bid b = bidRepository.save(bid);
        if (b == null)
            throw new BidServiceException("Bid not saved");

        return b;
    }

    /**
     * A repairer can place a bid on a repair.
     * <p/>
     * The type of bid is determined based on the repair and an according Bid is
     * created. All bidirectional associations between Bid-Repairer-Repair are
     * being taken care of.
     *
     * @param userId   Id of Repairer that places the bid
     * @param repairId Id of Repair for which a bid is submitted
     * @param price    Bid price
     * @return true if bid is successfully placed on repair
     */
    @Override
    public Integer placeBid(final Integer userId, final Integer repairId, final double price)
    {
        User user = userRepository.findOne(userId);
        if (user == null)
            throw new BidServiceException("User not found");

        Repairer repairer = Role.loadRole(user, Repairer.class);

        if (repairer == null)
            throw new BidServiceException("User is not a repairer");

        Repair repair = repairRepository.findOne(repairId);
        if (repair == null)
            throw new BidServiceException("Repair not found");

        Bid bid;
        // check if repair hasn't got assigned between showing repair and placing bid
        if (!repair.getDetails().isAssigned())
        {
            switch (repair.getDetails().getPriceModel())
            {
                case FIXED:
                    bid = new FixedBid(price);
                    break;
                default:
                    bid = new PerhourBid(price);
                    break;
            }
            bid.setRepairer(repairer);
            repairer.addBid(bid);
            repair.addBid(bid);
            bid.setRepair(repair);

            bidRepository.save(bid);
            repairRepository.save(repair);
            return bid.getId();
        }
        return -1;
    }


    /**
     * Client can accept a certain bid. If a bid is accepted. All other bids for
     * this repair are no longer eligible for acceptance. The bid will be marked
     * accepted.
     * <p/>
     * The repair will be set as "assigned to" the Repairer. The repair will be
     * added to the repairers assigned repair list.
     *
     * @param bidId Id of the bid that is accepted.
     */
    @Override
    public void acceptBid(final Integer bidId)
    {
        Bid acceptedBid = bidRepository.findOne(bidId);

        if (acceptedBid == null)
            throw new BidServiceException("Bid not found");

        Repair repair = acceptedBid.getRepair();
        Repairer acceptedRepairer = acceptedBid.getRepairer();

        for (Bid bid : repair.getBids())
        {
            // not really necessary
            bid.setAccepted(false);
            bid.setEligible(false);
        }
        // mark repair as "assigned to"
        repair.getDetails().setAssigned(true);
        // set repair's repairer
        repair.setRepairer(acceptedRepairer);
        // add assigned repair to repairer's assigned repair list
        acceptedRepairer.assignToRepairer(repair);
        // mark bid as accepted
        acceptedBid.setAccepted(true);
        repairRepository.save(repair);
    }

    /**
     * Clear accepted bid.
     * <p/>
     * This method reverse all the effects of accepting a bid.
     *
     * @param bidId Id of the bid that needs to be cleared
     */
    @Override
    public void clearBid(final Integer bidId)
    {
        Bid acceptedBid = bidRepository.findOne(bidId);

        if (acceptedBid == null)
            throw new BidServiceException("Bid not found");

        // get associated repair
        Repair repair = acceptedBid.getRepair();
        RepairDetails repairDetails = repair.getDetails();
        Repairer acceptedRepairer = acceptedBid.getRepairer();

        // reset all other bids in repair to eligible and not accepted
        // including this bid
        for (Bid bid : repair.getBids())
        {
            bid.setAccepted(false);
            bid.setEligible(true);
        }

        // remove repair from repairers assigned repair list
        acceptedRepairer.unassignFromRepaier(repair);
        // reset repairer assocation
        repair.setRepairer(null);
        // reset "assigned to"
        repairDetails.setAssigned(false);
        repairRepository.save(repair);
    }

    /**
     * Remove bid from the system.
     * <p/>
     * This will remove the bid from system and from all associated objects.
     * If the bid to be removed was already accepted. All other bids will be
     * reset to eligible and the repair will be removed from the repairers
     * assigned repair list.
     *
     * @param bidId id of the bid that needs to be removed
     */
    @Override
    public void removeBid(final Integer bidId)
    {
        Bid removeBid = bidRepository.findOne(bidId);
        if (removeBid == null)
            throw new BidServiceException("Bid not found");

        Repair repair = removeBid.getRepair();
        Repairer repairer = removeBid.getRepairer();

        // if this bid was already accepted
        // reset all other bids so the become eligible again
        if (removeBid.isAccepted())
        {
            // since this bid was accepted the associated repair
            // was on the repairers assigned list and thus
            // we need to remove it.
            repairer.removeRepair(repair);
            // reset all other bids in repair to eligible and not accepted
            // including this bid
            for (Bid bid : repair.getBids())
            {
                bid.setAccepted(false);
                bid.setEligible(true);
            }
        }
        // finally remove from the respective bid list
        // TODO: write remove methods for bids
        repairer.getBids().remove(removeBid);
        repair.getBids().remove(removeBid);
        repairRepository.save(repair);
    }
}

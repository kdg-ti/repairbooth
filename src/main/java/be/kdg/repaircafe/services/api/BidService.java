package be.kdg.repaircafe.services.api;


import be.kdg.repaircafe.dom.repairs.Bid;

import java.util.List;

public interface BidService {
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-service-layer/

    Bid findBidById(Integer bidId);

    List<Bid> findBidsByRepair(Integer repairId);

    List<Bid> findBidsByUser(Integer userId);

    Bid saveBid(Bid bid);

    Integer placeBid(Integer userId, Integer repairId, double price);

    void acceptBid(Integer bidId);

    void clearBid(Integer bidId);

    void removeBid(Integer bidId);
}

package be.kdg.repaircafe.dom.users.roles;

import be.kdg.repaircafe.dom.repairs.Bid;
import be.kdg.repaircafe.dom.repairs.Repair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Repairer searches repairs he/she can mend. A repairer places bids on
 * repairs
 *
 * @author wouter
 */
@Entity
@DiscriminatorValue("ROLE_REPAIRER")
public class Repairer extends Role {

    @Column(length = 10)
    private double overalRating;

    @Column
    private boolean rated = false;

    @Column
    private String degree;

    @OneToMany(targetEntity = Repair.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "repairer")
    private List<Repair> assignedRepairs;

    @OneToMany(targetEntity = Bid.class, mappedBy = "repairer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bid> bids;

    public Repairer(String degree) {
        this();
        this.degree = degree;
    }

    public Repairer() {
        this.bids = new ArrayList<>();
        this.assignedRepairs = new ArrayList<>();
    }

    /**
     * Rating is calculated based on ratings of past repairs done by this repairer
     *
     * @return
     * @{link Repairer#rate}
     */
    public int getOveralRating() {
        return (int) Math.round(overalRating);
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public List<Bid> getBids() {
        return bids;
    }

    /**
     * Every call to this method will update the overall rating of a repairer. Averaging out all ratings.
     *
     * @param rating
     */
    public void rate(int rating) {
        if (rating >= 0) {
            if (!rated) {
                overalRating = rating;
                rated = true;
            } else {
                overalRating = (overalRating + rating) / 2.0;
            }
        }
    }

    /**
     * Add repair to this user's list
     *
     * @param repair
     */
    public void assignToRepairer(Repair repair) {
        this.assignedRepairs.add(repair);
    }

    public void unassignFromRepaier(Repair repair) {
        this.assignedRepairs.remove(repair);
    }

    /**
     * Removing a repair for a repairer also removes associated bid(s) from his bid list.
     *
     * @param repair
     */
    public void removeRepair(Repair repair) {
        if (assignedRepairs.contains(repair)) {
            List<Bid> allBids = repair.getBids();

            // find intersection between repairers bid and bids of repair
            List<Bid> intersection = allBids.stream()
                    .filter(b -> bids.contains(b))
                    .collect(Collectors.toList());

            // delete all found bids from my bids
            intersection.stream()
                    .forEach(bid -> bids.remove(bid));

            // remove repair from assigned list
            assignedRepairs.remove(repair);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_REPAIRER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        return authorities;
    }

    @Override
    public RoleType getRoleType() {
        return RoleType.ROLE_REPAIRER;
    }

}

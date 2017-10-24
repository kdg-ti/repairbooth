package be.kdg.repaircafe.dom.repairs;


import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.dom.users.roles.Repairer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A repairer can bid on a repair. Depending on the choice of the customer the
 * bid will take the form of fixed bid or an per hour bid. Only one bid can be
 * submitted on a particular repair.
 * <p/>
 * Once a bid is accepted by the Client no more bid can be submitted.
 *
 * @author wouter
 * @see Client
 * @see Repairer
 * @see Repair
 */
@Entity
@DiscriminatorColumn(name = "Discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Bid")
public abstract class Bid implements Comparable<Bid>, Serializable {

    @Column(nullable = false)
    @Id
    @GeneratedValue
    private Integer bidId;

    @ManyToOne(targetEntity = Repair.class)
    @JoinColumn(name = "Repair_Id")
    private Repair repair;

    @ManyToOne(targetEntity = Repairer.class)
    @JoinColumn(name = "Repairer_Id")
    private Repairer repairer;

    @Column(nullable = false, length = 10)
    private double price;

    @Column
    private String comment;

    @Column(nullable = false)
    private boolean accepted = false;

    @Column(nullable = false)
    private boolean eligible = true;

    @Column
    private LocalDateTime timestamp;

    public Bid() {
    }

    public Bid(final double price) {
        this.price = price;
        timestamp = LocalDateTime.now();
    }

    public Integer getId() {
        return bidId;
    }

    /**
     * Return bid price.
     * This prices is either the fixed price or the price per hour
     *
     * @return bid price
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Repairer getRepairer() {
        return repairer;
    }

    /**
     * Set repairer that placed this bid.
     *
     * @param repairer
     */
    public void setRepairer(final Repairer repairer) {
        this.repairer = repairer;
    }

    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Mark bid as accepted
     *
     * @param value
     */
    public void setAccepted(final boolean value) {
        this.accepted = value;
    }

    /**
     * Is this bid still in the running
     *
     * @return bid eligibility
     */
    public boolean isEligible() {
        return eligible;
    }

    /**
     * Set eligibility of bid
     *
     * @param eligible
     */
    public void setEligible(final boolean eligible) {
        this.eligible = eligible;
    }

    public Repair getRepair() {
        return repair;
    }

    /**
     * Associate with repair
     *
     * @param repair
     */
    public void setRepair(final Repair repair) {
        this.repair = repair;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(final Integer bidId) {
        this.bidId = bidId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(final Bid o) {
        return (int) ((int) price - o.price);
    }

    /**
     * Returns bid type: Fixed or Per Hour
     *
     * @return bid type
     */
    public String getType() {
        return "Bid";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.bidId;
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bid other = (Bid) obj;
        return this.bidId.equals(other.bidId);
    }

    @Override
    public String toString() {
        return "Bid{" + timestamp.toString() + ", accepted=" + accepted + ", by user=" + repairer + ", for price=" + price + '}';
    }
}

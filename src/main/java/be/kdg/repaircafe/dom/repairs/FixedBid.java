package be.kdg.repaircafe.dom.repairs;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author wouter
 */
@Entity
@DiscriminatorValue("FixedBid")
public class FixedBid extends Bid implements Serializable {
    public FixedBid() {
    }

    /**
     * Can be used when a repair only accepts fixed price bids
     *
     * @param price
     */
    public FixedBid(double price) {
        super(price);
    }

    @Override
    public String getType() {
        return "Fixed Bid";
    }

}

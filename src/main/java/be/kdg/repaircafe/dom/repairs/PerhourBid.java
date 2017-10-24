package be.kdg.repaircafe.dom.repairs;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

/**
 * Represents a bid in accordance with a per hour price model.
 *
 * @author wouter
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PerhourBid")
public class PerhourBid extends Bid implements Serializable {

    public PerhourBid() {
    }

    /**
     * Bid with per hour  price.    
     * If the customer creates a per hour priced repair only this type of offer can be made.
     *
     * @param price
     */
    public PerhourBid(double price) {
        super(price);
    }

    @Override
    public String getType() {
        return "Per Hour";
    }

}

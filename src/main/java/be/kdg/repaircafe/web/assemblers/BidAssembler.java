package be.kdg.repaircafe.web.assemblers;

import be.kdg.repaircafe.dom.repairs.Bid;
import be.kdg.repaircafe.web.resources.repairs.BidResource;
import org.springframework.stereotype.Component;

@Component
public class BidAssembler extends Assembler<Bid, BidResource> {

    public BidAssembler() {
        super(Bid.class, BidResource.class);
    }
}

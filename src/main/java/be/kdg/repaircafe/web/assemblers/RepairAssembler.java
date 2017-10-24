package be.kdg.repaircafe.web.assemblers;

import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.web.resources.repairs.RepairResource;
import org.springframework.stereotype.Component;

@Component
public class RepairAssembler extends Assembler<Repair, RepairResource> {

    public RepairAssembler() {
        super(Repair.class, RepairResource.class);
    }
}

package be.kdg.repaircafe.config.orika.custom.mappers;

import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.web.resources.repairs.RepairResource;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class RepairMapper extends CustomMapper<Repair, RepairResource> {
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/orika/

    @Override
    public void mapAtoB(Repair source, RepairResource destination, MappingContext context) {
        mapperFacade.map(source.getItem(), destination.getItemResource());
        mapperFacade.map(source.getDetails(), destination.getRepairDetailsResource());
    }

    @Override
    public void mapBtoA(RepairResource source, Repair destination, MappingContext context) {
        mapperFacade.map(source.getItemResource(), destination.getItem());
        mapperFacade.map(source.getRepairDetailsResource(), destination.getDetails());
    }
}

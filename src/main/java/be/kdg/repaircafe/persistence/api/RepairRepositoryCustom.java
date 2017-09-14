package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.users.roles.Client;

import java.util.List;

/**
 * Extra interface to support customization of Spring's Data interfaces
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
 */
public interface RepairRepositoryCustom {

    List<Repair> getRepairsByClient(Client client);
}

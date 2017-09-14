package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.repairs.Repair;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface to trigger proxy creation of CRUD Repository
 *
 * @author wouter
 */
public interface RepairRepository extends JpaRepository<Repair, Integer>, RepairRepositoryCustom{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
}

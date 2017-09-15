package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.repairs.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface to trigger proxy creation of CRUD Repository
 * <p>
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
 *
 * @author wouter
 */
public interface RepairRepository extends JpaRepository<Repair, Integer>, RepairRepositoryCustom {

    @Query(value = "SELECT distinct r.item.category FROM Repair r")
    List<String> getAllCategories();

    @Query(value = "select distinct r.details.defect from Repair r")
    List<String> getAllDefects();

    @Query(value = "select distinct r.item.brand from Repair r")
    List<String> getAllBrands();
}

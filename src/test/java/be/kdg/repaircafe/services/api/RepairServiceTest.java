package be.kdg.repaircafe.services.api;

import be.kdg.repaircafe.dom.repairs.Item;
import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.repairs.RepairDetails;
import be.kdg.repaircafe.services.exceptions.RepairServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RepairServiceTest {
    @Autowired
    private RepairService repairService;

    @Test
    public void testFindRepairByID() throws Exception {
        Repair repair = repairService.findRepairById(1);
        assertThat(repair, is(notNullValue()));
    }

    @Test
    public void testFindRepairsByUserId() throws Exception {
        List<Repair> repairs = repairService.findRepairsByUserId(1);
        assertThat(repairs, is(not(empty())));
    }

    @Test
    public void testAddRepair() throws Exception {
        Item item = new Item("Leica Disto", "Leico", "Lasermeter");
        RepairDetails repairDetails = new RepairDetails("Elektrisch", "Laserstraal werkt niet meer", RepairDetails.PriceModel.FIXED, LocalDateTime.now().plusWeeks(1));
        Repair repair = new Repair(item, repairDetails);
        Repair r = repairService.saveRepair(1, repair);
        Repair persistedRepair = repairService.findRepairById(r.getId());
        assertThat(persistedRepair.getId(), equalTo(r.getId()));
    }

    @Test
    public void testUpdateRepair() throws Exception {
        String evaluation = "Well done";
        Repair repair = repairService.findRepairById(1);
        RepairDetails rd = repair.getDetails();
        rd.setEvaluation(evaluation);
        Repair persistedRepair = repairService.updateRepair(repair);
        assertThat(persistedRepair.getDetails().getEvaluation(), equalTo(evaluation));
    }

    @Test(expected = RepairServiceException.class)
    public void testDeleteRepair() throws Exception {
        repairService.deleteRepair(1);
        repairService.findRepairById(1);
    }
}
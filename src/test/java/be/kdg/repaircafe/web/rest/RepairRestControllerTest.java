package be.kdg.repaircafe.web.rest;

import be.kdg.repaircafe.dom.repairs.Item;
import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.repairs.RepairDetails;
import be.kdg.repaircafe.services.api.RepairService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RepairRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairService repairService;

    @Test
    @WithUserDetails("peter.jackson@hotmail.com")
    public void getRepairByRepairId() throws Exception {
        // mock evil repair
        int repairId = 666;
        Item item = new Item("Trident", "Firestone", "Torture Stuff");
        RepairDetails repairDetails = new RepairDetails("Tooth broke off", "Tooth broke off while using intensively", RepairDetails.PriceModel.FIXED, LocalDateTime.MAX);
        Repair repair = new Repair(item, repairDetails);
        repair.setRepairId(repairId);

        given(repairService.findRepairById(repairId)).willReturn(repair);

        mockMvc.perform(get("/api/repairs/" + repairId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("repairId", is(repair.getId())))
                .andExpect(jsonPath("itemResource.productName", is(repair.getItem().getProductName())))
                .andExpect(jsonPath("repairDetailsResource.defect", is(repair.getDetails().getDefect())));

    }
}
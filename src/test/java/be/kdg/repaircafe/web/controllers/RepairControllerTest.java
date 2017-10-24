package be.kdg.repaircafe.web.controllers;

import be.kdg.repaircafe.web.resources.repairs.RepairResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RepairControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void saveRepair() throws Exception {
        RepairResource repairResource = new RepairResource();
        repairResource.getItemResource().setProductName("Leico");
        repairResource.getRepairDetailsResource().setDescription("Werkt niet meer");

        String productName = repairResource.getItemResource().getProductName();
        String description = repairResource.getRepairDetailsResource().getDescription();

        mockMvc.perform(post("/saverepair.do")
                .param("itemResource.productName", productName)
                .param("repairDetailsResource.description", description))
                .andExpect(status().isOk())
                .andExpect(view().name("client/repaircreated"))
                .andExpect(model().attribute("repairResource", hasProperty("repairId", is(not(empty())))))
                .andExpect(model().attribute("repairResource", hasProperty("itemResource", hasProperty("productName", is(productName)))))
                .andExpect(model().attribute("repairResource", hasProperty("repairDetailsResource", hasProperty("description", is(description))))
                );

    }
}
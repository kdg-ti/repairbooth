package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.repairs.Repair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepairRepositoryTest {

    @Autowired
    private RepairRepository repairRepository;

    @Test
    public void findNonExistingRepair() {
        Repair repair = repairRepository.findOne(13);
        assertThat(repair, is(nullValue()));
    }
}
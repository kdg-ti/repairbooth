package be.kdg.repaircafe.services.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationServiceTest {
    @Autowired
    private InformationService informationService;

    @Test
    public void testGetAllCategories() throws Exception {
        assertThat(informationService.getAllCategories(), is(not(empty())));
    }

    @Test
    public void testGetAllBrands() throws Exception {
        assertThat(informationService.getAllBrands(), is(not(empty())));
    }

    @Test
    public void testGetAllDefects() throws Exception {
        assertThat(informationService.getAllDefects(), is(not(empty())));
    }
}
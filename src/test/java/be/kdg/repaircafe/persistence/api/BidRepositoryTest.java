package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.repairs.Bid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidRepositoryTest {

    @Autowired
    private BidRepository bidRepository;

    @Test
    public void checkBidsForRepair() {
        List<Bid> bids = bidRepository.findAll();
        assertThat(bids.size(), is(equalTo(3)));
    }

}
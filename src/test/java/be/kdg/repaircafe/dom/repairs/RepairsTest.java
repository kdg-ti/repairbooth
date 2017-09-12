package be.kdg.repaircafe.dom.repairs;

import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.dom.users.roles.Repairer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

/*
    Regular JUnit 4 tests
*/
public class RepairsTest {

    // this objects are mocked
    private Repairer repairer;
    private Client client;
    private Item item;
    private RepairDetails repairDetails;

    @Before
    public void beforeEachTest() {
        repairer = mock(Repairer.class);
        client = mock(Client.class);
        item = mock(Item.class);
        repairDetails = mock(RepairDetails.class);
    }

    @Test
    public void createRepair() {
        Repair repair = new Repair(item, repairDetails);
        repair.setClient(client);
        repair.setRepairer(repairer);
        assertThat(repair.getDetails(), equalTo(repairDetails));
        assertThat(repair.getRepairer(), is(equalTo(repairer)));
        assertThat(repair.getClient(), is(equalTo(client)));
    }

    @Test
    public void addBidToRepair() {
        Repair repair = new Repair(item, repairDetails);
        Bid bid = new PerhourBid(10);
        // manually set bid id for object comparison in equals method
        bid.setBidId(1);
        repair.addBid(bid);
        assertThat(repair.getBids(), hasItem(bid));
    }
}
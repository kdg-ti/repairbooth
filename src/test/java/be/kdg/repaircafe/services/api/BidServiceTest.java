package be.kdg.repaircafe.services.api;

import be.kdg.repaircafe.dom.repairs.Bid;
import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.dom.users.roles.Repairer;
import be.kdg.repaircafe.dom.users.roles.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BidServiceTest {
    @Autowired
    private BidService bidService;

    @Autowired
    private UserService userService;

    @Test
    public void testPlaceBid() throws Exception {
        List<User> users = userService.findUsersByRole(Repairer.class);
        Repairer repairer = Role.loadRole(users.get(1), Repairer.class);
        Integer bidId = bidService.placeBid(users.get(1).getUserId(), 1, 200);
        if (bidId != -1) {
            Bid bid = bidService.findBidById(bidId);
            List<Bid> usersBids = bidService.findBidsByUser(users.get(1).getUserId());
            assertThat(usersBids, hasItem(bid));
            assertThat(repairer.getBids(), hasItem(bid));
        }
    }

    @Test
    public void testAcceptBid() throws Exception {
        // Load all clients and select first client from list. From this select first repair
        List<User> clients = userService.findUsersByRole(Client.class);
        Client client = Role.loadRole(clients.get(0), Client.class);
        Repair repair = client.getRepairs().get(0);

        double bidPrice = 200.0;
        // Load all repairers and select first repairer from list.
        List<User> repairers = userService.findUsersByRole(Repairer.class);
        Repairer repairer = Role.loadRole(repairers.get(1), Repairer.class);
        Integer bidId = bidService.placeBid(repairers.get(1).getUserId(), repair.getId(), bidPrice);

        bidService.acceptBid(bidId);
        Bid bid = bidService.findBidById(bidId);
        assertThat(bid.isAccepted(), is(true));
    }

    @Test
    public void testClearBid() throws Exception {
        // Load all clients and select first client from list. From this select first repair
        List<User> clients = userService.findUsersByRole(Client.class);
        Client client = Role.loadRole(clients.get(0), Client.class);
        Repair repair = client.getRepairs().get(0);

        double bidPrice = 200.0;
        // Load all repairers and select first repairer from list.
        List<User> repairers = userService.findUsersByRole(Repairer.class);
        Repairer repairer = Role.loadRole(repairers.get(1), Repairer.class);
        Integer bidId = bidService.placeBid(repairers.get(1).getUserId(), repair.getId(), bidPrice);

        bidService.acceptBid(bidId);
        bidService.clearBid(bidId);
        Bid bid = bidService.findBidById(bidId);
        assertThat(bid.isAccepted(), is(false));
    }

    @Test
    public void testRemoveBid() throws Exception {
        // Load all clients and select first client from list. From this select first repair
        List<User> clients = userService.findUsersByRole(Client.class);
        Client client = Role.loadRole(clients.get(0), Client.class);
        Repair repair = client.getRepairs().get(0);

        double bidPrice = 200.0;
        // Load all repairers and select first repairer from list.
        List<User> repairers = userService.findUsersByRole(Repairer.class);
        Repairer repairer = Role.loadRole(repairers.get(1), Repairer.class);
        Integer bidId = bidService.placeBid(repairers.get(1).getUserId(), repair.getId(), bidPrice);
        Bid bid = bidService.findBidById(bidId);

        bidService.removeBid(bidId);
        assertThat(repair.getBids(), not(hasItem(bid)));
    }

    @Test
    public void testFindBidsByRepair() throws Exception {
        // Load all clients and select first client from list. From this select first repair
        List<User> clients = userService.findUsersByRole(Client.class);
        Client client = Role.loadRole(clients.get(0), Client.class);
        Repair repair = client.getRepairs().get(0);

        double bidPrice = 200.0;
        // Load all repairers and select first repairer from list.
        List<User> repairers = userService.findUsersByRole(Repairer.class);
        Repairer repairer = Role.loadRole(repairers.get(1), Repairer.class);
        Integer bidId = bidService.placeBid(repairers.get(1).getUserId(), repair.getId(), bidPrice);
        Bid bid = bidService.findBidById(bidId);

        List<Bid> bidsByRepair = bidService.findBidsByRepair(repair.getId());
        assertThat(bidsByRepair, hasItem(bid));
    }

    @Test
    public void testFindBidsByUser() throws Exception {
        List<User> clients = userService.findUsersByRole(Client.class);
        Client client = Role.loadRole(clients.get(0), Client.class);
        Repair repair = client.getRepairs().get(0);

        double bidPrice = 200.0;
        // Load all repairers and select first repairer from list.
        List<User> repairers = userService.findUsersByRole(Repairer.class);
        Repairer repairer = Role.loadRole(repairers.get(1), Repairer.class);
        Integer bidId = bidService.placeBid(repairers.get(1).getUserId(), repair.getId(), bidPrice);
        Bid bid = bidService.findBidById(bidId);

        List<Bid> bidsByUser = bidService.findBidsByUser(repairers.get(1).getUserId());
        assertThat(bidsByUser, hasItem(bid));
    }
}
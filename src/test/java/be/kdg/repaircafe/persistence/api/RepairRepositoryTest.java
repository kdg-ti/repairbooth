package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.dom.users.roles.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepairRepositoryTest {

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findNonExistingRepair() {
        Repair repair = repairRepository.findOne(13);
        assertThat(repair, is(nullValue()));
    }

    @Test
    public void findRepairsByClient() {

        // Find all users with role client and select the first one
        Optional<User> user = userRepository.findUsersByRole(Client.class).stream().findFirst();
        List<Repair> repairsByClient = null;
        // 'Cast' User object to Client
        if (Role.hasRole(user.get(), Client.class)) {
            Client client = Role.loadRole(user.get(), Client.class);
            repairsByClient = repairRepository.getRepairsByClient(client);
        }
        assertThat(repairsByClient, is(not(empty())));
    }
}
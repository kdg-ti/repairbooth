package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.dom.users.roles.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.isA;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserWithId() {
        User user = userRepository.findOne(1);
        assertThat(user.getUserId(), is(equalTo(1)));
    }

    @Test
    public void findUsersByRole() {
        List<User> usersByRole = userRepository.findUsersByRole(Client.class);
        usersByRole.forEach(u -> assertThat(u.getRoles(), hasItem(isA(Client.class))));
    }
}
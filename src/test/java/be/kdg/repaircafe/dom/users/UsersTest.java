package be.kdg.repaircafe.dom.users;

import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.dom.users.roles.Repairer;
import be.kdg.repaircafe.dom.users.roles.Role;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

/*
    Regular JUnit 4 tests
*/
public class UsersTest {

    private Person person;
    private Address address;
    private Repairer repairer;
    private Client client;

    @Before
    public void runBeforeEachTest() {
        person = mock(Person.class);
        address = mock(Address.class);
        repairer = mock(Repairer.class);
        client = mock(Client.class);
    }

    @Test
    public void createUserWithRolesClientAndRepairer() {
        String password = "piano123";

        // create two identities for the new user
        repairer.setDegree("Master");
        List<Role> identities = new ArrayList<>();
        identities.add(repairer);
        identities.add(client);

        User user = new User(person, "wouter.deketelaere@kdg.be", password, identities);
        assertThat(person, equalTo(user.getPerson()));
        assertThat(user.getRoles(), hasItem(isA(Repairer.class)));
        assertThat(user.getRoles(), hasItem(isA(Client.class)));
    }
}
package be.kdg.repaircafe.dom.users;

import be.kdg.repaircafe.dom.users.roles.Role;

import java.io.Serializable;
import java.util.List;


/**
 * Abstract class representing a user of the system. Every user is associated
 * with a Person. Every user has to have a unique username. This username takes
 * the form of an email address.
 *
 * @author wouter
 */
public class User implements Serializable {
    private Integer userId;

    private String username = null;

    private String encryptedPassword;

    private Person person;

    private List<Role> roles;

    public User() {
        this.person = new Person();
    }

    public User(Person person, String username, String encryptedPassword, List<Role> roles) {
        this.person = person;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.roles = roles;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int result = person != null ? person.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (encryptedPassword != null ? encryptedPassword.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (person != null ? !person.equals(user.person) : user.person != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (encryptedPassword != null ? !encryptedPassword.equals(user.encryptedPassword) : user.encryptedPassword != null)
            return false;
        return roles == user.roles;

    }

    @Override
    public String toString() {
        return "User{" + userId + ", " + username + ", " + encryptedPassword + '}';
    }
}

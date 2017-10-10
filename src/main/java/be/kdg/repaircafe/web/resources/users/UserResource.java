package be.kdg.repaircafe.web.resources.users;

import be.kdg.repaircafe.dom.users.roles.Role;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;
import java.util.List;

/**
 * UserResource is used to send User information over the wire.
 */
public class UserResource implements Serializable {
    // User properties
    private Integer userId;

    @Email(message = "Username must be a valid email address")
    private String username;

    private String password;

    private String oldPassword;

    private List<Role.RoleType> roleTypes;

    // Person and Address properties
    private PersonResource personResource;

    public UserResource() {
        this.personResource = new PersonResource();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public List<Role.RoleType> getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(List<Role.RoleType> roleTypes) {
        this.roleTypes = roleTypes;
    }

    public PersonResource getPersonResource() {
        return personResource;
    }

    public void setPersonResource(PersonResource personResource) {
        this.personResource = personResource;
    }

}

package be.kdg.repaircafe.dom.users.roles;

import be.kdg.repaircafe.dom.exceptions.UserException;
import be.kdg.repaircafe.dom.users.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@DiscriminatorColumn(name = "RoleType", discriminatorType = DiscriminatorType.STRING)
public abstract class Role {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer roleId;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public static <T extends Role> boolean hasRole(User user, Class<T> role) throws UserException {
        try {
            loadRole(user, role);
            return true;
        } catch (UserException use) {
            return false;
        }
    }

    public static <T extends Role> T loadRole(User user, Class<T> role) throws UserException {
        List<Role> roles = user.getRoles();
        Optional<T> result = (Optional<T>) roles
                .stream()
                .filter(r -> role.isInstance(r))
                .findAny();

        if (!result.isPresent())
            throw new UserException("Incorrect role for user");

        return result.get();
    }

    public static List<Role> createRoles(List<RoleType> roleTypes) {
        return roleTypes.stream().map(roleType -> toRole(roleType)).collect(Collectors.toList());
    }

    public static Role toRole(RoleType roleType) {
        switch (roleType) {
            case ROLE_REPAIRER:
                return new Repairer();
            case ROLE_ADMIN:
                return new Adminstrator();
            default:
                return new Client();
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public abstract RoleType getRoleType();

    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    public int hashCode() {
        return roleId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return roleId.equals(role.roleId);

    }

    public enum RoleType {
        ROLE_CLIENT, ROLE_REPAIRER, ROLE_ADMIN
    }
}

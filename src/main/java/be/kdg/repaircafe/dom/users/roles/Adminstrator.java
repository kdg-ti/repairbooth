package be.kdg.repaircafe.dom.users.roles;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_ADMIN")
public class Adminstrator extends Role {

    @Override
    public RoleType getRoleType() {
        return RoleType.ROLE_ADMIN;
    }
}

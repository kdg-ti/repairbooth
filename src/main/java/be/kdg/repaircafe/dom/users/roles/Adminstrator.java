package be.kdg.repaircafe.dom.users.roles;

public class Adminstrator extends Role {

    @Override
    public RoleType getRoleType() {
        return RoleType.ROLE_ADMIN;
    }
}

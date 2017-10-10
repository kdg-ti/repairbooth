package be.kdg.repaircafe.config.orika.custom.convertors;

import be.kdg.repaircafe.dom.users.roles.Role;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

@Component
public class RoleRoleTypeConvertor extends CustomConverter<Role, Role.RoleType> {
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/orika/

    @Override
    public Role.RoleType convert(Role role, Type<? extends Role.RoleType> type, MappingContext mappingContext) {
        return role.getRoleType();
    }

}

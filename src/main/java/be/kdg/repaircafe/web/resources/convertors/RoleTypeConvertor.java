package be.kdg.repaircafe.web.resources.convertors;

import be.kdg.repaircafe.dom.users.roles.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleTypeConvertor implements Converter<String, List<Role.RoleType>> {
    @Override
    public List<Role.RoleType> convert(String source) {
        List<Role.RoleType> roleTypes = new ArrayList<>();
        roleTypes.add(Role.RoleType.valueOf(source));
        return roleTypes;
    }
}

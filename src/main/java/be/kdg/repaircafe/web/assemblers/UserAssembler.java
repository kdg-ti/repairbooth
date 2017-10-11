package be.kdg.repaircafe.web.assemblers;

import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.web.resources.users.UserResource;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends Assembler<User, UserResource> {

    public UserAssembler() {
        super(User.class, UserResource.class);
    }
}

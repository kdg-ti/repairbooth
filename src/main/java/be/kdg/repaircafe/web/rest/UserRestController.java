package be.kdg.repaircafe.web.rest;

import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.services.api.RepairService;
import be.kdg.repaircafe.services.api.UserService;
import be.kdg.repaircafe.web.assemblers.RepairAssembler;
import be.kdg.repaircafe.web.assemblers.UserAssembler;
import be.kdg.repaircafe.web.resources.repairs.RepairResource;
import be.kdg.repaircafe.web.resources.users.PersonResource;
import be.kdg.repaircafe.web.resources.users.UserResource;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final Logger logger = Logger.getLogger(UserRestController.class);
    private final UserService userService;
    private final RepairService repairService;
    private final MapperFacade mapperFacade;
    private final UserAssembler userAssembler;
    private final RepairAssembler repairAssembler;

    public UserRestController(UserService userService, RepairService repairService,
                              MapperFacade mapperFacade, UserAssembler userAssembler, RepairAssembler repairAssembler) {
        this.userService = userService;
        this.repairService = repairService;
        this.mapperFacade = mapperFacade;
        this.userAssembler = userAssembler;
        this.repairAssembler = repairAssembler;
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody UserResource userResource) {
        User user_in = mapperFacade.map(userResource, User.class);
        User user_out = userService.addUser(user_in);

        logger.info(this.getClass().toString() + ": adding new user " + user_out.getUserId());
        return new ResponseEntity<>(userAssembler.toResource(user_out), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> findUserById(@PathVariable int userId) {

        logger.info(this.getClass().toString() + ":" + userId);
        User user = userService.findUserById(userId);
        UserResource userResource = userAssembler.toResource(user);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResource> updateUserById(@PathVariable int userId,
                                                       @Valid @RequestBody PersonResource personResource) {
        logger.info(this.getClass().toString() + ":" + "updating user " + userId);

        User user_in = userService.findUserById(userId);
        mapperFacade.map(personResource, user_in.getPerson());
        User user_out = userService.saveUser(user_in);

        return new ResponseEntity<>(userAssembler.toResource(user_out), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        logger.info(this.getClass().toString() + ": deleting user " + userId);
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<UserResource> updatePassword(@PathVariable int userId,
                                                       @Valid @RequestBody UserResource userResource)

    {

        logger.info(this.getClass().toString() + ": updating user " + userId);
        userService.updatePassword(userId, userResource.getOldPassword(), userResource.getPassword());
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(userAssembler.toResource(user), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/repairs")
    public ResponseEntity<List<RepairResource>> getRepairsByUser(@PathVariable Integer userId) {
        logger.info(this.getClass().toString() + ":" + "returning for user:" + userId);

        return new ResponseEntity<>(
                repairAssembler.toResources(repairService.findRepairsByUserId(userId)),
                HttpStatus.OK);

    }

    @PostMapping("/{userId}/repairs")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<RepairResource> createRepair(
            @AuthenticationPrincipal User user,
            @PathVariable Integer userId,
            @RequestBody @Valid RepairResource repairResource) {
        if (!userId.equals(user.getUserId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Repair in_repair = mapperFacade.map(repairResource, Repair.class);
        Repair out_repair = repairService.saveRepair(user.getUserId(), in_repair);

        return new ResponseEntity<>(repairAssembler.toResource(out_repair), HttpStatus.CREATED);

    }
}

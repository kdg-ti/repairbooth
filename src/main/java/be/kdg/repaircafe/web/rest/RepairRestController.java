package be.kdg.repaircafe.web.rest;

import be.kdg.repaircafe.dom.repairs.Bid;
import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.dom.users.roles.Repairer;
import be.kdg.repaircafe.dom.users.roles.Role;
import be.kdg.repaircafe.services.api.BidService;
import be.kdg.repaircafe.services.api.RepairService;
import be.kdg.repaircafe.services.exceptions.RepairServiceException;
import be.kdg.repaircafe.web.assemblers.BidAssembler;
import be.kdg.repaircafe.web.assemblers.RepairAssembler;
import be.kdg.repaircafe.web.resources.repairs.BidResource;
import be.kdg.repaircafe.web.resources.repairs.RepairResource;
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
@RequestMapping("/api/repairs")
public class RepairRestController {
    private final Logger logger = Logger.getLogger(UserRestController.class);
    private final RepairService repairService;
    private final BidService bidService;
    private final RepairAssembler repairAssembler;
    private final BidAssembler bidAssembler;
    private final MapperFacade mapperFacade;

    public RepairRestController(RepairService repairService, BidService bidService,
                                RepairAssembler repairAssembler, BidAssembler bidAssembler,
                                MapperFacade mapperFacade) {
        this.repairService = repairService;
        this.bidService = bidService;
        this.bidAssembler = bidAssembler;
        this.repairAssembler = repairAssembler;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping("/{repairId}")
    public ResponseEntity<RepairResource> getRepairByRepairId(
            @PathVariable("repairId") Integer repairId) {
        Repair repair = repairService.findRepairById(repairId);
        return new ResponseEntity<>(repairAssembler.toResource(repair), HttpStatus.OK);
    }


    @PutMapping("/{repairId}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<RepairResource> updateRepairByRepairId(
            @AuthenticationPrincipal User user,
            @PathVariable("repairId") Integer repairId,
            @RequestBody @Valid RepairResource repairResource) {
        Repair repair_in = repairService.findRepairById(repairId);

        // user can only update his own repairs
        if (!user.getUserId().equals(repair_in.getClient().getRoleId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


        mapperFacade.map(repairResource, repair_in);
        Repair repair_out = repairService.saveRepair(user.getUserId(), repair_in);
        return new ResponseEntity<>(repairAssembler.toResource(repair_out), HttpStatus.OK);
    }


    @RequestMapping(value = "/{repairId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<RepairResource> deleteRepairByRepairId(
            @AuthenticationPrincipal User user,
            @PathVariable("repairId") Integer repairId) {
        Repair repair = repairService.findRepairById(repairId);

        // user can only delete his own repairs
        if (!user.getUserId().equals(repair.getClient().getRoleId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repairService.deleteRepair(repairId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{repairId}/bids", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<List<BidResource>> getBids(@AuthenticationPrincipal User user,
                                                     @PathVariable(value = "repairId") Integer repairId) {
        boolean isOwnerClient = Role.hasRole(user, Client.class);
        if (isOwnerClient) {
            Repair repair = repairService.findRepairById(repairId);
            List<Repair> repairs = repairService.findRepairsByUserId(user.getUserId());
            if (!repairs.contains(repair))
                throw new RepairServiceException("User doesn't own this repair");
        }
        boolean isRepairer = Role.hasRole(user, Repairer.class);
        if (isOwnerClient || isRepairer) {
            List<Bid> bids = bidService.findBidsByRepair(repairId);
            return new ResponseEntity<>(bidAssembler.toResources(bids), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @RequestMapping(value = "/{repairId}/bids", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_REPAIRER')")
    public ResponseEntity<BidResource> placeBidOnRepair(
            @AuthenticationPrincipal User user,
            @PathVariable("repairId") Integer repairId,
            @RequestBody @Valid BidResource bidResource) {
        Integer bidId = bidService.placeBid(user.getUserId(), repairId, bidResource.getPrice());
        return new ResponseEntity<>(bidAssembler.toResource(bidService.findBidById(bidId)), HttpStatus.CREATED);
    }
}

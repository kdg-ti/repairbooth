package be.kdg.repaircafe.web.rest;

import be.kdg.repaircafe.dom.repairs.Bid;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.services.api.BidService;
import be.kdg.repaircafe.web.assemblers.BidAssembler;
import be.kdg.repaircafe.web.resources.repairs.BidResource;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * RestController for Bids
 */
@RestController
@RequestMapping("/api/bids")
public class BidRestController {
    private final Logger logger = Logger.getLogger(UserRestController.class);
    private final BidService bidService;
    private final BidAssembler bidAssembler;
    private final MapperFacade mapperFacade;

    public BidRestController(final BidService bidService,
                             final BidAssembler bidAssembler,
                             final MapperFacade mapperFacade) {
        this.bidService = bidService;
        this.bidAssembler = bidAssembler;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping("/{bidId}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<BidResource> getBidById(@AuthenticationPrincipal final User user,
                                                  @PathVariable(value = "bidId") final Integer bidId) {
        return new ResponseEntity<>(
                bidAssembler.toResource(bidService.findBidById(bidId)), HttpStatus.OK);
    }


    @PutMapping("/{bidId}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<BidResource> updateBidById(@AuthenticationPrincipal final User user,
                                                     @PathVariable(value = "bidId") final Integer bidId,
                                                     @RequestBody @Valid final BidResource bidResource) {
        Bid bid_in = bidService.findBidById(bidId);

        if (!bid_in.getRepair().getClient().getRoleId().equals(user.getUserId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        mapperFacade.map(bidResource, bid_in);
        Bid bid_out = bidService.saveBid(bid_in);
        return new ResponseEntity<>(
                bidAssembler.toResource(bid_out), HttpStatus.OK);
    }

    @DeleteMapping("/{bidId}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<BidResource> deleteBidById(@AuthenticationPrincipal final User user,
                                                     @PathVariable(value = "bidId") final Integer bidId) {
        bidService.removeBid(bidId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{bidId}/accept")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<BidResource> acceptBidById(@AuthenticationPrincipal final User user,
                                                     @PathVariable(value = "bidId") final Integer bidId) {
        bidService.acceptBid(bidId);
        return new ResponseEntity<>(
                bidAssembler.toResource(bidService.findBidById(bidId)), HttpStatus.OK);
    }


    @RequestMapping(value = "/{bidId}/clear", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<BidResource> clearBidById(@AuthenticationPrincipal final User user,
                                                    @PathVariable(value = "bidId") final Integer bidId) {
        bidService.clearBid(bidId);
        return new ResponseEntity<>(
                bidAssembler.toResource(bidService.findBidById(bidId)), HttpStatus.OK);
    }
}

package be.kdg.repaircafe.web.controllers;

import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.services.api.RepairService;
import be.kdg.repaircafe.web.assemblers.RepairAssembler;
import be.kdg.repaircafe.web.helpers.InformationControllerHelper;
import be.kdg.repaircafe.web.resources.repairs.RepairResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RepairController {
    private final RepairService repairService;
    private final InformationControllerHelper helper;
    private final RepairAssembler repairAssembler;

    @Autowired
    public RepairController(RepairService repairService, InformationControllerHelper helper,
                            RepairAssembler repairAssembler) {
        this.repairService = repairService;
        this.helper = helper;
        this.repairAssembler = repairAssembler;
    }

    @RequestMapping(value = "/getrepairs.do", method = RequestMethod.GET)
    public ModelAndView showRepairsForUser(@AuthenticationPrincipal User user, ModelAndView modelAndView) {
        List<Repair> repairs = repairService.findRepairsByUserId(user.getUserId());
        List<RepairResource> repairResources = repairAssembler.toResources(repairs);
        modelAndView.setViewName("repairs");
        modelAndView.addObject("repairResources", repairResources);
        return modelAndView;
    }

    @RequestMapping(value = "/newrepair.do", method = RequestMethod.GET)
    public ModelAndView showNewRepair(ModelAndView modelAndView) {
        modelAndView.addObject("repairResource", new RepairResource());
        modelAndView.addObject("categories", helper.getCategoryOptions());
        modelAndView.addObject("brands", helper.getBrandOptions());
        modelAndView.addObject("defects", helper.getDefectOptions());
        modelAndView.setViewName("client/newrepair");
        return modelAndView;
    }

    @RequestMapping(value = "/saverepair.do", method = RequestMethod.POST)
    public ModelAndView saveRepair(
            @AuthenticationPrincipal User user,
            @Valid @ModelAttribute("repairResource") RepairResource repairResource,
            BindingResult bindingResult,
            ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:/newrepair.do");
            return modelAndView;
        }

        Repair repair = repairService.saveRepair(user.getUserId(), repairAssembler.fromResource(repairResource));
        modelAndView.addObject("repairResource", repairAssembler.toResource(repair));
        modelAndView.setViewName("client/repaircreated");

        return modelAndView;
    }

    @RequestMapping(value = "/searchrepair.do", method = RequestMethod.GET)
    public ModelAndView searchRepair(ModelAndView modelAndView) {
        modelAndView.addObject("categories", helper.getCategoryOptions());
        modelAndView.addObject("brands", helper.getBrandOptions());
        modelAndView.addObject("defects", helper.getDefectOptions());
        modelAndView.setViewName("repairer/search");
        return modelAndView;
    }


}

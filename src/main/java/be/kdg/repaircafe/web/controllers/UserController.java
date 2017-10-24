package be.kdg.repaircafe.web.controllers;

import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.services.api.UserService;
import be.kdg.repaircafe.web.assemblers.UserAssembler;
import be.kdg.repaircafe.web.resources.users.UserResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Controller bean that handles user action related requests
 */
@Controller
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    public UserController(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    // Login form
    @RequestMapping("/login.do")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("userResource", new UserResource());
        return modelAndView;
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute UserResource userResource,
                                 BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/register.do");
        }

        User userIn = userAssembler.fromResource(userResource);
        User userOut = userService.addUser(userIn);
        modelAndView.addObject("userResource", userAssembler.toResource(userOut));
        modelAndView.setViewName("usercreated");
        return modelAndView;
    }
}

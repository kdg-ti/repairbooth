package be.kdg.repaircafe.web.controllers;

import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.services.api.UserService;
import be.kdg.repaircafe.web.resources.users.UserResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
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

    private UserService userService;
    private MapperFacade mapperFacade;

    @Autowired
    public UserController(UserService userService, MapperFacade mapperFacade) {
        this.userService = userService;
        this.mapperFacade = mapperFacade;
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

        User userIn = mapperFacade.map(userResource, User.class);
        User user = userService.addUser(userIn);
        modelAndView.addObject("userResource", mapperFacade.map(user, UserResource.class));
        modelAndView.setViewName("usercreated");
        return modelAndView;
    }
}

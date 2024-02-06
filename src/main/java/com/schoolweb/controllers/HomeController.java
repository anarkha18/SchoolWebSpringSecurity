package com.schoolweb.controllers;


import com.schoolweb.config.RoleBasedAuthHandler;
import com.schoolweb.entitys.UserDetailsEntity;
import com.schoolweb.entitys.LoginCredentials;
import com.schoolweb.services.userDetailsService;
import com.schoolweb.services.userEntityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private userEntityService userService;

    @Autowired
    private userDetailsService userDetailsService;


    @GetMapping("/register")
    public ModelAndView register(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return new ModelAndView("error").addObject("message", "Please log out to access registration ");
        }
        return new ModelAndView("registrationForm").addObject("UserDetailsEntity", new UserDetailsEntity());
    }

    @PostMapping("/register")
    public ModelAndView signup(@Valid @ModelAttribute("UserDetailsEntity") UserDetailsEntity myUser, BindingResult result) {
        try {
            if (result.hasErrors()) {
                logger.info("Validation errors: " + result.getAllErrors());
                return new ModelAndView("registrationForm")
                        .addObject("UserDetailsEntity", myUser)
                        .addObject("hasErrors", true)
                        .addObject("error", "user not saved!!!");
            }

            if (userService.userNameExists(myUser.getUser().getUserName())) {
                return new ModelAndView("registrationForm")
                        .addObject("UserDetailsEntity", myUser)
                        .addObject("error", "user name already exists!!!");
            }
            LoginCredentials loginCredentials=userService.createLoginObj(myUser);
            userService.saveLoginCredentials(loginCredentials);

            myUser.setUser(loginCredentials);
            userDetailsService.saveUserDetails(myUser);
            logger.info("successfull saved" + myUser);
            return new ModelAndView("registrationForm")
                    .addObject("UserDetailsEntity", new UserDetailsEntity())
                    .addObject("success", "user is saved!!!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/login")
    public ModelAndView login(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return new ModelAndView("redirect:/"); // Redirect logged-in users
        } else {
            // User is not logged in, show the login page
            return new ModelAndView("login");
        }
    }

}

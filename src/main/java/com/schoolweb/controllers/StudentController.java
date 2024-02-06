package com.schoolweb.controllers;


import com.schoolweb.entitys.LoginCredentials;
import com.schoolweb.entitys.UserDetailsEntity;
import com.schoolweb.services.userEntityService;
import com.schoolweb.services.userDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private userEntityService userService;

    @Autowired
    private userDetailsService userDetailsService;
    @GetMapping("/Home")
    public ModelAndView studentHome(@AuthenticationPrincipal UserDetails userDetails) {
        LoginCredentials loginCredentials = userService.findByUserName(userDetails.getUsername());
        UserDetailsEntity userDetailsEntity = userDetailsService.getUserDetailsbyUser(loginCredentials);

        // Pass both entities to the view
        return new ModelAndView("studentHome")
                .addObject("principal", userDetails)
                .addObject("userDetailsEntity", userDetailsEntity);
    }

    @GetMapping("/profile")
    public ModelAndView studentPro() {
        return new ModelAndView("ok");
    }

}

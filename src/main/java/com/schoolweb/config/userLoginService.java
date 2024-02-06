package com.schoolweb.config;

import com.schoolweb.entitys.LoginCredentials;
import com.schoolweb.services.userEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class userLoginService implements UserDetailsService {


    private static final Logger logger= LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private userEntityService userEntityService;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<LoginCredentials> myUser = Optional.ofNullable(userEntityService.findByUserName(username));
        return myUser.map(UserLoginDetails::new).orElseThrow(() -> {
            logger.error("User not found: {}", username);
            return new UsernameNotFoundException("User not found with username: " + username);
        });
    }

}

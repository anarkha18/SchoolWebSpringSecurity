package com.schoolweb.services;


import com.schoolweb.entitys.LoginCredentials;
import com.schoolweb.entitys.UserDetailsEntity;
import com.schoolweb.repos.UserDetailsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(userEntityService.class);

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUserDetails(UserDetailsEntity user) {
        userDetailsRepo.save(user);
    }

    public UserDetailsEntity getUserDetailsbyUser(LoginCredentials user) {
        return userDetailsRepo.findByUserId(user.getId());
    }
}
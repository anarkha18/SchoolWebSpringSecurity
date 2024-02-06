package com.schoolweb.services;


import com.schoolweb.entitys.LoginCredentials;
import com.schoolweb.entitys.UserDetailsEntity;
import com.schoolweb.repos.userRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userEntityService {

    private static final Logger logger= LoggerFactory.getLogger(userEntityService.class);

    @Autowired
    private userRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void saveLoginCredentials(LoginCredentials user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encode password
        userRepo.save(user);
    }

    public boolean userNameExists(String userName) throws Exception{
        if (userRepo.findByUserName(userName) != null) {
            logger.info(userName + "already exists");
            return true;
        }
        return false;

    }

    public LoginCredentials findByUserName(String username) {
        return userRepo.findByUserName(username);
    }

    public LoginCredentials createLoginObj(UserDetailsEntity myUser) {
        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUserName(myUser.getUser().getUserName());
        loginCredentials.setPassword(myUser.getUser().getPassword());
        loginCredentials.setRole(myUser.getUser().getRole());
        return loginCredentials;
    }
}

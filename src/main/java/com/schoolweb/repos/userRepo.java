package com.schoolweb.repos;

import com.schoolweb.entitys.LoginCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<LoginCredentials, Long> {
    LoginCredentials findByUserName(String userName);

}

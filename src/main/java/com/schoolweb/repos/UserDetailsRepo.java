package com.schoolweb.repos;

import com.schoolweb.entitys.LoginCredentials;
import com.schoolweb.entitys.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Long> {
    UserDetailsEntity findByUserId(long id);
}

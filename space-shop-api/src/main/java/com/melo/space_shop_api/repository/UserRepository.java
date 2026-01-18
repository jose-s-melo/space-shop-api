package com.melo.space_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.melo.space_shop_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);
    
}

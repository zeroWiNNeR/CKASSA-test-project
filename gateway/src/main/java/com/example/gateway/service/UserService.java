package com.example.gateway.service;

import com.example.gateway.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Created by Aleksei Vekovshinin on 26.11.2020
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}


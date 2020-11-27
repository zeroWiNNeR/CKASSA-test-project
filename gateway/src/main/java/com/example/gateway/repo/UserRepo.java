package com.example.gateway.repo;

import com.example.gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Aleksei Vekovshinin on 26.11.2020
 */
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

}

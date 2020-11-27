package com.example.gateway.controller;

import com.example.gateway.model.User;
import com.example.gateway.model.UserRole;
import com.example.gateway.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Created by Aleksei Vekovshinin on 26.11.2020
 */
@Controller
public class RegistrationController {

    private final UserRepo userRepo;
    @Autowired
    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/registration")
    public String registration(User user, Model model) {
        user = new User();
        Set<UserRole> roles = new HashSet<>(Arrays.asList(UserRole.READER));

        user.setRoles(roles);
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            User user,
            Map<String, Object> model,
            @RequestParam Map<String, String> form
    ) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        Set<String> roles = Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toSet());

        Set<UserRole> userRoles = new HashSet<>();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                userRoles.add(UserRole.valueOf(key));
            }
        }
        user.setRoles(userRoles);
        user.setActive(true);
        userRepo.save(user);

        return "redirect:/login";
    }
}

package com.example.gateway.controller;

import com.example.gateway.model.*;
import com.example.gateway.repo.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@Controller
public class GatewayController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final TaskRepo taskRepo;
    @Autowired
    public GatewayController(TaskRepo taskRepo, RestTemplate restTemplate) {
        this.taskRepo = taskRepo;
        this.restTemplate = restTemplate;
    }

    @PreAuthorize("hasAuthority('READER')")
    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("message", "Hello, " + user.getUsername());
        model.addAttribute("frontendRequest", new FrontendRequest());
        model.addAttribute("tasks", taskRepo.findAll());

        return "index";
    }

    @PreAuthorize("hasAuthority('WRITER')")
    @PostMapping("/makeTask")
    public String makeTask(
            @AuthenticationPrincipal User user,
            @ModelAttribute @Valid FrontendRequest frontendRequest
    ) {
        if (user.getAuthorities().contains(UserRole.WRITER)) {
            logger.info("POST FrontendRequest(/makeTask): " + frontendRequest.getType() + " " + frontendRequest.getLength());
            Task task = new Task(frontendRequest.getType(), frontendRequest.getLength(), TaskStatus.INQUEUE, LocalDateTime.now(), null);
            task = taskRepo.save(task);

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Task> requestEntity = new HttpEntity<>(task, headers);
                restTemplate.exchange("http://test-service/makeTask", HttpMethod.POST, requestEntity, TestServiceResponse.class);
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

}

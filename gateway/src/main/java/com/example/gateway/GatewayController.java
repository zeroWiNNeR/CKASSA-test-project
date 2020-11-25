package com.example.gateway;

import com.example.gateway.model.Request;
import com.example.gateway.model.Task;
import com.example.gateway.model.TaskStatus;
import com.example.gateway.model.TestServiceResponse;
import com.example.gateway.repo.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@Controller
public class GatewayController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    private TestServiceProxy testServiceProxy;
    private RestTemplate restTemplate;
    private TaskRepo taskRepo;
    @Autowired
    public GatewayController(TaskRepo taskRepo, RestTemplate restTemplate) {
        this.taskRepo = taskRepo;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", "Hello, user!");
        model.addAttribute("request", new Request());
        model.addAttribute("tasks", taskRepo.findAll());

        return "index";
    }

    @PostMapping("/makeTask")
    public String makeTask(
            @ModelAttribute Request request,
            Model model
    ) {
        logger.info("POST Request(/makeTask): " + request.getType() + " " + request.getLength());
        Task task = new Task();
        task.setType(request.getType());
        task.setLength(request.getLength());
        task.setStatus(TaskStatus.INQUEUE);
        task.setCreateTime(LocalDateTime.now());
        task = taskRepo.save(task);
        TestServiceResponse response = new TestServiceResponse("ERROR", null);
        try {
            response = restTemplate.getForObject(
                    "http://test-service/makeTask?id="+task.getId(),
                    TestServiceResponse.class
            );
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());

            return "redirect:/";
        }
        List<Task> tasks = taskRepo.findAll();
        if (response.getStatus().equals("OK")) {
            tasks.add(response.getTask());
            model.addAttribute("message", "Успешно!");
            model.addAttribute("request", new Request());
            model.addAttribute("tasks", tasks);
        } else {
            model.addAttribute("message", "Недуачно!");
            model.addAttribute("request", new Request());
            model.addAttribute("tasks", tasks);
        }
        return "index";
    }

}

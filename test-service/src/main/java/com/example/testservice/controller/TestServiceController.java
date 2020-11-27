package com.example.testservice.controller;

import com.example.testservice.model.Task;
import com.example.testservice.model.TestServiceResponse;
import com.example.testservice.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@RestController
public class TestServiceController {

    @Value("${server.port}")
    private String port;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TestService testService;
    @Autowired
    public TestServiceController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    public String refresh() {
        return "";
    }

    @PostMapping("/makeTask")
    public TestServiceResponse makeExternalRequest(@RequestBody Task task) {
        logger.info("Launched Task on port: " + port);
        return testService.makeExternalRequest(task.getId(), task.getType(), task.getLength());
    }

}

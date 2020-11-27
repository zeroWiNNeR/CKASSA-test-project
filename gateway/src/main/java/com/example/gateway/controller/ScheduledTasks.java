package com.example.gateway.controller;

import com.example.gateway.config.RibbonConfiguration;
import com.example.gateway.model.Task;
import com.example.gateway.model.TestServiceResponse;
import com.example.gateway.repo.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@Component
@RibbonClient(name = "test-service", configuration = RibbonConfiguration.class)
public class ScheduledTasks {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final TaskRepo taskRepo;

    @Autowired
    public ScheduledTasks(RestTemplate restTemplate, TaskRepo taskRepo) {
        this.restTemplate = restTemplate;
        this.taskRepo = taskRepo;
    }

    @Scheduled(fixedRate = 30000)
    public void makeTasks() {
        logger.info("Finding outstandingTasks ...");
        LocalDateTime now = LocalDateTime.now();
        int seconds = now.getSecond();
        LocalDateTime neededTime;
        if (seconds >= 30) {
            neededTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond()-30);
        } else {
            int remainder = (seconds - 30) * (-1);
            neededTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute()-1, 59-remainder);
        }
        List<Task> outstandingTasks = taskRepo.getTasksByCreateTimeLessThan(neededTime);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (outstandingTasks.size() > 0) {
            logger.info("OutstandingTasks found!");
            outstandingTasks.forEach(task ->
                    restTemplate.exchange(
                            "http://test-service/makeTask",
                            HttpMethod.POST,
                            new HttpEntity<>(
                                    new Task(task.getId(), task.getType(), task.getLength(), task.getStatus(), task.getCreateTime(), task.getPayload()),
                                    headers
                            ),
                            TestServiceResponse.class
                    )
            );
            logger.info("outstandingTasks completed!");
        } else
            logger.info("No outstandingTasks found.");
    }

}

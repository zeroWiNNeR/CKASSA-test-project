package com.example.gateway;

import com.example.gateway.config.RibbonConfiguration;
import com.example.gateway.model.Task;
import com.example.gateway.model.TestServiceResponse;
import com.example.gateway.repo.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Created by Aleksei Vekovshinin on 26.11.2020
 */
@Component
@RibbonClient(name = "test-service", configuration = RibbonConfiguration.class)
public class ScheduledTasks {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;
    private TaskRepo taskRepo;

    @Autowired
    public ScheduledTasks(RestTemplate restTemplate, TaskRepo taskRepo) {
        this.restTemplate = restTemplate;
        this.taskRepo = taskRepo;
    }

    @Scheduled(fixedRate = 30000)
    public void makeTasks() {
        logger.info("Finding outstandingTasks ...");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime neededTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute()-1, now.getSecond());
        List<Task> outstandingTasks = taskRepo.getTasksByCreateTimeLessThan(neededTime);
        if (outstandingTasks.size() > 0) {
            logger.info("OutstandingTasks found!");
            outstandingTasks.forEach(task ->
                    restTemplate.getForObject("http://test-service/makeTask?id="+task.getId(), TestServiceResponse.class)
            );
            logger.info("outstandingTasks completed!");
        } else
            logger.info("No outstandingTasks found.");
    }

}

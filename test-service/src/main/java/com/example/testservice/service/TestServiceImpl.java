package com.example.testservice.service;

import com.example.testservice.model.Task;
import com.example.testservice.model.TaskStatus;
import com.example.testservice.model.TestServiceResponse;
import com.example.testservice.repo.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@Service
public class TestServiceImpl implements TestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TaskRepo taskRepo;
    private final RestTemplate restTemplate;
    @Autowired
    public TestServiceImpl(TaskRepo taskRepo, RestTemplate restTemplate) {
        this.taskRepo = taskRepo;
        this.restTemplate = restTemplate;
    }

    @Override
    public TestServiceResponse makeExternalRequest(Long id, String type, Integer length) {
        Task task = taskRepo.getById(id);

        logger.info("Make External Request: " + type + " " + length);
        String generatedValue = restTemplate.getForObject(
                "http://localhost:46000/generate?type=" + type + "&&length=" + length,
                String.class
        );

        TestServiceResponse testServiceResponse = new TestServiceResponse();

        if (generatedValue != null) {
            logger.info("Generator Response: " + generatedValue);

            task.setPayload(generatedValue);
            task.setStatus(TaskStatus.COMPLETED);
            task = taskRepo.save(task);

            testServiceResponse.setStatus("OK");
            testServiceResponse.setTask(task);
            return testServiceResponse;
        } else {
            logger.info("External generator doesn't work correctly!");
            testServiceResponse.setStatus("ERROR");
            testServiceResponse.setTask(null);
            return testServiceResponse;
        }
    }

}

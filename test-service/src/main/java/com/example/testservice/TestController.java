package com.example.testservice;

import com.example.testservice.model.TestServiceResponse;
import com.example.testservice.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@RestController
public class TestController {

    @Value("${server.port}")
    private String port;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TestService testService;
    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    public String refresh() {
        return "";
    }

    @GetMapping("/makeTask")
    public TestServiceResponse makeExternalRequest (
            @RequestParam("id") Long id
    ) {
        logger.info("Launched Task on port: " + port);
        return testService.makeExternalRequest(id);
    }

}

package com.example.generateapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@RestController
public class GenerateApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/generate")
    public String getString(
            @RequestParam("type") String type,
            @RequestParam("length") Integer length
    ) {

        if ("string".equals(type)) {

            try {
                Thread.sleep((int)(Math.random() * 10000));
            } catch (InterruptedException e) {
                logger.error("Interrupted error " + e.getLocalizedMessage());
            }

            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = (int)(AlphaNumericString.length() * Math.random());
                sb.append(AlphaNumericString.charAt(index));
            }

            logger.info("Generated STRING = " + sb);
            return sb.toString();

        } else
            return null;
    }
}

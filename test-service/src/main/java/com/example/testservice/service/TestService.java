package com.example.testservice.service;

import com.example.testservice.model.TestServiceResponse;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
public interface TestService {

    /**
     * @param id - id of task in DB
     * @return payload generated by API
     **/
    TestServiceResponse makeExternalRequest(Long id);

}

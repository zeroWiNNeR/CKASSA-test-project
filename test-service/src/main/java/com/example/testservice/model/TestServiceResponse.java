package com.example.testservice.model;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
public class TestServiceResponse {

    private String status;

    private Task task;

    public TestServiceResponse() {
    }

    public TestServiceResponse(String status, Task task) {
        this.status = status;
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}

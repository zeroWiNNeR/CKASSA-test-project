package com.example.gateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@Entity
@Table(name = "TASKS")
public class Task {

    @Id
    @SequenceGenerator(name="task_generator", sequenceName = "task_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="task_generator")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "length")
    private Integer length;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "payload")
    private String payload;


    public Task() {

    }

    public Task(Long id, String type, Integer length, TaskStatus status, LocalDateTime createTime, String payload) {
        this.id = id;
        this.type = type;
        this.length = length;
        this.status = status;
        this.createTime = createTime;
        this.payload = payload;
    }

    public Task(String type, Integer length, TaskStatus status, LocalDateTime createTime, String payload) {
        this.type = type;
        this.length = length;
        this.status = status;
        this.createTime = createTime;
        this.payload = payload;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}

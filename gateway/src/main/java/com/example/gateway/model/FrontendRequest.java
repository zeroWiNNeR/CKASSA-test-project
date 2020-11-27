package com.example.gateway.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
public class FrontendRequest implements Serializable {

    @NotEmpty
    private String type;

    @Min(0)
    @Max(100)
    private Integer length;

    public FrontendRequest() {
    }

    public FrontendRequest(String type, Integer length) {
        this.type = type;
        this.length = length;
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

}

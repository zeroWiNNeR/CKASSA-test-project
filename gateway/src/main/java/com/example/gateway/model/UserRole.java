package com.example.gateway.model;

import org.springframework.security.core.GrantedAuthority;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
public enum UserRole implements GrantedAuthority {

    READER, WRITER;

    @Override
    public String getAuthority() {
        return name();
    }

}

package com.trackhour.app.controller.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {
    private String password;

    public Password(String password) {
        this.password = password;
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String getBcryptPassword(){
        return this.password;
    }
}
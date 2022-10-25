package com.trackhour.app.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {


    @JsonProperty("identification")
    private UUID id;

    @JsonProperty("e-mail")
    private String email;

    @JsonProperty("Create_date")
    private LocalDateTime dateRegistration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public UserResponse(UUID id, String email, LocalDateTime dateRegistration) {
        this.id = id;
        this.email = email;
        this.dateRegistration = dateRegistration;
    }
}

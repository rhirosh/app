package com.trackhour.app.controller;

import com.trackhour.app.controller.dto.UserRequest;
import com.trackhour.app.controller.mapper.UserMapping;
import com.trackhour.app.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    final private UserService service;
    final private UserMapping mapping;

    public UserController(UserService service, UserMapping mapping) {
        this.service = service;
        this.mapping = mapping;
    }
    //final private UserMapping mapping;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserRequest userRequest) throws Exception{

        try{
            service.save(mapping.requestToModel(userRequest));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){ return ResponseEntity.ok( mapping.modelsToRequests(service.getUsers()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(mapping.modelToRequest(service.getUser(id)));
    }
}

package com.trackhour.app.controller;

import com.trackhour.app.controller.dto.AuthResponse;
import com.trackhour.app.controller.dto.Password;
import com.trackhour.app.controller.dto.UserRequest;
import com.trackhour.app.controller.mapper.UserMapping;
import com.trackhour.app.security.TokenService;
import com.trackhour.app.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    final private UserService service;
    final private UserMapping mapping;
    final private TokenService tokenService;
    final private AuthenticationManager authenticationManager;

    public UserController(UserService service, UserMapping mapping, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.mapping = mapping;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping(value = "/auth")
    public ResponseEntity<?> auth(@RequestBody UserRequest request){

        UsernamePasswordAuthenticationToken login = request.converterToUserNameAuthentication();
        try {
            Authentication authentication = this.authenticationManager.authenticate(login);
            String token = this.tokenService.generateToken(authentication);
            AuthResponse response = new AuthResponse(token,"Bearer");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserRequest request) throws Exception {
        try {
            Password bty = new Password(request.getPassword());
            request.setPassword(bty.getBcryptPassword());
            service.save(mapping.requestToModel(request));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e){
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

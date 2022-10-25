package com.trackhour.app.service.impl;

import com.trackhour.app.model.User;
import com.trackhour.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    final private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Save user
    public void save(User user) throws Exception{
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());

        if(byEmail.isPresent())
        {
            throw new IllegalArgumentException("email already registrated");
        }
        userRepository.save(user);
    }
    //List all users
    public List<User> getUsers(){ return(List<User>) userRepository.findAll();}

    //List single user
    public User getUser(UUID id) throws Exception{
        Optional<User> byUser = userRepository.findById(id);
        if(byUser.isPresent()){
            return byUser.get();
        }
        throw new IllegalArgumentException("User not found");
    }
}

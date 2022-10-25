package com.trackhour.app.security;

import com.trackhour.app.model.User;
import com.trackhour.app.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user =this.userRepository.findByEmail(email);
        if(user.isPresent())
        {
            return user.get();
        }
        throw new UsernameNotFoundException("Not valid");
    }
}

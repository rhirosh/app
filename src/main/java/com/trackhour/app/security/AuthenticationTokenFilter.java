package com.trackhour.app.security;

import com.trackhour.app.model.User;
import com.trackhour.app.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private UserRepository userRepository;
   private TokenService tokenService;

    public AuthenticationTokenFilter(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.requestToken(request);
        Boolean isValid = this.tokenService.isTokenIsValid(token);

        if(isValid){
            this.authUser(token);
        }
        filterChain.doFilter(request,response);

    }

    private void authUser(String token) {
        String id  = this.tokenService.getIdUserToken(token);
        Optional<User> user = this.userRepository.findById(UUID.fromString(id));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String requestToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(Objects.isNull(token) || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7, token.length());
    }
}

package com.trackhour.app.security;


import com.trackhour.app.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private UserRepository userRepository;
    private AuthenticationService authenticationService;
    private TokenService tokenService;

    public SecurityConfiguration(UserRepository userRepository, AuthenticationService authenticationService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity requestHttp) throws Exception{
        requestHttp.cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST,"/api/users").permitAll()
                .antMatchers(HttpMethod.POST,"/api/users/auth").permitAll()
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().userDetailsService(this.authenticationService)
                .addFilterBefore(new AuthenticationTokenFilter(this.userRepository, this.tokenService ), UsernamePasswordAuthenticationFilter.class);

        return requestHttp.build();
    }


}

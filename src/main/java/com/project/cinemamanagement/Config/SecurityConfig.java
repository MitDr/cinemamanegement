package com.project.cinemamanagement.Config;

import com.project.cinemamanagement.Filter.JwtAuthFilter;
import com.project.cinemamanagement.Provider.CustomAuthentication;
import com.project.cinemamanagement.Service.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;
    @Autowired
    @Lazy
    private CustomAuthentication customAuthentication;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/register").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/auth/login").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/auth/refresh").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/auth/logout").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/auth/refresh").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/movie").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/movie/**").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/room").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/room/**").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/seat").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/seat/**").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/ticket").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/ticket/**").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/showtime").permitAll())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/showtime/**").permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
//                .authenticationManager(authenticationManager(new AuthenticationConfiguration()))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

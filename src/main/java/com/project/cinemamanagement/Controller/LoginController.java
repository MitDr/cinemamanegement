package com.project.cinemamanagement.Controller;


import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.MyResponse.TokenResponse;
import com.project.cinemamanagement.PayLoad.Request.AuthRequest;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.Provider.CustomAuthentication;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomAuthentication authenticationManager;
    @PostMapping("/login")
    private ResponseEntity<TokenResponse> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) throws Exception{
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        }
        catch (AuthenticationException e){
            throw new Exception("Invalid user or password",e);
        }
        if(authentication.isAuthenticated()){
            String accessToken = jwtService.generateToken(authRequest.getUsername());
            String refreshToken = jwtService.generateRefreshToken(authRequest.getUsername());
            userService.saveRefreshToken(authRequest.getUsername(),refreshToken);
            return new ResponseEntity<TokenResponse>(new TokenResponse(accessToken,refreshToken,"hello!!" + authRequest.getUsername()),HttpStatus.OK);
        }
        else {
            throw new Exception("SOMEHOWITISUNAUTHORIZE");
        }
    }

    @PostMapping("/register")
    private ResponseEntity<MyResponse> addNewUser(@Valid @RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"register sucessfully"), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    private ResponseEntity<TokenResponse> refreshToken(@RequestBody String refreshToken){
        User user = userService.getUserByRefreshToken(refreshToken);
        if(user == null){
            throw new DataNotFoundException("There is no such refresh token");
        }
        String accessToken = jwtService.generateToken(user.getUserName());
        String newRefreshToken = jwtService.generateRefreshToken(user.getUserName());
        userService.saveRefreshToken(user.getUserName(),newRefreshToken);
        return new ResponseEntity<TokenResponse>(new TokenResponse(accessToken,newRefreshToken,"login sucessfully"),HttpStatus.OK);
    }

    @PostMapping("/logout")
    private ResponseEntity<MyResponse> logout(@RequestBody String refreshToken){
        User user = userService.getUserByRefreshToken(refreshToken);
        if(user == null){
            throw new DataNotFoundException("There is no such refresh token");
        }
        userService.deleteRefreshToken(refreshToken);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"logout sucessfully"),HttpStatus.OK);

    }
}

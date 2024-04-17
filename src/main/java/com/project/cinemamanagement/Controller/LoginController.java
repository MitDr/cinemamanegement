package com.project.cinemamanagement.Controller;


import com.project.cinemamanagement.Entity.User;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
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
//            jwtService.saveRefreshToken(authRequest.getUsername(),refreshToken);
            return new ResponseEntity<TokenResponse>(new TokenResponse(accessToken,refreshToken,"login sucessfully"),HttpStatus.OK);
        }
        else {
            throw new Exception("SOMEHOWITISUNAUTHORIZE");
        }
    }

//    @PostMapping("/addNewUser")
//    private MyResponse addNewUser(@Valid @RequestBody UserRequest userRequest){
//        try{
//            return new MyResponse(userService.addUser(userRequest),"user added",200);
//        }
//        catch (Exception e){
//            return new MyResponse(null,e.getMessage(),400);
//        }
//    }

    @PostMapping("/register")
    private ResponseEntity<MyResponse> addNewUser(@Valid @RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"register sucessfully"), HttpStatus.OK);
    }
}

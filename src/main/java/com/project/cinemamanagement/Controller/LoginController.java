package com.project.cinemamanagement.Controller;


import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.AuthRequest;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }
        else{
            throw new UsernameNotFoundException("invalid user request");
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

    @PostMapping("/addNewUser")
    private ResponseEntity<MyResponse> addNewUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.addUser(userRequest),"user added",200), HttpStatus.OK);
    }
}

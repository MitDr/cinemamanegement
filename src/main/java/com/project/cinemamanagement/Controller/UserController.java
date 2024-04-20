package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.AuthRequest;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MyResponse> getAllUser(){
        return new ResponseEntity<>(new MyResponse(userService.getAllUser(),"All user is get"),null,200);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<MyResponse> getUserById(@PathVariable Long userId){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.getUserById(userId),"User with " + userId + " is get"), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<MyResponse> updateUser(@PathVariable Long userId, @RequestBody User user){
        userService.updateUser(userId,user);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Update user successfully"),null,200);
    }

    @DeleteMapping("/{userId}")
    private ResponseEntity<MyResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Delete user successfully"),null,200);
    }
    @GetMapping("/testing/{userId}")
    public ResponseEntity<MyResponse> getAll(@PathVariable Long userId){
        return new ResponseEntity<>(new MyResponse(userService.getUserTicketByUserName(userId),"All user ticket is get"),null,200);
    }
}

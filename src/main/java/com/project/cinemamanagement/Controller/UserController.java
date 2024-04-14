package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.PayLoad.Request.AuthRequest;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(),null,200);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        try
        {
            return new ResponseEntity<>(userService.getUserById(userId),null,200);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @PutMapping("/{userId}")
    private ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user){
        try{
            return new ResponseEntity<>(userService.updateUser(userId,user),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @DeleteMapping("/{userId}")
    private ResponseEntity<?> deleteUser(@PathVariable Long userId){
        try{
            return new ResponseEntity<>(userService.deleteUser(userId),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }
    @GetMapping("/testing")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(userService.getAllUser(),null,200);
    }
}

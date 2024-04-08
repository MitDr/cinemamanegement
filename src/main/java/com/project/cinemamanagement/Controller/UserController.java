package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    private ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(),null,200);
    }

    @GetMapping("/{userId}")
    private ResponseEntity<?> getUserById(Long userId){
        try
        {
            return new ResponseEntity<>(userService.getUserById(userId),null,200);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @PostMapping
    private ResponseEntity<?> addUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addUser(user),null,200);
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
}

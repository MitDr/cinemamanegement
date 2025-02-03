package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.AuthRequest;
import com.project.cinemamanagement.PayLoad.Request.PasswordRequest;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.PayLoad.Request.VerficationRequest;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Service.UserService;
import com.project.cinemamanagement.Service.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
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
@CrossOrigin(origins = "${frontend.endpoint}")
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationService verificationService;

    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MyResponse> getAllUser(){
        return new ResponseEntity<>(new MyResponse(userService.getAllUser(),"All user is get"),null,200);
    }
//    @GetMapping("/paging")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<MyResponse> getAllUserPaging(@RequestParam int page){
//        return new ResponseEntity<>(new MyResponse(userService.getAllUserPaging(page),"All user is get"),null,200);
//    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<MyResponse> getUserById(@PathVariable Long userId){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.getUserById(userId),"User with " + userId + " is get"), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<MyResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest user){
        userService.updateUser(userId,user);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Update user successfully"),null,200);
    }

    @DeleteMapping("/{userId}")
    private ResponseEntity<MyResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Delete user successfully"),null,200);
    }

    @PostMapping("/verification")
    public ResponseEntity<MyResponse> verification(@RequestBody VerficationRequest verficationRequest, HttpServletRequest request){
        verificationService.sendEmail(verficationRequest ,request);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Verification successfully"),null,200);
    }
    @PostMapping("/verify")
    public ResponseEntity<MyResponse> verify(@RequestParam(value = "token") String token){
        verificationService.verify(token);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Verify successfully"),null,200);
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<MyResponse> forgotPassword(@RequestBody VerficationRequest verficationRequest, HttpServletRequest request){
        userService.forgotPassword(verficationRequest ,request);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"email has been sent"),null,200);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<MyResponse> resetPassword(@RequestBody PasswordRequest passwordRequest){
        userService.verifyAndChangePassword(passwordRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Password has been reset"),null,200);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<MyResponse> getUserInfo(@RequestParam(value = "userName") String userName){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.getUserByUserName(userName),"Get user info"),null,200);
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> getUserTickets(@RequestParam(value = "userName") String userName){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.getUserTicketByUserName(userName),"Get user tickets"),null,200);
    }
}

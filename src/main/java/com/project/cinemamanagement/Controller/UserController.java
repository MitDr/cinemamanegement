package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.PasswordRequest;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.PayLoad.Request.VerficationRequest;
import com.project.cinemamanagement.Service.UserService;
import com.project.cinemamanagement.Service.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${frontend.endpoint}")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final VerificationService verificationService;

    @GetMapping("/admin/users")
    public ResponseEntity<MyResponse> getAllUser(){
        return new ResponseEntity<>(new MyResponse(userService.getAllUser(),"All user is get"),null,200);
    }

    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<MyResponse> getUserById(@PathVariable Long userId){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.getUserById(userId),"User with " + userId + " is get"), HttpStatus.ACCEPTED);
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<MyResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest user){
        userService.updateUser(userId,user);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Update user successfully"),null,200);
    }

    @DeleteMapping("/admin/users/{userId}")
    private ResponseEntity<MyResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Delete user successfully"),null,200);
    }

    @PostMapping("/client/users/verification")
    public ResponseEntity<MyResponse> verification(@RequestBody VerficationRequest verficationRequest, HttpServletRequest request){
        verificationService.sendEmail(verficationRequest ,request);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Verification successfully"),null,200);
    }
    @PostMapping("/public/users/verify")
    public ResponseEntity<MyResponse> verify(@RequestParam(value = "token") String token){
        verificationService.verify(token);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Verify successfully"),null,200);
    }
    @PostMapping("/client/users/forgotPassword")
    public ResponseEntity<MyResponse> forgotPassword(@RequestBody VerficationRequest verficationRequest, HttpServletRequest request){
        userService.forgotPassword(verficationRequest ,request);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"email has been sent"),null,200);
    }
    @PostMapping("/public/users/resetPassword")
    public ResponseEntity<MyResponse> resetPassword(@RequestBody PasswordRequest passwordRequest){
        userService.verifyAndChangePassword(passwordRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Password has been reset"),null,200);
    }

    @GetMapping("/client/users/userInfo")
    public ResponseEntity<MyResponse> getUserInfo(@RequestParam(value = "userName") String userName){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.getUserByUserName(userName),"Get user info"),null,200);
    }

    @GetMapping("/client/users/tickets")
    public ResponseEntity<?> getUserTickets(@RequestParam(value = "userName") String userName){
        return new ResponseEntity<MyResponse>(new MyResponse(userService.getUserTicketByUserName(userName),"Get user tickets"),null,200);
    }
}

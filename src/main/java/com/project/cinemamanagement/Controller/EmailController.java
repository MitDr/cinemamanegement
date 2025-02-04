package com.project.cinemamanagement.Controller;


import com.project.cinemamanagement.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

//    @GetMapping("/send-email")
//    public String sendEmail() {
//        // Thay thế địa chỉ email của người nhận và nội dung email ở đây
//        emailService.sendEmail("ttlong1301@gmail.com", "Test Email", "This is a test email.");
//
//        return "Email sent successfully!";
//    }
}

package com.project.cinemamanagement.Controller;


import com.project.cinemamanagement.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail() {
        // Thay thế địa chỉ email của người nhận và nội dung email ở đây
        emailService.sendEmail("ttlong1301@gmail.com", "Test Email", "This is a test email.");

        return "Email sent successfully!";
    }
}

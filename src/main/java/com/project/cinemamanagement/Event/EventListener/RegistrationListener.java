package com.project.cinemamanagement.Event.EventListener;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Entity.VerificationToken;
import com.project.cinemamanagement.Event.OnRegistrationCompleteEvent;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Repository.VerificationRepository;
import com.project.cinemamanagement.Service.EmailService;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Ultility.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final JwtService jwtService;

    private final EmailService emailService;

    private final VerificationRepository verificationRepository;

    private final UserRepository userRepository;
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserRequest userRequest = event.getUser();
        User user = userRepository.findByUserName(userRequest.getUserName()).orElseThrow(() -> new RuntimeException("User not found"));
        sendemail(user, jwtService, emailService, verificationRepository);
    }

    public static void sendemail(User user, JwtService jwtService, EmailService emailService, VerificationRepository verificationRepository) {
        UUID uuid = UUIDGenerator.generateUUID(user.getUserName()+user.getEmail());
//        String token = jwtService.generateEmailToken(user.getUserName());
        String link = "http://localhost:8080/api/v1/user/verify?token=" + uuid;
        emailService.sendEmail(user.getEmail(), "Đây là đường dẫn xác nhận",link);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(uuid.toString());
        verificationToken.setUser(user);
        verificationToken.setConfirmAt(null);

        verificationRepository.save(verificationToken);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}

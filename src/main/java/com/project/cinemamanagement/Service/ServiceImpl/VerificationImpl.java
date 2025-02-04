package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Entity.VerificationToken;
import com.project.cinemamanagement.Event.EventListener.RegistrationListener;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Request.VerficationRequest;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Repository.VerificationRepository;
import com.project.cinemamanagement.Service.EmailService;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Service.VerificationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationImpl implements VerificationService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final JwtService jwtService;

    private final VerificationRepository verificationRepository;
    @Override
    public void sendEmail(VerficationRequest VerifyRequest, HttpServletRequest request) {
        User user = userRepository.findByEmail(VerifyRequest.getEmail()).orElseThrow(() -> new DataNotFoundException("User not found"));
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken") && user.getRefreshToken().equals(cookie.getValue())) {
                    RegistrationListener.sendemail(user, jwtService, emailService, verificationRepository);
                }
            }
        }
    }

    @Override
    public void verify(String token) {
        VerificationToken verificationToken = verificationRepository.findByToken(token);
        if(verificationToken == null){
            throw new DataNotFoundException("There is no such token");
        }
        if(verificationToken.getConfirmAt() != null){
            throw new DataNotFoundException("Token has been confirmed");
        }
        if(!verificationToken.getExpiredAt().isBefore(LocalDateTime.now())){
            verificationToken.setConfirmAt(LocalDateTime.now());
            User user = userRepository.findByUserName(verificationToken.getUser().getUserName()).orElseThrow(() -> new DataNotFoundException("User not found"));
            user.setVerified(true);
            userRepository.save(user);
            verificationRepository.save(verificationToken);
        }
    }


}

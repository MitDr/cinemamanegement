package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Enum.ROLE;
import com.project.cinemamanagement.Event.EventListener.RegistrationListener;
import com.project.cinemamanagement.Event.OnRegistrationCompleteEvent;
import com.project.cinemamanagement.Exception.DataFoundException;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Exception.InvalidDataException;
import com.project.cinemamanagement.PayLoad.Request.*;
import com.project.cinemamanagement.PayLoad.Response.AuthResponse;
import com.project.cinemamanagement.PayLoad.Response.TicketResponse;
import com.project.cinemamanagement.PayLoad.Response.UserResponse;
import com.project.cinemamanagement.PayLoad.Response.UserTicketResponse;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Service.EmailService;
import com.project.cinemamanagement.Service.JwtService;
import com.project.cinemamanagement.Service.TicketService;
import com.project.cinemamanagement.Service.UserService;
import com.project.cinemamanagement.Ultility.OTPGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    private final TicketService ticketService;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final ApplicationEventPublisher eventPublisher;

    private final OTPGenerator otpGenerator;

    @Value("${jwt.cookieExpiration}")
    private Long cookieExpiration;

    @Override
    public List<UserResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userList) {
            userResponses.add(new UserResponse(user.getUserId(),user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole()));
        }
        return userResponses;
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new DataFoundException("User already exists");
        }
        else {
            User user = User.builder()
                    .userName(userRequest.getUserName())
                    .fullName(userRequest.getFullName())
                    .phone(userRequest.getPhone())
                    .email(userRequest.getEmail())
                    .address(userRequest.getAddress())
                    .passWord(encoder.encode(userRequest.getPassWord()))
                    .role(ROLE.USER)
                    .verified(false)
                    .build();
            userRepository.save(user);
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this,userRequest));
            return new UserResponse(user);
        }
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        return new UserResponse(user.getUserId(), user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getRole());
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest user) {
        User updateUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));

        updateUser.setUserName(user.getUserName());
        updateUser.setPhone(user.getPhone());
        updateUser.setEmail(user.getEmail());
        updateUser.setAddress(user.getAddress());
        updateUser.setFullName(user.getFullName());

        userRepository.save(updateUser);
        return new UserResponse(updateUser.getUserId() ,updateUser.getUserName(), updateUser.getFullName(), updateUser.getEmail(), updateUser.getPhone(), updateUser.getAddress(),updateUser.getRole());
    }

    @Override
    public UserResponse deleteUser(Long userId) {
        User deleteUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        userRepository.delete(deleteUser);
        return new UserResponse(deleteUser.getUserId(),deleteUser.getUserName(), deleteUser.getFullName(), deleteUser.getEmail(), deleteUser.getPhone(), deleteUser.getAddress(), deleteUser.getRole());
    }

    @Override
    public void saveRefreshToken(String userName, String refreshToken) {
        User user = userRepository.findByUserName(userName).orElseThrow((() -> new DataNotFoundException("User not found")));
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserByRefreshToken(RefreshRequest refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken.getRefreshToken());
        if(user == null){
            throw new DataNotFoundException("There is no such refresh token");
        }
        return new UserResponse(user.getUserId() ,user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getRole());
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken);
        user.setRefreshToken(null);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserByUserName(String userName) {
        User user =  userRepository.findByUserName(userName).orElseThrow(() -> new DataNotFoundException("User not found"));
        return new UserResponse(user.getUserId() ,user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getRole());

    }

    @Override
    public AuthResponse loginUser(AuthRequest authRequest, HttpServletResponse response){
        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String accessToken;
        String refreshToken;
        User user;
        if(authentication.isAuthenticated()){
            accessToken = jwtService.generateToken(authRequest.getUsername());
            refreshToken = jwtService.generateRefreshToken(authRequest.getUsername());
            user = userRepository.findByUserName(authRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(cookieExpiration)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return new AuthResponse(user,accessToken);
        }
        else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")){
                    User user = userRepository.findByRefreshToken(cookie.getValue());
                    user.setRefreshToken(null);
                    userRepository.save(user);
                }

                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }

    @Override
    public void forgotPassword(VerficationRequest VerifyRequest, HttpServletRequest request) {
        User user = userRepository.findByEmail(VerifyRequest.getEmail()).orElseThrow(() -> new DataNotFoundException("User not found"));
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken") && user.getRefreshToken().equals(cookie.getValue())) {
                    String otp = otpGenerator.generateOTP(user.getEmail());
//                    System.out.println(otp);
                    emailService.sendEmail(user.getEmail(), "OTP", "Your OTP: " + otp +" is valid for 10 minutes");
                }
            }
        }
    }

    @Override
    public void verifyAndChangePassword(PasswordRequest passwordRequest) {
        User user = userRepository.findByEmail(passwordRequest.getEmail()).orElseThrow(() -> new DataNotFoundException("User not found"));
        String email = passwordRequest.getEmail();
        String otp = passwordRequest.getOtp();
        String newPassword = passwordRequest.getNewPassword();
        String confirmPassword = passwordRequest.getConfirmPassword();
        if(newPassword.equals(confirmPassword) && otpGenerator.validateCache(email, otp)){
            user.setPassWord(encoder.encode(newPassword));
            otpGenerator.invalidCache(email);
        }
        else{
            throw new InvalidDataException("Invalid OTP or new password is not same as confirm password");
        }
        userRepository.save(user);
    }

    @Override
    public UserTicketResponse getUserTicketByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new DataNotFoundException("User not found"));
        List<TicketResponse> ticketResponses;
        ticketResponses = ticketService.getTicketByUserId(user.getUserId());
        return new UserTicketResponse(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(), ticketResponses);
    }
}

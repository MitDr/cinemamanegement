package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.PayLoad.Request.*;
import com.project.cinemamanagement.PayLoad.Response.AuthResponse;
import com.project.cinemamanagement.PayLoad.Response.UserResponse;
import com.project.cinemamanagement.PayLoad.Response.UserTicketResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUser();

    UserResponse addUser(UserRequest user);

    UserResponse getUserById(Long userId);

    UserResponse updateUser(Long userId, UserRequest user);

    UserResponse deleteUser(Long userId);
    void saveRefreshToken(String userName, String refreshToken);

    UserResponse getUserByRefreshToken(RefreshRequest refreshToken);

    void deleteRefreshToken(String refreshToken);
    UserResponse getUserByUserName(String userName);
    AuthResponse loginUser(AuthRequest authRequest, HttpServletResponse response) throws Exception;
    UserTicketResponse getUserTicketByUserName(String userName);

    void logout(HttpServletRequest request, HttpServletResponse response);

    void forgotPassword(VerficationRequest VerifyRequest, HttpServletRequest request);

    void verifyAndChangePassword(PasswordRequest passwordRequest);
}

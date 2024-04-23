package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.PayLoad.Request.RefreshRequest;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.PayLoad.Response.UserResponse;
import com.project.cinemamanagement.PayLoad.Response.UserTicketResponse;

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
    UserTicketResponse getUserTicketByUserName(Long userId);
}

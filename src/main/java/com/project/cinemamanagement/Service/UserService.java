package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.PayLoad.Response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUser();

    UserResponse addUser(UserRequest user);

    UserResponse getUserById(Long userId);

    UserResponse updateUser(Long userId, User user);

    UserResponse deleteUser(Long userId);
    void saveRefreshToken(String userName, String refreshToken);

    User getUserByRefreshToken(String refreshToken);

    void deleteRefreshToken(String refreshToken);
    User getUserByUserName(String userName);
}

package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User addUser(User user);

    User getUserById(Long userId);

    User updateUser(Long userId, User user);

    User deleteUser(Long userId);
}
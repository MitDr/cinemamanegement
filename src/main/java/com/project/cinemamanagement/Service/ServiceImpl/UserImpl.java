package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Exception.DataFoundException;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(UserRequest userRequest) {
        User user = new User();
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new DataFoundException("User already exists");
        }
        else {
            user.setUserName(userRequest.getUserName());
            user.setFullName(userRequest.getFullName());
            user.setPhone(userRequest.getPhone());
            user.setEmail(userRequest.getEmail());
            user.setAddress(userRequest.getAddress());
            user.setPassWord(encoder.encode(userRequest.getPassWord()));
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    @Override
    public User updateUser(Long userId, User user) {
        User updateUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));

        updateUser.setUserName(user.getUserName());
        updateUser.setPhone(user.getPhone());
        updateUser.setEmail(user.getEmail());
        updateUser.setAddress(user.getAddress());
        updateUser.setFullName(user.getFullName());
        updateUser.setRole(user.getRole());

        return userRepository.save(updateUser);
    }

    @Override
    public User deleteUser(Long userId) {
        User deleteUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        userRepository.delete(deleteUser);
        return deleteUser;
    }

    @Override
    public void saveRefreshToken(String userName, String refreshToken) {
        User user = userRepository.findByUserName(userName);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Override
    public User getUserByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken);
        user.setRefreshToken(null);
        userRepository.save(user);
    }
}

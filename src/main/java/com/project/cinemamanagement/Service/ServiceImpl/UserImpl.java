package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Exception.DataFoundException;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import com.project.cinemamanagement.PayLoad.Response.TicketResponse;
import com.project.cinemamanagement.PayLoad.Response.UserResponse;
import com.project.cinemamanagement.PayLoad.Response.UserTicketResponse;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Service.TicketService;
import com.project.cinemamanagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<UserResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userList) {
            userResponses.add(new UserResponse(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole()));
        }
        return userResponses;
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
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
        userRepository.save(user);
        return new UserResponse(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getRole());
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        return new UserResponse(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getRole());
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
        return new UserResponse(updateUser.getUserName(), updateUser.getFullName(), updateUser.getEmail(), updateUser.getPhone(), updateUser.getAddress(),updateUser.getRole());
    }

    @Override
    public UserResponse deleteUser(Long userId) {
        User deleteUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        userRepository.delete(deleteUser);
        return new UserResponse(deleteUser.getUserName(), deleteUser.getFullName(), deleteUser.getEmail(), deleteUser.getPhone(), deleteUser.getAddress(), deleteUser.getRole());
    }

    @Override
    public void saveRefreshToken(String userName, String refreshToken) {
        User user = userRepository.findByUserName(userName);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserByRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken);
        if(user == null){
            throw new DataNotFoundException("There is no such refresh token");
        }
        return new UserResponse(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getRole());
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken);
        user.setRefreshToken(null);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserByUserName(String userName) {
        User user =  userRepository.findByUserName(userName);
        return new UserResponse(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getRole());

    }

    @Override
    public UserTicketResponse getUserTicketByUserName(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        List<TicketResponse> ticketResponses;
        ticketResponses = ticketService.getTicketByUserId(userId);
        return new UserTicketResponse(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(), ticketResponses);
    }
}

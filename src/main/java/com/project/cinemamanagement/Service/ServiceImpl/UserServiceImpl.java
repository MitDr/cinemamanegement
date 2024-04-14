package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUserName(username);
//
//        return user.map(UserDetailImpl::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUserName(username);
        System.out.println(user.getUserId());
        System.out.println(user.getPassWord());
        System.out.println(user.getUserName());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailImpl(user);
    }
}

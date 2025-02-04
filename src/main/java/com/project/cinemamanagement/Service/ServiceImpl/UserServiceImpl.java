package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username){
            Optional<User> user = userRepository.findByUserName(username);
//            return user.map(UserDetailImpl::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
           // Kiểm tra xem user có tồn tại trong database không?
//        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.map(UserDetailImpl::new).orElse(null);
    }
}

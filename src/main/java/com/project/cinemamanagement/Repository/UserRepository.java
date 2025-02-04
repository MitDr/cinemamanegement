package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    User findByRefreshToken(String refreshToken);

    Optional<User>  findByEmail(String email);
}

package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);
}

package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.Payment;
import com.project.cinemamanagement.Repository.Custom.PaymentRepositoryCustom;
import com.project.cinemamanagement.Specifications.PaymentSpecifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>, JpaSpecificationExecutor<Payment>, PaymentRepositoryCustom {
    Payment findByPaymentSessionId(String paymentId);
}

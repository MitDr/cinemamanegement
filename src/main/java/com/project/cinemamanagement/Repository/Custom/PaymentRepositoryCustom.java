package com.project.cinemamanagement.Repository.Custom;

import com.project.cinemamanagement.Entity.Payment;
import org.springframework.data.jpa.domain.Specification;

public interface PaymentRepositoryCustom {
    Long calculateTotalRevenue(Specification<Payment> spec);
}

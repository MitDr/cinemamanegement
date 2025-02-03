package com.project.cinemamanagement.Specifications;

import com.project.cinemamanagement.Entity.Payment;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecifications {
    public static Specification<Payment> getRevenue(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate datePredicate = criteriaBuilder.between(
                    root.get("paymentDate"),
                    java.sql.Date.valueOf(startDate),
                    java.sql.Date.valueOf(endDate)
            );
            return datePredicate;
        };
    }
}

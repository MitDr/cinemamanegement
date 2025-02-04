package com.project.cinemamanagement.Specifications;

import com.project.cinemamanagement.Entity.Payment;
import com.project.cinemamanagement.Entity.Ticket;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecifications {
    public static Specification<Ticket> findByUserId(Long userID) {
        return (((root, query, criteriaBuilder) -> {
            Join<Ticket, Payment> paymentJoin = root.join("payment");

            Predicate userPredicate = criteriaBuilder.equal(paymentJoin.get("user").get("userId"), userID);

            return userPredicate;
        }));

    }
}

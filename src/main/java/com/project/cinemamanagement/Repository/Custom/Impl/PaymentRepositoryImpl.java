package com.project.cinemamanagement.Repository.Custom.Impl;

import com.project.cinemamanagement.Entity.Payment;
import com.project.cinemamanagement.Repository.Custom.PaymentRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Long calculateTotalRevenue(Specification<Payment> spec) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Payment> root = query.from(Payment.class);

        query.select(criteriaBuilder.sum(root.get("paymentPrice")));

        if (spec != null) {
            query.where(spec.toPredicate(root, query, criteriaBuilder));
        }

        return entityManager.createQuery(query).getSingleResult();
    }
}

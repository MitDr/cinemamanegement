package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Payment;
import com.project.cinemamanagement.PayLoad.Inner.PaymentInner;
import com.project.cinemamanagement.PayLoad.Request.PaymentRequest;
import com.project.cinemamanagement.PayLoad.Response.PaymentResponse;
import com.stripe.exception.StripeException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    String createPayment(PaymentInner paymentInner) throws StripeException;

    void refund(Long paymentId, Long ticketId, String email) throws StripeException;

    void addPayment(PaymentRequest paymentRequest);
    void updatePayment(PaymentRequest paymentRequest, Long paymentId);
    void deletePayment(Long paymentId);
    List<PaymentResponse> getAllPayment();
    PaymentResponse getPaymentById(Long paymentId);

    void successPayment(String session_id) throws ParseException;
    void cancelPayment(String session_id);

    void scheduleTicketDeletion(Long ticketId, Long expiresAt);
    void expiredPayment(String sessionId);

    Long totalRevenue(LocalDate startDate, LocalDate endDate);
}

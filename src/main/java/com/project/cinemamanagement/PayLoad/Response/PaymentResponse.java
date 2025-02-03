package com.project.cinemamanagement.PayLoad.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private String paymentMethod;
    private String paymentStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentDate;
    private Long paymentPrice;
    private UserResponse user;
    private TicketResponse[] ticketResponses;

    public PaymentResponse(Payment payment) {
        this.paymentId = payment.getPaymentId();
        this.paymentMethod = payment.getPaymentMethod();
        this.paymentStatus = payment.getPaymentStatus().toString();
        this.paymentDate = payment.getPaymentDate();
        this.paymentPrice = payment.getPaymentPrice();
        this.user = new UserResponse(payment.getUser());
        this.ticketResponses = new TicketResponse[payment.getTicket().size()];
        for (int i = 0; i < payment.getTicket().size(); i++) {
            this.ticketResponses[i] = new TicketResponse(payment.getTicket().get(i));
        }
    }
}

package com.project.cinemamanagement.Entity;

import com.project.cinemamanagement.Enum.PAYMENTSTAT;
import com.project.cinemamanagement.PayLoad.Request.PaymentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_Id")
    private Long paymentId;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PAYMENTSTAT paymentStatus;
    @Column(name = "payment_session_id")
    private String paymentSessionId;
    @Column(name = "payment_date")
    private Date paymentDate;
    @Column(name = "payment_price")
    private Long paymentPrice;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Ticket> ticket;

    public Payment(PaymentRequest paymentRequest) {
        this.paymentMethod = paymentRequest.getPaymentMethod();
        this.paymentStatus = PAYMENTSTAT.valueOf(paymentRequest.getPaymentStatus());
        this.paymentDate = paymentRequest.getPaymentDate();
        this.paymentPrice = paymentRequest.getPaymentPrice();
    }
}

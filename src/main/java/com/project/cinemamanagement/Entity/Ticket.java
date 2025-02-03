package com.project.cinemamanagement.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_ticket", uniqueConstraints = @UniqueConstraint(columnNames = {"showtimeId","seatId"}))
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;
    @Column(name = "seatLocation")
    private String seatLocation;
    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtimeId")
    private ShowTime showTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentId")
    private Payment payment;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatId", referencedColumnName = "seatID", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Seat seat;

    public Ticket(TicketRequest ticketRequest, ShowTime showTime, Payment payment) {
        this.date = ticketRequest.getDate();
        this.showTime = showTime;
        this.payment = payment;
    }
}

package com.project.cinemamanagement.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;
    @Column(name = "price")
    private Long price;
    @Column(name = "seatLocation")
    private String seatLocation;
    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private ShowTime showTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Ticket(TicketRequest ticketRequest, ShowTime showTime, User user) {
        this.price = ticketRequest.getPrice();
        this.date = ticketRequest.getDate();
        this.showTime = showTime;
        this.user = user;
    }
}

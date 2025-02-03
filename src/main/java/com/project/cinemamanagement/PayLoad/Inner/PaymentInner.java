package com.project.cinemamanagement.PayLoad.Inner;

import com.project.cinemamanagement.Entity.Ticket;
import com.project.cinemamanagement.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInner {
    String name ;
    Long price;
    String description;
    String currency;
    String email;
    User user;
    List<Ticket> ticketList;
}

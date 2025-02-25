package com.project.cinemamanagement.PayLoad.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long idTicket;
    private String seatLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private Long showtimeId;

    public TicketResponse(Ticket ticket) {
        this.idTicket = ticket.getIdTicket();
        this.seatLocation = ticket.getSeatLocation();
        this.date = ticket.getDate();
        this.showtimeId = ticket.getShowTime().getShowTimeId();
    }
}

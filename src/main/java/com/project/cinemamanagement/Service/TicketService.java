package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.PayLoad.Response.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> addTicket(TicketRequest ticket);

    TicketResponse getTicketById(Long ticketId);

    TicketResponse deleteTicket(Long ticketId);

    TicketResponse updateTicket(Long ticketId, TicketRequest ticket);

    List<TicketResponse> getAllTicket();


}

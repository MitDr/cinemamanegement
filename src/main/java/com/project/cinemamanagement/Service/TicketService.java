package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.PayLoad.Response.TicketResponse;
import com.stripe.exception.StripeException;

import java.util.List;

public interface TicketService {
    String addTicket(TicketRequest ticket) throws StripeException;

    TicketResponse getTicketById(Long ticketId);

    TicketResponse deleteTicket(Long ticketId);

    TicketResponse updateTicket(Long ticketId, TicketRequest ticket);

    List<TicketResponse> getAllTicket();

    List<TicketResponse> getTicketByUserId(Long userId);
}

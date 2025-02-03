package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.PayLoad.Request.TicketRequest;

public interface WebSocketService {
    void process(TicketRequest ticketRequest);
}

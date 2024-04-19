package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Entity.Ticket;
import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.PayLoad.Response.TicketResponse;
import com.project.cinemamanagement.Repository.ShowTimeRepository;
import com.project.cinemamanagement.Repository.TicketRepository;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository; ;
    @Autowired
    ShowTimeRepository showTimeRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public TicketResponse addTicket(TicketRequest ticket) {
        ShowTime showtime = showTimeRepository.findById(ticket.getShowtimeId()).orElseThrow(() -> new DataNotFoundException("Showtime not found"));
        User user = userRepository.findById(ticket.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        Ticket newTicket = new Ticket();
        newTicket.setShowTime(showtime);
        newTicket.setUser(user);
        newTicket.setPrice(ticket.getPrice());
        newTicket.setDate(ticket.getDate());
        newTicket.setSeatLocation(ticket.getSeatLocation());
        ticketRepository.save(newTicket);

        return new TicketResponse(newTicket.getIdTicket(), newTicket.getPrice(), newTicket.getSeatLocation(), newTicket.getDate(), newTicket.getShowTime().getShowTimeId(), newTicket.getUser().getUserId());
    }

    @Override
    public TicketResponse getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        return new TicketResponse(ticket.getIdTicket(), ticket.getPrice(), ticket.getSeatLocation(), ticket.getDate(), ticket.getShowTime().getShowTimeId(), ticket.getUser().getUserId());
    }

    @Override
    public TicketResponse deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        ticketRepository.delete(ticket);
        return new TicketResponse(ticket.getIdTicket(), ticket.getPrice(), ticket.getSeatLocation(), ticket.getDate(), ticket.getShowTime().getShowTimeId(), ticket.getUser().getUserId());
    }

    @Override
    public TicketResponse updateTicket(Long ticketId, TicketRequest ticket) {
        Ticket ticket1 = ticketRepository.findById(ticketId).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        User user = userRepository.findById(ticket.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        ShowTime showtime = showTimeRepository.findById(ticket.getShowtimeId()).orElseThrow(() -> new DataNotFoundException("Showtime not found"));
        ticket1.setShowTime(showtime);
        ticket1.setUser(user);
        ticket1.setPrice(ticket.getPrice());
        ticket1.setSeatLocation(ticket.getSeatLocation());
        ticket1.setDate(ticket.getDate());
        ticketRepository.save(ticket1);
        return new TicketResponse(ticket1.getIdTicket(), ticket1.getPrice(), ticket1.getSeatLocation(), ticket1.getDate(), ticket1.getShowTime().getShowTimeId(), ticket1.getUser().getUserId());
    }

    @Override
    public List<TicketResponse> getAllTicket() {
        List<Ticket> ticket = ticketRepository.findAll();
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for (Ticket t: ticket) {
            ticketResponses.add(new TicketResponse(t.getIdTicket(), t.getPrice(), t.getSeatLocation(), t.getDate(), t.getShowTime().getShowTimeId(), t.getUser().getUserId()));
        }
        return ticketResponses;
    }
}
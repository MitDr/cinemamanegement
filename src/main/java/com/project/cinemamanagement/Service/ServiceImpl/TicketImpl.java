package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Entity.Ticket;
import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Enum.SEATTYPE;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Exception.InvalidDataException;
import com.project.cinemamanagement.PayLoad.Inner.PaymentInner;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.PayLoad.Response.TicketResponse;
import com.project.cinemamanagement.Repository.SeatRepository;
import com.project.cinemamanagement.Repository.ShowTimeRepository;
import com.project.cinemamanagement.Repository.TicketRepository;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Service.PaymentService;
import com.project.cinemamanagement.Service.TicketService;
import com.project.cinemamanagement.Specifications.TicketSpecifications;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TicketImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository; ;
    @Autowired
    ShowTimeRepository showTimeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    PaymentService paymentService;

    @Override
    public String addTicket(TicketRequest ticket) throws StripeException {
        if(!validateTicketSeat(ticket.getSeatLocation())){
            throw new InvalidDataException("Seat location is invalid");
        }
        for(String s : ticket.getSeatLocation()){
            if(ticketRepository.existsBySeatSeatNumberAndShowTimeShowTimeId(s,ticket.getShowtimeId())){
                throw new InvalidDataException("Seat is not available");
            }
        }
        int countDoubleSeat = 0;
        String[] location = ticket.getSeatLocation();
        List<Ticket> ticketsToSave = new ArrayList<>();
        ShowTime showtime = showTimeRepository.findById(ticket.getShowtimeId()).orElseThrow(() -> new DataNotFoundException("Showtime not found"));
        if (ticket.getDate().after(showtime.getTimeEnd())){
            throw new InvalidDataException("Ticket date is invalid");
        }
        List<Seat> seats = seatRepository.findByRoomRoomIDAndSeatNumberIn(showtime.getRoom().getRoomID(), Arrays.asList(location));
//
        Map<String, Seat> seatMap = seats.stream()
                .collect(Collectors.toMap(Seat::getSeatNumber, Function.identity()));
        for (String s : location) {
            Seat seat = seatMap.get(s);

            if (seat.getSeatType() == SEATTYPE.DOUBLE) {
                countDoubleSeat++;
            }
            Ticket newTicket = new Ticket();
            newTicket.setShowTime(showtime);
            newTicket.setDate(ticket.getDate());
            newTicket.setSeatLocation(s);
            newTicket.setSeat(seat);
            ticketsToSave.add(newTicket);
        }
        ticketRepository.saveAll(ticketsToSave);
        User user = userRepository.findById(ticket.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        PaymentInner paymentInner = new PaymentInner();
        paymentInner.setCurrency("vnd");
        paymentInner.setName(user.getFullName());
        paymentInner.setDescription("Thanh toan ve ngay: " + ticket.getDate() + " tai phong: " + showtime.getRoom().getRoomID() +" cho khach hang: " + user.getFullName() + ". So luong ve: " + seats.size());
        paymentInner.setEmail(user.getEmail());
        paymentInner.setPrice(showtime.getPrice() * location.length + showtime.getPrice() * countDoubleSeat);
        paymentInner.setUser(user);
        paymentInner.setTicketList(ticketsToSave);
        //Add payment
        return paymentService.createPayment(paymentInner);
    }

    public boolean validateTicketSeat(String[] seatLocation) {
        for(String s: seatLocation){
            if(s.matches("^(1[0-9]|20|[1-9]([A-T]))$")){
                return true;
            }
            else if( s.matches("^(1[0-9]|20|[1-9])([A-T])_(1[0-9]|20|[1-9])\\2$")){
                String[] string = s.split("_");
                int seat1 = string[0].length()==2?Integer.parseInt(String.valueOf(string[0].charAt(0))):Integer.parseInt(string[0].substring(0,1));
                int seat2 = string[1].length()==2?Integer.parseInt(String.valueOf(string[1].charAt(0))):Integer.parseInt(string[1].substring(0,1));
                return seat2 - seat1 == 1;
            }
        }
        return false;
    }
    @Override
    public TicketResponse getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        return new TicketResponse(ticket.getIdTicket(), ticket.getSeatLocation(), ticket.getDate(), ticket.getShowTime().getShowTimeId());
    }

    @Override
    public TicketResponse deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        ticketRepository.delete(ticket);
        return new TicketResponse(ticket.getIdTicket(), ticket.getSeatLocation(), ticket.getDate(), ticket.getShowTime().getShowTimeId());
    }

    @Override
    public TicketResponse updateTicket(Long ticketId, TicketRequest ticket) {
        String[] location = ticket.getSeatLocation();
        TicketResponse ticketResponse;
        if(location.length != 1){
            throw new InvalidDataException("Seat location must be 1");
        }
        ShowTime showtime = showTimeRepository.findById(ticket.getShowtimeId()).orElseThrow(() -> new DataNotFoundException("Showtime not found"));
        Ticket ticket1 = ticketRepository.findById(ticketId).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        ticket1.setShowTime(showtime);
        ticket1.setSeatLocation(location[0]);
        ticket1.setDate(ticket.getDate());
        ticketResponse = new TicketResponse(ticket1.getIdTicket(), ticket1.getSeatLocation(), ticket1.getDate(), ticket1.getShowTime().getShowTimeId());
        ticketRepository.save(ticket1);
        return ticketResponse;
    }

    @Override
    public List<TicketResponse> getAllTicket() {
        List<Ticket> ticket = ticketRepository.findAll();
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for (Ticket t: ticket) {
            ticketResponses.add(new TicketResponse(t.getIdTicket(), t.getSeatLocation(), t.getDate(), t.getShowTime().getShowTimeId()));
        }
        return ticketResponses;
    }

    @Override
    public List<TicketResponse> getTicketByUserId(Long userId) {
        Specification<Ticket> spec = TicketSpecifications.findByUserId(userId);
        List<Ticket> ticket = ticketRepository.findAll(spec);
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for (Ticket t: ticket) {
            TicketResponse temp = new TicketResponse(t.getIdTicket(), t.getSeatLocation(), t.getDate(), t.getShowTime().getShowTimeId());
            ticketResponses.add(temp);
        }
        return ticketResponses;
    }
}

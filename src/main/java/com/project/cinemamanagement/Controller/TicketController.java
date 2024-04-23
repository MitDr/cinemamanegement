package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Exception.DataFoundException;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.PayLoad.Response.*;
import com.project.cinemamanagement.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ShowTimeService showTimeService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<MyResponse> getAllTicket(){
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.getAllTicket(),null),null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MyResponse> addTicket(@RequestBody TicketRequest ticket) {
        UserResponse user = userService.getUserById(ticket.getUserId());
        if(user == null){
            throw new DataNotFoundException("User not found");
        }
        ShowtimeResponse showTime = showTimeService.getShowTimeById(ticket.getShowtimeId());
        MovieShowtimeResponse movie = movieService.getMovieById(showTime.getMovieId());
        List<SeatResponse> seatList = seatService.getUntakenSeat(ticket.getShowtimeId());
        List<String> availableSeat= new ArrayList<>();
        for(SeatResponse s : seatList){
            availableSeat.add(s.getSeatNumber());
        }
        StringBuilder message = new StringBuilder("Phim của bạn: " + movie.getMovieName() + " Thời gian: " + showTime.getTimeStart().toString() + " Phòng " + showTime.getRoomId() + " Ghế");
        for(String s : ticket.getSeatLocation()){
            if(!availableSeat.contains(s)){
                throw new DataFoundException("Seat is not available");
            }
            message.append(" ").append(s);
        }
        String userEmail = user.getEmail();
        //emailService.sendEmail(userEmail, "Thanh toán vé xem phim thành công ", message.toString());
        ticketService.addTicket(ticket);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Thêm vé thành công"),null,HttpStatus.CREATED);
    }
    @PutMapping("/{ticketId}")
    public ResponseEntity<MyResponse> updateTicket(@PathVariable Long ticketId,@RequestBody TicketRequest ticket){
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.updateTicket(ticketId,ticket),"Update ticket successfully"),null,HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<MyResponse> deleteTicket(@RequestParam Long ticketId){
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.deleteTicket(ticketId),"Delete ticket successfully"),null,HttpStatus.OK);
    }

}

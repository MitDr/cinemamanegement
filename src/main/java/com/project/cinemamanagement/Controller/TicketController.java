package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.PayLoad.Response.RoomResponse;
import com.project.cinemamanagement.PayLoad.Response.ShowtimeResponse;
import com.project.cinemamanagement.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ShowTimeService showTimeService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<MyResponse> getAllTicket(){
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.getAllTicket(),null),null, HttpStatus.OK);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<MyResponse> addTicket(@RequestBody TicketRequest ticket,  @PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        ShowtimeResponse showTime = showTimeService.getShowTimeById(ticket.getShowtimeId());
        Movie movie = movieService.getMovieById(showTime.getMovieId());
        StringBuilder message = new StringBuilder("Phim của bạn: " + movie.getMovieName() + " Thời gian: " + showTime.getTimeStart().toString() + " Phòng " + showTime.getRoomId() + " Ghế");
        for(String s : ticket.getSeatLocation()){
            message.append(" ").append(s);
        }
        String userEmail = user.getEmail();
        emailService.sendEmail(userEmail, "Thanh toán vé xem phim thành công ", message.toString());
        ticketService.addTicket(ticket);
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.addTicket(ticket),null),null,HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<MyResponse> updateTicket(@PathVariable Long ticketId,@RequestBody TicketRequest ticket){
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.updateTicket(ticketId,ticket),null),null,HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<MyResponse> deleteTicket(@RequestParam Long ticketId){
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.deleteTicket(ticketId),null),null,HttpStatus.OK);
    }

}

package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.Service.MovieService;
import com.project.cinemamanagement.Service.TicketService;
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

    @GetMapping
    public ResponseEntity<MyResponse> getAllTicket(){
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.getAllTicket(),null),null, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<MyResponse> addTicket(@RequestBody TicketRequest ticket){
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

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
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "${frontend.endpoint}")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/admin/tickets")
    public ResponseEntity<MyResponse> getAllTicket() {
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.getAllTicket(), null), null, HttpStatus.OK);
    }

    @PostMapping("/client/tickets")
    public ResponseEntity<MyResponse> addTicket(@Valid @RequestBody TicketRequest ticket) throws StripeException {
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.addTicket(ticket), "Thêm vé thành công"), null, HttpStatus.CREATED);
    }

    @PutMapping("/admin/tickets/{ticketId}")
    public ResponseEntity<MyResponse> updateTicket(@PathVariable Long ticketId, @RequestBody TicketRequest ticket) {
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.updateTicket(ticketId, ticket), "Update ticket successfully"), null, HttpStatus.OK);
    }

    @DeleteMapping("/admin/tickets/{ticketId}")
    public ResponseEntity<MyResponse> deleteTicket(@RequestParam Long ticketId) {
        return new ResponseEntity<MyResponse>(new MyResponse(ticketService.deleteTicket(ticketId), "Delete ticket successfully"), null, HttpStatus.OK);
    }

}

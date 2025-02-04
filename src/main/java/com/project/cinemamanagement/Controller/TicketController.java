package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.TicketRequest;
import com.project.cinemamanagement.Service.TicketService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

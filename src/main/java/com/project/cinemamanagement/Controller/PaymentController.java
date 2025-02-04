package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.PaymentRequest;
import com.project.cinemamanagement.PayLoad.Request.RevenueRequest;
import com.project.cinemamanagement.Service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionExpireParams;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "${frontend.endpoint}")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/admin/payments")
    public ResponseEntity<?> getAllPayment() {
        return new ResponseEntity<MyResponse>(new MyResponse(paymentService.getAllPayment(), "Get all payment"), null, 200);
    }

    @PostMapping("/admin/payments")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.addPayment(paymentRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Add new payment successfully"), null, 201);
    }

    @PutMapping("/admin/payments/{paymentId}")
    public ResponseEntity<?> updatePayment(@RequestBody PaymentRequest paymentRequest, @PathVariable Long paymentId) {
        paymentService.updatePayment(paymentRequest, paymentId);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Update payment successfully"), null, 200);
    }

    @DeleteMapping("/admin/payments/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Delete payment successfully"), null, 200);
    }

    @GetMapping("/admin/payments/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long paymentId) {
        return new ResponseEntity<MyResponse>(new MyResponse(paymentService.getPaymentById(paymentId), "Get payment by id"), null, 200);
    }

    @GetMapping("/public/payments/success")
    public void successPayment(@RequestParam String session_id, HttpServletResponse httpResponse) throws IOException, ParseException {
        paymentService.successPayment(session_id);
        httpResponse.sendRedirect("http://localhost:3000/transaction/successful");

    }

    @GetMapping("/public/payments/cancel")
    public void cancelPayment(@RequestParam String session_id, HttpServletResponse httpResponse) throws IOException {
        paymentService.cancelPayment(session_id);
        httpResponse.sendRedirect("http://localhost:3000/transaction/cancel");
    }

    @PostMapping("/client/payments/refund")
    public ResponseEntity<?> refund(@RequestParam Long paymentId, @RequestParam Long ticketId, @RequestParam String email) {
        try {
            paymentService.refund(paymentId, ticketId, email);
            return new ResponseEntity<MyResponse>(new MyResponse(null, "Refund successfully"), null, 200);
        } catch (StripeException e) {
            return new ResponseEntity<MyResponse>(new MyResponse(null, e.getMessage()), null, 400);
        }
    }

    @GetMapping("/admin/payments/revenue")
    public ResponseEntity<?> getRevenue(@Valid @RequestBody RevenueRequest request) {
        return new ResponseEntity<MyResponse>(new MyResponse(paymentService.totalRevenue(request.getStartDate(), request.getEndDate()), "Get total revenue"), null, 200);

    }
}

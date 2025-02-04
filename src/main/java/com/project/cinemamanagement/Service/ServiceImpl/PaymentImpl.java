package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Payment;
import com.project.cinemamanagement.Entity.Ticket;
import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Enum.PAYMENTSTAT;
import com.project.cinemamanagement.Enum.SEATTYPE;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Exception.InternalErrorException;
import com.project.cinemamanagement.Exception.InvalidDataException;
import com.project.cinemamanagement.PayLoad.Inner.PaymentInner;
import com.project.cinemamanagement.PayLoad.Request.PaymentRequest;
import com.project.cinemamanagement.PayLoad.Response.PaymentResponse;
import com.project.cinemamanagement.Repository.PaymentRepository;
import com.project.cinemamanagement.Repository.TicketRepository;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Service.EmailService;
import com.project.cinemamanagement.Service.PaymentService;
import com.project.cinemamanagement.Specifications.PaymentSpecifications;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionExpireParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Override
    public String createPayment(PaymentInner paymentInner) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/v1/public/payments/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080/api/v1/public/payments/cancel?session_id={CHECKOUT_SESSION_ID}")
                .setExpiresAt((System.currentTimeMillis() / 1000L) + (60 * 30))
                .setCustomerEmail(paymentInner.getEmail())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(paymentInner.getCurrency())
                                                .setUnitAmount(paymentInner.getPrice())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData
                                                                .builder()
                                                                .setName("Thanh toan don hang" + paymentInner.getName())
                                                                .setDescription("Day la hoa don thanh toan cho don hanh: " + paymentInner.getDescription())
                                                                .addImage("https://stripe.com/img/documentation/checkout/marketplace.png")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                ).build();

        Session session = Session.create(params);
        List<Ticket> ticketList;
        Payment payment = new Payment();
        payment.setPaymentMethod("Stripe");
        payment.setPaymentPrice(paymentInner.getPrice());
        payment.setPaymentSessionId(session.getId());
        payment.setUser(paymentInner.getUser());

        ticketList = paymentInner.getTicketList();

        payment.setTicket(paymentInner.getTicketList());

        paymentRepository.save(payment);

        for (Ticket ticket : ticketList) {
            ticket.setPayment(payment);
        }
        ticketRepository.saveAll(ticketList);

        Payment payment1 = paymentRepository.findByPaymentSessionId(session.getId());
        scheduleTicketDeletion(payment1.getPaymentId(), (long) (60 * 5));
        return session.getUrl();
    }

    @Override
    public void refund(Long paymentId, Long ticketID, String email) throws StripeException {
        Ticket ticket = ticketRepository.findById(ticketID).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotFoundException("Payment not found"));
        if (payment.getUser() != user) {
            throw new InvalidDataException("Email is not correct");
        }
        if (ticket.getPayment() != payment) {
            throw new InvalidDataException("Ticket is not belong to payment");
        }
        if (ticket.getPayment().getPaymentStatus() == null) {
            throw new InvalidDataException("Ticket is not paid");
        }
        String sessionId = payment.getPaymentSessionId();
        Session session = Session.retrieve(sessionId);
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(session.getPaymentIntent())
                .setCustomer(session.getCustomer())
                .setAmount(ticket.getSeat().getSeatType() == SEATTYPE.DOUBLE ? ticket.getShowTime().getPrice() * 2 : ticket.getShowTime().getPrice())
                .build();

        Refund.create(params);
        long newPrice = payment.getPaymentPrice() - (ticket.getSeat().getSeatType() == SEATTYPE.DOUBLE ? ticket.getShowTime().getPrice() * 2 : ticket.getShowTime().getPrice());
        payment.setPaymentStatus(PAYMENTSTAT.REFUND);
        payment.setPaymentPrice(newPrice);
        ticketRepository.delete(ticket);
    }

    @Override
    public void addPayment(PaymentRequest paymentRequest) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Long s : paymentRequest.getTicketId()) {
            Ticket ticket = ticketRepository.findById(s).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
            if (ticket.getPayment() != null) {
                throw new DataNotFoundException("Ticket has been paid");
            }
            ticketList.add(ticket);
        }
        Payment payment = new Payment(paymentRequest);
        User user = userRepository.findById(paymentRequest.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        payment.setUser(user);
        payment.setTicket(ticketList);
        paymentRepository.save(payment);
        for (Ticket ticket : ticketList) {
            ticket.setPayment(payment);
        }
        ticketRepository.saveAll(ticketList);
    }

    @Override
    public void updatePayment(PaymentRequest paymentRequest, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotFoundException("Payment not found"));
        User user = userRepository.findById(paymentRequest.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));

        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus(PAYMENTSTAT.valueOf(paymentRequest.getPaymentStatus()));
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setPaymentPrice(paymentRequest.getPaymentPrice());
        payment.setUser(user);
        List<Ticket> ticketList;
        ticketList = ticketRepository.findAllByPaymentPaymentId(paymentId);
        for (Ticket ticket : ticketList) {
            ticket.setPayment(null);
        }
        ticketList = new ArrayList<>();
        for (Long s : paymentRequest.getTicketId()) {
            Ticket ticket = ticketRepository.findById(s).orElseThrow(() -> new DataNotFoundException("Ticket not found"));
            ticketList.add(ticket);
        }
        payment.setTicket(ticketList);
        paymentRepository.save(payment);
        for (Ticket ticket : ticketList) {
            ticket.setPayment(payment);
        }
        ticketRepository.saveAll(ticketList);

    }

    @Override
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotFoundException("Payment not found"));
        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayment() {
        List<Payment> paymentList = paymentRepository.findAll();
        List<PaymentResponse> paymentResponseList = new ArrayList<>();
        for (Payment payment : paymentList) {
            paymentResponseList.add(new PaymentResponse(payment));
        }
        return paymentResponseList;
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotFoundException("Payment not found"));
        return new PaymentResponse(payment);
    }

    @Override
    public void successPayment(String session_id) throws ParseException {
        Payment payment = paymentRepository.findByPaymentSessionId(session_id);
        payment.setPaymentStatus(PAYMENTSTAT.PAID);
        Date currentDate = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(currentDate);

        List<Ticket> tickets = payment.getTicket();
        StringBuilder text = new StringBuilder("Payment success <br>");
        text.append("Film ").append(tickets.get(0).getShowTime().getMovie().getMovieName()).append("<br>");
        text.append("Room ").append(tickets.get(0).getShowTime().getRoom().getRoomID()).append("<br>");
        text.append("At ").append(tickets.get(0).getShowTime().getTimeStart()).append("<br>");
        text.append("seat location ");
        for (Ticket ticket : tickets) {
            text.append(ticket.getSeatLocation()).append(" ");
        }
        emailService.sendEmail(payment.getUser().getEmail(), "Payment Success", text.toString());

        Date formattedDateObject = formatter.parse(formattedDate);
        payment.setPaymentDate(formattedDateObject);
        paymentRepository.save(payment);
    }

    @Override
    public void cancelPayment(String session_id) {
        Payment payment = paymentRepository.findByPaymentSessionId(session_id);
        paymentRepository.delete(payment);

    }

    @Override
    public void scheduleTicketDeletion(Long paymentId, Long expiresAt) {
        long delay = expiresAt * 1000L;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotFoundException("Payment not found"));
            expiredPayment(payment.getPaymentSessionId());
            cancelPayment(payment.getPaymentSessionId());
        }, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void expiredPayment(String sessionId) {
        try {
            Session resource =
                    Session.retrieve(
                            sessionId
                    );
            SessionExpireParams params = SessionExpireParams.builder().build();
            resource.expire(params);
        } catch (StripeException e) {
            throw new InternalErrorException(e.getMessage());
        }

    }

    @Override
    public Long totalRevenue(LocalDate startDate, LocalDate endDate) {
        Specification<Payment> spec = PaymentSpecifications.getRevenue(startDate, endDate);
        return paymentRepository.calculateTotalRevenue(spec);
    }

}

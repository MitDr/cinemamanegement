package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket>{
    List<Ticket> findAllByPaymentPaymentId(Long paymentId);
    List<Ticket> findBySeat(Seat seat);

    boolean existsBySeatSeatNumberAndShowTimeShowTimeId(String seatNumber, Long showTimeId);
}

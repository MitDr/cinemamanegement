package com.project.cinemamanagement.Specifications;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Entity.Ticket;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class SeatSpecifications {
    public static Specification<Seat> getSeatByRoomType(String roomType) {
        return (root, query, criteriaBuilder) -> {
            Join<Seat, Room> join = root.join("room");
            return criteriaBuilder.equal(join.get("roomType"), roomType);
        };
    }

    public static Specification<Seat> getUntakenSeat(Long showtimeId){
        return ((root, query, criteriaBuilder) -> {

            Join<Seat,Room> roomJoin = root.join("room");
            Join<Room, ShowTime> showtimeJoin = root.join("showtime");
            Join<ShowTime, Ticket> ticketJoin = root.join("ticket");
            Predicate showtimePredicate = criteriaBuilder.equal(showtimeJoin.get("idTicket"), showtimeId);

            Subquery <String> subquery = query.subquery(String.class);
            Root<Ticket> ticketSubqueryRoot = subquery.from(Ticket.class);
            subquery.select(ticketSubqueryRoot.get("seatLocation")).where(criteriaBuilder.equal(ticketSubqueryRoot.get("showTime"), showtimeJoin.get("idTicket")));
            Predicate seatNotInTicketPredicate = criteriaBuilder.not(root.get("seatNumber").in(subquery));

            Predicate finalPredicate = criteriaBuilder.and(showtimePredicate, seatNotInTicketPredicate);

            return finalPredicate;
        });
    }
}
package com.project.cinemamanagement.Specifications;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Entity.Ticket;
import jakarta.persistence.criteria.*;
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
            Join<Seat, Room> roomJoin = root.join("room");
            Join<Room, ShowTime> showtimeJoin = roomJoin.join("showTime");

            Subquery<String> subquery = query.subquery(String.class);
            Root<ShowTime> subRoot = subquery.from(ShowTime.class);
            Join<ShowTime, Ticket> ticketJoin = subRoot.join("ticket");

            subquery.select(ticketJoin.get("seatLocation"))
                    .where(criteriaBuilder.equal(subRoot.get("showTimeId"), showtimeId));

            Predicate finalPredicate = criteriaBuilder.not(root.get("seatNumber").in(subquery));

            return finalPredicate;
        });
    }
    public static Specification<Seat> getAllSeatbyShowtimeId(Long showtimeId){
        return ((root, query, criteriaBuilder) -> {
            Join<Seat, Room> roomJoin = root.join("room");
            Join<Room, ShowTime> showtimeJoin = roomJoin.join("showTime");

            Predicate showtimePredicate = criteriaBuilder.equal(showtimeJoin.get("showTimeId"), showtimeId);

            Predicate finalPredicate = criteriaBuilder.and(showtimePredicate);

            return finalPredicate;
        });
    }
}
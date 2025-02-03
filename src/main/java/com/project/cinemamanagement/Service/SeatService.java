package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.PayLoad.Request.SeatRequest;
import com.project.cinemamanagement.PayLoad.Response.SeatResponse;
import com.project.cinemamanagement.PayLoad.Response.SeatResponseUser;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SeatService {
    List<SeatResponse> getAllSeat();
    void addSeat(SeatRequest seatRequest);
    Page<SeatResponse> getAllSeatPaging(int pageNumber, int pageSize);
    SeatResponse getSeatById(Long seatId);
    void updateSeat(Long seatId, SeatRequest seat);
    void deleteSeat(Long seatId);
    List<SeatResponse> getAllSeatResponseByRoomType(String roomType);
    List<SeatResponse> getUntakenSeat(Long showtimeId);
    List<SeatResponse> getAllSeatbyShowtimeId(Long showtimeId);
    List<SeatResponseUser> getRealAllSeat(Long showtimeId);
}

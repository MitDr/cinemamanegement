package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.PayLoad.Request.SeatRequest;
import com.project.cinemamanagement.PayLoad.Response.SeatResponse;

import java.util.List;

public interface SeatService {
    List<SeatResponse> getAllSeat();
    List<SeatResponse> getSeatByScreenId(Long screenId);
    SeatResponse addSeat(SeatRequest seatRequest);
    SeatResponse getSeatById(Long seatId);
    SeatResponse updateSeat(Long seatId, SeatRequest seat);
    SeatResponse deleteSeat(Long seatId);
    List<SeatResponse> getAllSeatResponse();
    List<SeatResponse> getAllSeatResponseByRoomType(String roomType);
    List<SeatResponse> getUntakenSeat(Long showtimeId);
    List<SeatResponse> getAllSeatbyShowtimeId(Long showtimeId);
}

package com.project.cinemamanagement.PayLoad.Response;

import com.project.cinemamanagement.Entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long SeatId;
    private int SeatStatus;
    private String SeatNumber;
    private String SeatType;

    public SeatResponse(Seat seat) {
        this.SeatId = seat.getSeatID();
        this.SeatStatus = seat.getSeatStatus();
        this.SeatNumber = seat.getSeatNumber();
        this.SeatType = seat.getSeatType();
    }
}

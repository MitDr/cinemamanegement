package com.project.cinemamanagement.PayLoad.Response;

import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Enum.SEATTYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseUser {
    private Long SeatId;
    private int SeatStatus;
    private String SeatNumber;
    private SEATTYPE SeatType;
    private Long roomId;
    public SeatResponseUser(Seat seat) {

        this.SeatId = seat.getSeatID();
        this.SeatNumber = seat.getSeatNumber();
        this.SeatType = seat.getSeatType();
        this.roomId = seat.getRoom().getRoomID();

    }
}

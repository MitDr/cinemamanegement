package com.project.cinemamanagement.PayLoad.Response;

import com.project.cinemamanagement.Enum.SEATSTAT;
import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Enum.SEATTYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long SeatId;
    private SEATSTAT SeatStatus;
    private String SeatNumber;
    private SEATTYPE SeatType;
    private Long roomId;

    public SeatResponse(Seat seat) {
        this.SeatId = seat.getSeatID();
        this.SeatStatus = seat.getSeatStatus();
        this.SeatNumber = seat.getSeatNumber();
        this.SeatType = seat.getSeatType();
        if (seat.getRoom() != null){
            this.roomId = seat.getRoom().getRoomID();
        }else this.roomId = null;
    }
}

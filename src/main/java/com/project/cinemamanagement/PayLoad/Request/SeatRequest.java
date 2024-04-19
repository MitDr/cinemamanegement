package com.project.cinemamanagement.PayLoad.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {
    private int SeatStatus;
    private String SeatNumber;
    private String SeatType;

    private Long RoomId;
}

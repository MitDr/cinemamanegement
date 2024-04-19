package com.project.cinemamanagement.PayLoad.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long SeatId;
    private int SeatStatus;
    private int SeatNumber;
    private String SeatType;
}

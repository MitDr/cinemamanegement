package com.project.cinemamanagement.PayLoad.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {
    @NotBlank(message = "SeatStatus must not be blank")
    private int SeatStatus;
    @NotBlank(message = "SeatNumber must not be blank")
    private String SeatNumber;
    @NotBlank(message = "SeatType must not be blank")
    private String SeatType;
    @NotBlank(message = "RoomId must not be blank")
    private Long RoomId;
}

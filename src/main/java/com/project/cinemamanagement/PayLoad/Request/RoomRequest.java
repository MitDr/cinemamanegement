package com.project.cinemamanagement.PayLoad.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    @NotBlank(message = "status must not be blank")
    private int Status;
    @NotBlank(message = "SeatQuantity must not be blank")
    private int SeatQuantity;
    @NotBlank(message = "RoomType must not be blank")
    private String RoomType;
}

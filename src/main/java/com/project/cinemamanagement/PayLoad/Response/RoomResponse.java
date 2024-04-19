package com.project.cinemamanagement.PayLoad.Response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoomResponse {
    private Long roomId;
    private int status;
    private int seatQuantity;
    private String roomType;
}

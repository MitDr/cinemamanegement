package com.project.cinemamanagement.PayLoad.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private int Status;
    private int SeatQuantity;
    private String RoomType;
}

package com.project.cinemamanagement.PayLoad.Response;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Enum.ROOMSTAT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoomResponse {
    private Long roomId;
    private ROOMSTAT status;
    private int seatQuantity;
    private String roomType;

    public RoomResponse(Room room) {
        this.roomId = room.getRoomID();
        this.status = room.getStatus();
        this.seatQuantity = room.getSeatQuantity();
        this.roomType = room.getRoomType();
    }
}

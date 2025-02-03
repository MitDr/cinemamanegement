package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.PayLoad.Request.RoomRequest;
import com.project.cinemamanagement.PayLoad.Response.RoomResponse;

import java.util.List;

public interface RoomService {

    List<RoomResponse> getAllRoom();

    RoomResponse getRoomByRoomId(Long roomId);

    void addRoom(RoomRequest roomRequest);

    void updateRoom(Long roomId, RoomRequest roomRequest);

    void deleteRoom(Long roomId);
}

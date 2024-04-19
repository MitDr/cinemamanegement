package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.PayLoad.Response.RoomResponse;

import java.util.List;

public interface RoomService {

    List<RoomResponse> getAllRoom();

    RoomResponse getRoomByRoomId(Long roomId);

    RoomResponse addRoom(Room room);

    RoomResponse updateRoom(Long roomId, Room room);

    RoomResponse deleteRoom(Long roomId);
}

package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.PayLoad.Response.RoomResponse;
import com.project.cinemamanagement.Repository.RoomRepository;
import com.project.cinemamanagement.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Override
    public List<RoomResponse> getAllRoom() {
        List<Room> temp;
        List<RoomResponse> roomResponseList = new ArrayList<>();
        temp = roomRepository.findAll();
        for (Room room: temp) {
            roomResponseList.add(new RoomResponse(room.getRoomID(),room.getStatus(),room.getSeatQuantity(), room.getRoomType()));
        }

        return roomResponseList;
    }

    @Override
    public RoomResponse getRoomByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        return new RoomResponse(room.getRoomID(),room.getStatus(),room.getSeatQuantity(), room.getRoomType());
    }

    @Override
    public RoomResponse addRoom(Room room) {
        roomRepository.save(room);
        return new RoomResponse(room.getRoomID(),room.getStatus(),room.getSeatQuantity(), room.getRoomType()) ;
    }

    @Override
    public RoomResponse updateRoom(Long roomId, Room room) {
        Room temp = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        temp.setRoomType(room.getRoomType());
        temp.setSeatQuantity(room.getSeatQuantity());
        temp.setStatus(room.getStatus());
        roomRepository.save(temp);
        return new RoomResponse(temp.getRoomID(),temp.getStatus(),temp.getSeatQuantity(), temp.getRoomType());
    }

    @Override
    public RoomResponse deleteRoom(Long roomId) {
        Room temp = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        roomRepository.delete(temp);
        return new RoomResponse(temp.getRoomID(),temp.getStatus(),temp.getSeatQuantity(), temp.getRoomType());
    }
}

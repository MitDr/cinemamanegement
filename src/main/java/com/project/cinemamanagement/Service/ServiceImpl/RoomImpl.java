package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.PayLoad.Request.RoomRequest;
import com.project.cinemamanagement.PayLoad.Response.RoomResponse;
import com.project.cinemamanagement.Repository.RoomRepository;
import com.project.cinemamanagement.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAllRoom() {
        List<Room> rooms = roomRepository.findAllByOrderByRoomID();
        return new ArrayList<>(rooms.stream().map(RoomResponse::new).toList());
    }

    @Override
    public RoomResponse getRoomByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        return new RoomResponse(room);
    }

    @Override
    public void addRoom(RoomRequest roomRequest) {
        Room room = new Room(roomRequest);
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(Long roomId, RoomRequest room) {
        Room temp = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        temp.setRoomType(room.getRoomType());
        temp.setStatus(room.getStatus());
        roomRepository.save(temp);

    }

    @Override
    public void deleteRoom(Long roomId) {
        Room temp = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        roomRepository.delete(temp);

    }
}

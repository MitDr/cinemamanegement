package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.PayLoad.Request.RoomRequest;
import com.project.cinemamanagement.PayLoad.Response.RoomResponse;
import com.project.cinemamanagement.Repository.RoomRepository;
import com.project.cinemamanagement.Service.BaseService;
import com.project.cinemamanagement.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomImpl extends BaseService<Room, Long> implements RoomService  {

    private final RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAllRoom() {
        List<Room> rooms = getAll();
        return new ArrayList<>(rooms.stream().map(RoomResponse::new).toList());
    }

    @Override
    public RoomResponse getRoomByRoomId(Long roomId) {
        Room room = getById(roomId);
        return new RoomResponse(room);
    }

    @Override
    public void addRoom(RoomRequest roomRequest) {
        Room room = new Room(roomRequest);
        create(room);
    }

    @Override
    public void updateRoom(Long roomId, RoomRequest room) {
        Room existingRoom = getById(roomId);
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setStatus(room.getStatus());
        update(roomId, existingRoom);

    }

    @Override
    public void deleteRoom(Long roomId) {
        delete(roomId);

    }

    @Override
    protected JpaRepository<Room, Long> getRepository() {
        return roomRepository;
    }

}

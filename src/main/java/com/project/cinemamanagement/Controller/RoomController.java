package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> getAllRoom() {
        return new ResponseEntity<MyResponse>(new MyResponse(roomService.getAllRoom(),null), null, 200);
    }
    @GetMapping("/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> getRoomByRoomId(Long roomId) {
        return new ResponseEntity<MyResponse>(new MyResponse(roomService.getRoomByRoomId(roomId),null), null, 200);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Add new room successfully"), null, 200);
    }
    @PutMapping("/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> updateRoom(Long roomId, Room room) {
        roomService.updateRoom(roomId, room);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Update room successfully"), null, 200);
    }
    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> deleteRoom(Long roomId) {
        roomService.deleteRoom(roomId);;
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Delete room successfully"), null, 200);
    }
}

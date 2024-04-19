package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping
    private ResponseEntity<MyResponse> getAllRoom() {
        try {
            return new ResponseEntity<MyResponse>(new MyResponse(roomService.getAllRoom(),null), null, 200);
        } catch (Exception e) {
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null), null, 404);
        }
    }
    @GetMapping("/{roomId}")
    private ResponseEntity<MyResponse> getRoomByRoomId(Long roomId) {
        try {
            return new ResponseEntity<MyResponse>(new MyResponse(roomService.getRoomByRoomId(roomId),null), null, 200);
        } catch (Exception e) {
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null), null, 404);
        }
    }
    @PostMapping
    private ResponseEntity<MyResponse> addRoom(@RequestBody Room room) {
        try {
            return new ResponseEntity<MyResponse>(new MyResponse(roomService.addRoom(room), null), null, 200);
        } catch (Exception e) {
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(), null), null, 404);
        }
    }
    @PutMapping("/{roomId}")
    private ResponseEntity<MyResponse> updateRoom(Long roomId, Room room) {
        try {
            return new ResponseEntity<MyResponse>(new MyResponse(roomService.updateRoom(roomId, room), null), null, 200);
        } catch (Exception e) {
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(), null), null, 404);
        }
    }
    @DeleteMapping("/{roomId}")
    private ResponseEntity<MyResponse> deleteRoom(Long roomId) {
        try {
            return new ResponseEntity<MyResponse>(new MyResponse(roomService.deleteRoom(roomId), null), null, 200);
        } catch (Exception e) {
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(), null), null, 404);
        }
    }
}

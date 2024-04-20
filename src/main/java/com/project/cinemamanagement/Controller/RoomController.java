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
        return new ResponseEntity<MyResponse>(new MyResponse(roomService.getAllRoom(),"Get all room"), null, 200);
    }
    @GetMapping("/{roomId}")
    private ResponseEntity<MyResponse> getRoomByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<MyResponse>(new MyResponse(roomService.getRoomByRoomId(roomId),"Get room by room id"), null, 200);
    }
    @PostMapping
    private ResponseEntity<MyResponse> addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Add new room successfully"), null, 200);
    }
    @PutMapping("/{roomId}")
    private ResponseEntity<MyResponse> updateRoom(@PathVariable Long roomId, @RequestBody Room room) {
        roomService.updateRoom(roomId, room);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Update room successfully"), null, 200);
    }
    @DeleteMapping("/{roomId}")
    private ResponseEntity<MyResponse> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Delete room successfully"), null, 200);
    }
}

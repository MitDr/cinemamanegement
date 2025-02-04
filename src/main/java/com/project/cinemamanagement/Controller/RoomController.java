package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.RoomRequest;
import com.project.cinemamanagement.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${frontend.endpoint}")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/admin/rooms")
    private ResponseEntity<MyResponse> getAllRoom() {
        return new ResponseEntity<MyResponse>(new MyResponse(roomService.getAllRoom(), "Get all room"), null, 200);
    }

    @GetMapping("/public/rooms/{roomId}")
    private ResponseEntity<MyResponse> getRoomByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<MyResponse>(new MyResponse(roomService.getRoomByRoomId(roomId), "Get room by room id"), null, 200);
    }

    @PostMapping("/admin/rooms")
    private ResponseEntity<MyResponse> addRoom(@RequestBody RoomRequest roomRequest) {
        roomService.addRoom(roomRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Add new room successfully"), null, 200);
    }

    @PutMapping("/admin/rooms/{roomId}")
    private ResponseEntity<MyResponse> updateRoom(@PathVariable Long roomId, @RequestBody RoomRequest roomRequest) {
        roomService.updateRoom(roomId, roomRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Update room successfully"), null, 200);
    }

    @DeleteMapping("/admin/rooms/{roomId}")
    private ResponseEntity<MyResponse> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Delete room successfully"), null, 200);
    }
}

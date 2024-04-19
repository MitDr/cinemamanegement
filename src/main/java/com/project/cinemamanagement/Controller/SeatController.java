package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.SeatRequest;
import com.project.cinemamanagement.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seat")
public class SeatController {
    @Autowired
    SeatService seatService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> getAllSeat(){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeat(),"Get all seat"),null,200);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> getSeatById(Long id){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getSeatById(id),"Seat with id: "+" "+id+" is get"),null,200);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> deleteSeat(Long id){
        seatService.deleteSeat(id);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Delete seat successfully"),null,200);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity<MyResponse> addSeat(@RequestBody SeatRequest seatRequest){
        seatService.addSeat(seatRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Add new seat successfully"),null,200);

    }
    @GetMapping("/room/{roomType}")
    private ResponseEntity<MyResponse> getSeatByRoomType(@PathVariable String roomType){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatResponseByRoomType(roomType),"Get all seat by room type"),null,200);
    }
    @GetMapping("showtime/allseat/{id}")
    private ResponseEntity<MyResponse> getSeatByShowtimeId(@PathVariable Long id){

        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatbyShowtimeId(id),"get seat by show time id"),null,200);
    }
    @GetMapping("showtime/untakenseat/{id}")
    private ResponseEntity<MyResponse> getUntakenSeat(@PathVariable Long id){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getUntakenSeat(id),"get untaken seat"),null,200);
    }
}

package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.SeatRequest;
import com.project.cinemamanagement.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seat")
public class SeatController {
    @Autowired
    SeatService seatService;

    @GetMapping
    private ResponseEntity<MyResponse> getAllSeat(){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeat(),"Get all seat"),null,200);
    }
    @GetMapping("/{id}")
    private ResponseEntity<MyResponse> getSeatById(@PathVariable Long id){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getSeatById(id),"Get seat by id"),null,200);
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<MyResponse> deleteSeat(@PathVariable Long id){
        seatService.deleteSeat(id);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Delete seat successfully"),null,200);
    }
    @PostMapping
    private ResponseEntity<MyResponse> addSeat(@RequestBody SeatRequest seatRequest){
        seatService.addSeat(seatRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Add new seat successfully"),null,200);
    }
    @PutMapping("/{id}")
    private ResponseEntity<MyResponse> updateSeat(@PathVariable Long id,@RequestBody SeatRequest seatRequest){
        seatService.updateSeat(id,seatRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Update seat successfully"),null,200);
    }
    @GetMapping("/room/{roomType}")
    private ResponseEntity<MyResponse> getSeatByRoomType(@PathVariable String roomType){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatResponseByRoomType(roomType),"Get seat by room type"),null,200);
    }
    @GetMapping("showtime/allseat/{showtimeId}")
    private ResponseEntity<MyResponse> getSeatByShowtimeId(@PathVariable Long showtimeId){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatbyShowtimeId(showtimeId),"Get seat by showtime"),null,200);
    }
    @GetMapping("showtime/untakenseat/{showtimeId}")
    private ResponseEntity<MyResponse> getUntakenSeat(@PathVariable Long showtimeId){
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getUntakenSeat(showtimeId),"Get untaken seat by showtime"),null,200);
    }
}

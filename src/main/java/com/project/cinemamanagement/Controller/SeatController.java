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
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeat(),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
    @GetMapping("/{id}")
    private ResponseEntity<MyResponse> getSeatById(@PathVariable Long id){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(seatService.getSeatById(id),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),"test1"),null,404);
        }
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<MyResponse> deleteSeat(@PathVariable Long id){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(seatService.deleteSeat(id),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
    @PostMapping
    private ResponseEntity<MyResponse> addSeat(@RequestBody SeatRequest seatRequest){
        try{

            return new ResponseEntity<MyResponse>(new MyResponse(seatService.addSeat(seatRequest),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
    @PutMapping("/{id}")
    private ResponseEntity<MyResponse> updateSeat(@PathVariable Long id,@RequestBody SeatRequest seatRequest){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(seatService.updateSeat(id,seatRequest),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
    @GetMapping("/room/{roomType}")
    private ResponseEntity<MyResponse> getSeatByRoomType(@PathVariable String roomType){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatResponseByRoomType(roomType),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
    @GetMapping("showtime/allseat/{id}")
    private ResponseEntity<MyResponse> getSeatByShowtimeId(@PathVariable Long id){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatbyShowtimeId(id),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
    @GetMapping("showtime/untakenseat/{id}")
    private ResponseEntity<MyResponse> getUntakenSeat(@PathVariable Long id){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(seatService.getUntakenSeat(id),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
}

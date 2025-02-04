package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.SeatRequest;
import com.project.cinemamanagement.Service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${frontend.endpoint}")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/admin/seats")
    private ResponseEntity<MyResponse> getAllSeat() {
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeat(), "Get all seat"), null, 200);
    }

    @GetMapping("/admin/seats/paging")
    private ResponseEntity<MyResponse> getAllSeatPaging(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatPaging(pageNumber, pageSize), "Get all seat paging"), null, 200);
    }

    @GetMapping("/admin/seats/{id}")
    private ResponseEntity<MyResponse> getSeatById(@PathVariable Long id) {
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getSeatById(id), "Get seat by id"), null, 200);
    }

    @DeleteMapping("/admin/seats/{id}")
    private ResponseEntity<MyResponse> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Delete seat successfully"), null, 200);
    }

    @PostMapping("/admin/seats")
    private ResponseEntity<MyResponse> addSeat(@Valid @RequestBody SeatRequest seatRequest) {
        seatService.addSeat(seatRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Add new seat successfully"), null, 200);
    }

    @PutMapping("/admin/seats/{id}")
    private ResponseEntity<MyResponse> updateSeat(@PathVariable Long id, @RequestBody SeatRequest seatRequest) {
        seatService.updateSeat(id, seatRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Update seat successfully"), null, 200);
    }

    @GetMapping("/admin/seats/roomtype")
    private ResponseEntity<MyResponse> getSeatByRoomType(@RequestParam String roomType) {
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatResponseByRoomType(roomType), "Get seat by room type"), null, 200);
    }

    @GetMapping("/admin/showtime/allseat/{showtimeId}")
    private ResponseEntity<MyResponse> getSeatByShowtimeId(@PathVariable Long showtimeId) {
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getAllSeatbyShowtimeId(showtimeId), "Get seat by showtime"), null, 200);
    }

    @GetMapping("/admin/showtime/untakenseat/{showtimeId}")
    private ResponseEntity<MyResponse> getUntakenSeat(@PathVariable Long showtimeId) {
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getUntakenSeat(showtimeId), "Get untaken seat by showtime"), null, 200);
    }

    @GetMapping("/public/showtime/seat/{showtimeId}")
    private ResponseEntity<MyResponse> getTakenSeat(@PathVariable Long showtimeId) {
        return new ResponseEntity<MyResponse>(new MyResponse(seatService.getRealAllSeat(showtimeId), "Get all seat by showtime"), null, 200);
    }
}

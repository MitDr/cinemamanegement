package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.ShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Response.ShowtimeResponse;
import com.project.cinemamanagement.Service.MovieService;
import com.project.cinemamanagement.Service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${frontend.endpoint}")
@RequestMapping("/api/v1")
public class ShowTimeController {
    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping("/admin/showtimes")
    public ResponseEntity<MyResponse> getAllShowTime() {
        return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.getAllShowTime(), "get all show time"), null, 200);
    }

    @GetMapping("/public/showtimes/{showTimeId}")
    private ResponseEntity<MyResponse> getShowTimeById(@PathVariable Long showTimeId) {
        return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.getShowTimeById(showTimeId), "get show time by id"), null, 200);
    }

    @GetMapping("/public/showtimes/movie")
    private ResponseEntity<MyResponse> getShowTimeByMovieId(@RequestParam(value = "movieId") Long movie) {
        return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.getShowTimeByMovieId(movie), "get all show time by movie id"), null, 200);
    }

    @PostMapping("/admin/showtimes")
    private ResponseEntity<MyResponse> addShowTime(@RequestBody ShowtimeRequest showTime) {
        showTimeService.addShowTime(showTime);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Add new show time successfully"), null, 200);
    }

    @PutMapping("/admin/showtimes/{showTimeId}")
    private ResponseEntity<MyResponse> updateShowTime(@PathVariable Long showTimeId, @RequestBody ShowtimeRequest showTime) {
        showTimeService.updateShowTime(showTimeId, showTime);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Update show time successfully"), null, 200);
    }

    @DeleteMapping("/admin/showtimes/{showTimeId}")
    private ResponseEntity<MyResponse> deleteShowTime(@PathVariable Long showTimeId) {
        showTimeService.deleteShowTime(showTimeId);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "delete show time successfully"), null, 200);
    }
}

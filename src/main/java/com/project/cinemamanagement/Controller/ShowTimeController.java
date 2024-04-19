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
@RequestMapping("/api/v1/showtime")
public class ShowTimeController {
    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<MyResponse> getAllShowTime(){
        return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.getAllShowTime(),null),null,200);
    }

    @GetMapping("/{showTimeId}")
    private ResponseEntity<MyResponse> getShowTimeById(@PathVariable Long showTimeId){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.getShowTimeById(showTimeId),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }

    @GetMapping("/movie")
    private ResponseEntity<MyResponse> getShowTimeByMovieId(@RequestParam(value = "movieId") Long movie){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.getShowTimeByMovieId(movie),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }

    }

    @PostMapping
    private ResponseEntity<MyResponse> addShowTime(@RequestBody ShowtimeRequest showTime){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.addShowTime(showTime),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }

    @PutMapping("/{showTimeId}")
    private ResponseEntity<MyResponse> updateShowTime(@PathVariable Long showTimeId,@RequestBody ShowtimeRequest showTime){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.updateShowTime(showTimeId,showTime),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }

    @DeleteMapping("/{showTimeId}")
    private ResponseEntity<MyResponse> deleteShowTime(@PathVariable Long showTimeId){
        try{
            return new ResponseEntity<MyResponse>(new MyResponse(showTimeService.deleteShowTime(showTimeId),null),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<MyResponse>(new MyResponse(e.getMessage(),null),null,404);
        }
    }
}

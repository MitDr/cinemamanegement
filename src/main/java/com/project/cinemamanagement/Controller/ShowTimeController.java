package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;
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
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAllShowTime(){
        return new ResponseEntity<>(showTimeService.getAllShowTime(),null,200);
    }

    @GetMapping("/{showTimeId}")
    private ResponseEntity<?> getShowTimeById(@PathVariable Long showTimeId){
        try{
            return new ResponseEntity<>(showTimeService.getShowTimeById(showTimeId),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @GetMapping("/movie")
    private ResponseEntity<?> getShowTimeByMovieId(@RequestParam(value = "movieId") Long movie){
        try{
            return new ResponseEntity<>(showTimeService.getShowTimeByMovieId(movie),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }

    }

    @PostMapping
    private ResponseEntity<?> addShowTime(@RequestBody ShowTime showTime){
        try{
            return new ResponseEntity<>(showTimeService.addShowTime(showTime),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @PutMapping("/{showTimeId}")
    private ResponseEntity<?> updateShowTime(@PathVariable Long showTimeId,@RequestBody ShowTime showTime){
        try{
            return new ResponseEntity<>(showTimeService.updateShowTime(showTimeId,showTime),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @DeleteMapping("/{showTimeId}")
    private ResponseEntity<?> deleteShowTime(@PathVariable Long showTimeId){
        try{
            return new ResponseEntity<>(showTimeService.deleteShowTime(showTimeId),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }
}

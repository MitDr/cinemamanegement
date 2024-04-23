package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.Service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;


    @GetMapping
    public ResponseEntity<MyResponse> getAllMovie(){
        return new ResponseEntity<MyResponse>(new MyResponse(movieService.getAllMovie(),"Get all movie"),null,200);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MyResponse> getMovieById(@PathVariable Long movieId){
        return new ResponseEntity<MyResponse>(new MyResponse(movieService.getMovieById(movieId),"Movie with id: "+" "+movieId+" is get"),null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MyResponse> addMovie(@Valid @RequestBody Movie movie){
        movieService.addMovie(movie);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Add new movie successfully"),null,HttpStatus.CREATED);
    }

    @PutMapping("/{movieId}")
    private ResponseEntity<MyResponse> updateMovie(@PathVariable Long movieId,@Valid @RequestBody Movie movie){
        movieService.updateMovie(movieId,movie);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Update movie successfully"),null,HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}")
    private ResponseEntity<MyResponse> deleteMovie(@PathVariable Long movieId){
        movieService.deleteMovie(movieId);
        return new ResponseEntity<MyResponse>(new MyResponse(null,"Delete movie successfully"),null,HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<MyResponse> getMovieByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<MyResponse>(new MyResponse(movieService.getMovieByDate(date),"Get movie by date"),null,HttpStatus.OK);
    }
}

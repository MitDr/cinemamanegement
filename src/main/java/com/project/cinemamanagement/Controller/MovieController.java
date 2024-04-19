package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;


    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAllMovie(){
        return new ResponseEntity<>(movieService.getAllMovie(),null,200);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieById(@PathVariable Long movieId){
        try{
            return new ResponseEntity<>(movieService.getMovieById(movieId),null, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie){
        return new ResponseEntity<>(movieService.addMovie(movie),null,HttpStatus.CREATED);
    }

    @PutMapping("/{movieId}")
    private ResponseEntity<?> updateMovie(@PathVariable Long movieId,@RequestBody Movie movie){
        try{
            return new ResponseEntity<>(movieService.updateMovie(movieId,movie),null,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{movieId}")
    private ResponseEntity<?> deleteMovie(@PathVariable Long movieId){
        try{
            return new ResponseEntity<>(movieService.deleteMovie(movieId),null,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,HttpStatus.NOT_FOUND);
        }
    }
// Payload if needed
//    @GetMapping("/payload")
//    private ResponseEntity<?> getMovieList(){
//        return new ResponseEntity<>(movieService.getMovieList(),null,HttpStatus.OK);
//    }
//
//    @PutMapping("/payload/{movieId}")
//    private ResponseEntity<?> updateMovieStatus(@PathVariable Long movieId, @RequestBody MovieResponse movieResponse){
//        try{
//            Movie updateMovie = movieService.getMovieById(movieId);
//            updateMovie.setStatus(movieResponse.getStatus());
//            updateMovie.setReleaseDate(movieResponse.getReleaseDate());
//            updateMovie.setEndDate(movieResponse.getEndDate());
//            updateMovie.setAgeRestriction(movieResponse.getAgeRestriction());
//            return new ResponseEntity<>(movieService.updateMovie(movieId,updateMovie),null,HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),null,HttpStatus.NOT_FOUND);
//        }
//    }
}

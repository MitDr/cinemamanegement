package com.project.cinemamanagement.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.MyResponse.ErrorResponse;
import com.project.cinemamanagement.MyResponse.MyResponse;
import com.project.cinemamanagement.PayLoad.Request.MovieRequest;
import com.project.cinemamanagement.PayLoad.Request.MovieShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.Service.MovieService;
import com.project.cinemamanagement.Ultility.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@CrossOrigin(origins = "${frontend.endpoint}")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/public/movies")
    public ResponseEntity<MyResponse> getAllMovie() {
        return new ResponseEntity<MyResponse>(new MyResponse(movieService.getAllMovie(), "Get all movie"), null, 200);
    }

    @GetMapping("/public/movies/{movieId}")
    public ResponseEntity<MyResponse> getMovieById(@PathVariable Long movieId) {
        return new ResponseEntity<MyResponse>(new MyResponse(movieService.getMovieById(movieId), "Movie with id: " + " " + movieId + " is get"), null, HttpStatus.OK);
    }

    @PostMapping(consumes = "multipart/form-data", value = "/admin/movies")
    public ResponseEntity<?> addMovie(@Valid @ModelAttribute MovieRequest movieRequest, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors:");
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            });
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value()), null, HttpStatus.BAD_REQUEST);
        }
        if (movieRequest.getImage() != null && !Validator.isValidFile(movieRequest.getImage())) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Image is not valid", HttpStatus.BAD_REQUEST.value()), null, HttpStatus.BAD_REQUEST);
        }
        movieService.addMovie(movieRequest);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Add new movie successfully"), null, HttpStatus.CREATED);
    }

//    @PutMapping("/{movieId}")
//    private ResponseEntity<MyResponse> updateMovie(@PathVariable Long movieId,@Valid @RequestBody MovieRequest movie){
//        movieService.updateMovie(movieId,movie);
//        return new ResponseEntity<MyResponse>(new MyResponse(null,"Update movie successfully"),null,HttpStatus.OK);
//    }

    @PutMapping("/admin/movies/{movieId}")
    public ResponseEntity<?> updateMovie(@Valid @ModelAttribute MovieRequest movieRequest, BindingResult bindingResult, @PathVariable Long movieId) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value()), null, HttpStatus.BAD_REQUEST);
        }
        if (!movieRequest.getImage().isEmpty() && !Validator.isValidFile(movieRequest.getImage())) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Image is not valid", HttpStatus.BAD_REQUEST.value()), null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<MyResponse>(new MyResponse(movieService.updateMovie(movieId, movieRequest), "Update movie successfully"), null, HttpStatus.OK);
    }

    @DeleteMapping("/admin/movies/{movieId}")
    private ResponseEntity<MyResponse> deleteMovie(@PathVariable Long movieId) throws IOException {
        movieService.deleteMovie(movieId);
        return new ResponseEntity<MyResponse>(new MyResponse(null, "Delete movie successfully"), null, HttpStatus.OK);
    }

    @GetMapping("/public/movies/date")
    public ResponseEntity<MyResponse> getMovieByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return new ResponseEntity<MyResponse>(new MyResponse(movieService.getMovieByDate(date), "Get movie by date"), null, HttpStatus.OK);
    }

    //WIP
//    @PostMapping(value = "/showtime", consumes = "multipart/form-data")
//    public ResponseEntity<?> addMovieShowtime(@Valid @ModelAttribute MovieShowtimeRequest movieShowtimeRequest, BindingResult bindingResult) throws JsonProcessingException {
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<ErrorResponse>(new ErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value()), null, HttpStatus.BAD_REQUEST);
//        }
//        if (!Validator.isValidFile(movieShowtimeRequest.getImage())) {
//            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Image is not valid", HttpStatus.BAD_REQUEST.value()), null, HttpStatus.BAD_REQUEST);
//        }
//        movieService.addMovieShowtime(movieShowtimeRequest);
//        return new ResponseEntity<>(new MyResponse(null, "Add new movie showtime successfully"), null, HttpStatus.OK);
//    }
}

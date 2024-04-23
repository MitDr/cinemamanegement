package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.PayLoad.Response.MovieShowtimeResponse;

import java.util.Date;
import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovie();
    MovieResponse addMovie(Movie movie);
    MovieShowtimeResponse getMovieById(Long movieId);
    MovieResponse updateMovie(Long movieId, Movie movie);
    MovieResponse deleteMovie(Long movieId);
    //List<MovieRequest> getMovieList();
    List<MovieResponse> getMovieByDate(Date date);
}

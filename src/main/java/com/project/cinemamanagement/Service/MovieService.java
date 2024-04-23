package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Movie;

import java.util.Date;
import java.util.List;

public interface MovieService {
    List<Movie> getAllMovie();
    Movie addMovie(Movie movie);
    Movie getMovieById(Long movieId);
    Movie updateMovie(Long movieId, Movie movie);
    Movie deleteMovie(Long movieId);
    //List<MovieRequest> getMovieList();
    List<Movie> getMovieByDate(Date date);
}

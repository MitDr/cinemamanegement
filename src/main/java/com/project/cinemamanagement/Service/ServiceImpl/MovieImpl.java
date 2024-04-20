package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Repository.MovieRepository;
import com.project.cinemamanagement.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public List<Movie> getAllMovie() {
        return movieRepository.findAll();
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));
    }

    @Override
    public Movie updateMovie(Long movieId, Movie movie) {
        Movie updateMovie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));

        updateMovie.setMovieName(movie.getMovieName());
        updateMovie.setMovieGenre(movie.getMovieGenre());
        updateMovie.setDescription(movie.getDescription());
        updateMovie.setDuration(movie.getDuration());
        updateMovie.setDirector(movie.getDirector());
        updateMovie.setActor(movie.getActor());
        updateMovie.setReleaseDate(movie.getReleaseDate());
        updateMovie.setEndDate(movie.getEndDate());
        updateMovie.setAgeRestriction(movie.getAgeRestriction());
        updateMovie.setUrlTrailer(movie.getUrlTrailer());
        updateMovie.setStatus(movie.getStatus());

        return movieRepository.save(updateMovie);
    }

    @Override
    public Movie deleteMovie(Long movieId) {
        Movie deleteMovie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));

        movieRepository.delete(deleteMovie);
        return deleteMovie;
    }

}

package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.PayLoad.Response.MovieShowtimeResponse;
import com.project.cinemamanagement.PayLoad.Response.SeatResponse;
import com.project.cinemamanagement.PayLoad.Response.ShowtimeResponse;
import com.project.cinemamanagement.Repository.MovieRepository;
import com.project.cinemamanagement.Service.MovieService;
import com.project.cinemamanagement.Service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowTimeService showTimeService;
    @Override
    public List<MovieResponse> getAllMovie() {
        List<Movie> movieList = movieRepository.findAllByOrderByMovieId();
        List<MovieResponse> movieResponses = new ArrayList<>();
        for (Movie movie: movieList) {
            movieResponses.add(new MovieResponse(movie));
        }
        return movieResponses;

    }

    @Override
    public MovieResponse addMovie(Movie movie) {
        movieRepository.save(movie);
        return new MovieResponse(movie);
    }

    @Override
    public MovieShowtimeResponse getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));
        List<ShowtimeResponse> showtimeResponseList = showTimeService.getShowTimeByMovieId(movieId);
        return new MovieShowtimeResponse(movie, showtimeResponseList);
    }

    @Override
    public MovieResponse updateMovie(Long movieId, Movie movie) {
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

        movieRepository.save(updateMovie);

        return new MovieResponse(updateMovie);
    }

    @Override
    public MovieResponse deleteMovie(Long movieId) {
        Movie deleteMovie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));

        movieRepository.delete(deleteMovie);

        return new MovieResponse(deleteMovie);
    }

}

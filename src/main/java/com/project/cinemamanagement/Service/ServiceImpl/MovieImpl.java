package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Request.MovieRequest;
import com.project.cinemamanagement.Repository.MovieRepository;
import com.project.cinemamanagement.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//    @Override
//    public List<MovieRequest> getMovieList() {
//        List<Movie> movie = movieRepository.findAll();
//        List<MovieRequest> movieRequests = new ArrayList<>();
//        for(int i=0;i<movie.size();i++){
//            MovieRequest movieRequest = new MovieRequest();
//            movieRequest.setMovieName(movie.get(i).getMovieName());
//            movieRequest.setMovieGenre(movie.get(i).getMovieGenre());
//            movieRequest.setDescription(movie.get(i).getDescription());
//            movieRequest.setDuration(movie.get(i).getDuration());
//            movieRequest.setDirector(movie.get(i).getDirector());
//            movieRequest.setActor(movie.get(i).getActor());
//            movieRequest.setReleaseDate(movie.get(i).getReleaseDate());
//            movieRequest.setEndDate(movie.get(i).getEndDate());
//            movieRequest.setAgeRestriction(movie.get(i).getAgeRestriction());
//            movieRequest.setUrlTrailer(movie.get(i).getUrlTrailer());
//            movieRequest.setStatus(movie.get(i).getStatus());
//            movieRequest.setUrlThumbnail(movie.get(i).getUrlThumbnail());
//
//            movieRequests.add(movieRequest);
//        }
//        return movieRequests;
//    }
}

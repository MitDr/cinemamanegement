package com.project.cinemamanagement.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.PayLoad.Request.MovieRequest;
import com.project.cinemamanagement.PayLoad.Request.MovieShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.PayLoad.Response.MovieShowtimeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovie();
    MovieResponse addMovie(MovieRequest movie) throws IOException;
    MovieShowtimeResponse getMovieById(Long movieId);
    MovieResponse updateMovie(Long movieId, MovieRequest movie) throws IOException;
    MovieResponse deleteMovie(Long movieId) throws IOException;
    //List<MovieRequest> getMovieList();
    List<MovieResponse> getMovieByDate(Date date);
    void addMovieShowtime(MovieShowtimeRequest movieShowtimeRequest) throws JsonProcessingException;
    File saveTemporaryImage(MultipartFile file, String fileName);
    String updateToCloud(File file, String movieName) throws IOException;
    void deleteTemnaryImage(File file);

    void delteFromCloud(String publicId) throws IOException;
}

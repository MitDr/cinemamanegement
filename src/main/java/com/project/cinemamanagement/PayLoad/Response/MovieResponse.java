package com.project.cinemamanagement.PayLoad.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private Long movieId;
    private String movieName;
    private String movieGenre;
    private String description;
    private int duration;
    private String director;
    private String actor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String ageRestriction;
    private String urlTrailer;
    private int status;
    private String urlThumbnail;

    public MovieResponse(Movie movie) {
        this.movieId = movie.getMovieId();
        this.movieName = movie.getMovieName();
        this.movieGenre = movie.getMovieGenre();
        this.description = movie.getDescription();
        this.duration = movie.getDuration();
        this.director = movie.getDirector();
        this.actor = movie.getActor();
        this.releaseDate = movie.getReleaseDate();
        this.endDate = movie.getEndDate();
        this.ageRestriction = movie.getAgeRestriction();
        this.urlTrailer = movie.getUrlTrailer();
        this.urlThumbnail = movie.getUrlThumbnail();
        if(movie.getReleaseDate().after(new Date()) && movie.getEndDate().after(new Date())) this.status = 0;
        else if (movie.getEndDate().before(new Date()) && movie.getReleaseDate().before(new Date())){
            this.status = 2;
        }else this.status = 1;
    }
}

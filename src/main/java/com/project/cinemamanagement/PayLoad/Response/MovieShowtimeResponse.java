package com.project.cinemamanagement.PayLoad.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieShowtimeResponse {
    private Long movieId;
    private String movieName;
    private String movieGenre;
    private String description;
    private String duration;
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

    private List<ShowtimeResponse> showTimes;

    public MovieShowtimeResponse(Movie movie, List<ShowtimeResponse> showTimes){
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
        this.status = movie.getStatus();
        this.urlThumbnail = movie.getUrlThumbnail();
        this.showTimes = showTimes;
    }
}

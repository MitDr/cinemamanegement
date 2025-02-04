package com.project.cinemamanagement.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_movie")
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_Id")
    private Long movieId;
    @Column(name = "movie_name", length = 300, unique = true)
    private String movieName;
    @Column(name = "movie_genre")
    private String movieGenre;
    @Column(name = "movie_description")
    private String description;
    @Column(name = "move_duration")
    private int duration;
    @Column(name = "movie_director")
    private String director;
    @Column(name = "movie_actor")
    private String actor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "movie_release_date")
    private Date releaseDate;
    @Column(name = "movie_end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @Column(name = "movie_age_restriction")
    private String ageRestriction;
    @Column(name = "movie_url_trailer")
    private String urlTrailer;
    @Column(name = "movie_url_thumbnail")
    private String urlThumbnail;

    @ToString.Exclude
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ShowTime> showTimes;

    public Movie(Builder builder) {
        this.movieName = builder.movieName;
        this.movieGenre = builder.movieGenre;
        this.description = builder.description;
        this.duration = builder.duration;
        this.director = builder.director;
        this.actor = builder.actor;
        this.releaseDate = builder.releaseDate;
        this.endDate = builder.endDate;
        this.ageRestriction = builder.ageRestriction;
        this.urlTrailer = builder.urlTrailer;
        this.urlThumbnail = builder.urlThumbnail;
    }

    public static class Builder {

        private String movieName;

        private String movieGenre;

        private String description;

        private int duration;

        private String director;

        private String actor;

        private Date releaseDate;

        private Date endDate;

        private String ageRestriction;

        private String urlTrailer;

        private String urlThumbnail;

        public Builder() {
        }

        public Builder setMovieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public Builder setMovieGenre(String movieGenre) {
            this.movieGenre = movieGenre;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setDirector(String director) {
            this.director = director;
            return this;
        }

        public Builder setActor(String actor) {
            this.actor = actor;
            return this;
        }

        public Builder setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setAgeRestriction(String ageRestriction) {
            this.ageRestriction = ageRestriction;
            return this;
        }

        public Builder setUrlTrailer(String urlTrailer) {
            this.urlTrailer = urlTrailer;
            return this;
        }

        public Builder setUrlThumbnail(String urlThumbnail) {
            this.urlThumbnail = urlThumbnail;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}

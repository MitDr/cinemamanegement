package com.project.cinemamanagement.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name ="tbl_movie")
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_Id")
    private Long movieId;
    @Column(name = "movie_name", length = 300)
    private String movieName;
    @Column(name = "movie_genre")
    private String movieGenre;
    @Column(name = "movie_description")
    private String description;
    @Column(name = "move_duration")
    private String duration;
    @Column(name = "movie_director" )
    private String director;
    @Column(name = "movie_actor")
    private String actor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "movie_release_date")
    private Date releaseDate;
    @Column(name = "movie_end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @Column(name = "movie_rating")
    private float rating;
    @Column(name = "movie_url_trailer")
    private String urlTrailer;
    @Column(name = "movie_status")
    private int status;
    @Column(name = "movie_url_thumbnail")
    private String urlThumbnail;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<ShowTime> showTimes = new HashSet<>();
}

package com.project.cinemamanagement.PayLoad.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {
    private String movieName;
    private String movieGenre;
    private String description;
    private String duration;
    private String director;
    private String actor;
    private Date releaseDate;
    private Date endDate;
    private String ageRestriction;
    private String urlTrailer;
    private int status;
    private String urlThumbnail;
}

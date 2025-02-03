package com.project.cinemamanagement.PayLoad.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieShowtimeRequest {
    @NotBlank
    private String movieName;
    @NotBlank
    private String movieGenre;
    @NotBlank
    private String description;
    @Positive(message = "duration is incorrect")
    private int duration;
    @NotBlank
    private String director;
    @NotBlank
    private String actor;
    @NotNull(message = "releaseDate is missing")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent
    private Date releaseDate;
    @NotNull(message = "endDate is missing")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent
    private Date endDate;
    @NotBlank
    private String ageRestriction;
    @NotBlank
    private String urlTrailer;
    @NotNull(message = "File is missing")
    private MultipartFile image;
    @NotBlank(message = "listShowtime must not be blank")
    String listShowtime;
}

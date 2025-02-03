package com.project.cinemamanagement.PayLoad.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeRequest
{
    @NotBlank(message = "timeStart must not be blank")
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;
    @NotBlank(message = "timeEnd must not be blank")
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;
    @NotBlank(message = "price must not be blank")
    @Positive(message = "price is incorrect")
    private Long price;
    @NotBlank(message = "movieId must not be blank")
    private Long movieId;
    private Long roomId;
}

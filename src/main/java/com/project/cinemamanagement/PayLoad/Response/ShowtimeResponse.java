package com.project.cinemamanagement.PayLoad.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Entity.ShowTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeResponse {

    private Long showTimeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")

    private Date timeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;
    private Long price;
    private Long movieId;
    private Long roomId;

    public ShowtimeResponse(ShowTime showTime) {
        this.showTimeId = showTime.getShowTimeId();
        this.timeStart = showTime.getTimeStart();
        this.timeEnd = showTime.getTimeEnd();
        this.price = showTime.getPrice();
        this.movieId = showTime.getMovie().getMovieId();
        this.roomId = showTime.getRoom().getRoomID();
    }
}

package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.PayLoad.Request.ShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Response.ShowtimeResponse;

import java.util.List;

public interface ShowTimeService {

    ShowtimeResponse addShowTime(ShowtimeRequest showTime);

    ShowtimeResponse getShowTimeById(Long showTimeId);

    ShowtimeResponse deleteShowTime(Long showTimeId);

    ShowtimeResponse updateShowTime(Long showTimeId, ShowtimeRequest showTime);
    List<ShowtimeResponse> getAllShowTime();
    List<ShowtimeResponse> getShowTimeByMovieId(Long movie);
    void addShowTimeList(List<ShowtimeRequest> showTimeList, Movie movie);
}

package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;

import java.util.List;

public interface ShowTimeService {

    ShowTime addShowTime(ShowTime showTime);

    ShowTime getShowTimeById(Long showTimeId);

    ShowTime deleteShowTime(Long showTimeId);

    ShowTime updateShowTime(Long showTimeId, ShowTime showTime);
    List<ShowTime> getAllShowTime();
    List<ShowTime> getShowTimeByMovieId(Long movie);
}

package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Repository.MovieRepository;
import com.project.cinemamanagement.Repository.ShowTimeRepository;
import com.project.cinemamanagement.Service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowTimeImpl implements ShowTimeService {
    @Autowired
    ShowTimeRepository showTimeRepository;
    @Autowired
    MovieRepository movieRepository;
    @Override
    public ShowTime addShowTime(ShowTime showTime) {
        if (showTime.getMovie() == null || showTime.getMovie().getMovieId() == null) {
            throw new IllegalArgumentException("movieId is required");
        }

        Long movieId = showTime.getMovie().getMovieId();
        if (!movieRepository.existsById(movieId)) {
            throw new DataNotFoundException("Movie with id " + movieId + " not found");
        }
        return showTimeRepository.save(showTime);
    }

    @Override
    public ShowTime getShowTimeById(Long showTimeId) {
        return showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
    }

    @Override
    public ShowTime deleteShowTime(Long showTimeId) {
        ShowTime deleteShowTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        showTimeRepository.delete(deleteShowTime);
        return deleteShowTime;
    }

    @Override
    public ShowTime updateShowTime(Long showTimeId, ShowTime showTime) {
        ShowTime updateShowTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));

        updateShowTime.setTimeStart(showTime.getTimeStart());
        updateShowTime.setTimeEnd(showTime.getTimeEnd());
        updateShowTime.setStatus(showTime.getStatus());
        updateShowTime.setMovie(showTime.getMovie());

        return showTimeRepository.save(updateShowTime);
    }

    @Override
    public List<ShowTime> getAllShowTime() {
        return showTimeRepository.findAll();
    }

    @Override
    public List<ShowTime> getShowTimeByMovieId(Long movie) {
        return showTimeRepository.findShowTimeByMovieMovieId(movie);
    }
}

package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Request.ShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Response.ShowtimeResponse;
import com.project.cinemamanagement.Repository.MovieRepository;
import com.project.cinemamanagement.Repository.RoomRepository;
import com.project.cinemamanagement.Repository.ShowTimeRepository;
import com.project.cinemamanagement.Service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowTimeImpl implements ShowTimeService {
    @Autowired
    ShowTimeRepository showTimeRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RoomRepository roomRepository;

    @Override
    public ShowtimeResponse addShowTime(ShowtimeRequest showTime) {

        Movie movie = movieRepository.findById(showTime.getMovieId()).orElseThrow(() -> new DataNotFoundException("Movie not found"));
        Room room = roomRepository.findById(showTime.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
        ShowTime newShowTime = new ShowTime();
        newShowTime.setTimeStart(showTime.getTimeStart());
        newShowTime.setTimeEnd(showTime.getTimeEnd());
        newShowTime.setStatus(showTime.getStatus());
        newShowTime.setMovie(movie);
        newShowTime.setRoom(room);
        showTimeRepository.save(newShowTime);

        return new ShowtimeResponse(newShowTime.getShowTimeId(), newShowTime.getTimeStart(), newShowTime.getTimeEnd(), newShowTime.getStatus(), newShowTime.getMovie().getMovieId(), newShowTime.getRoom().getRoomID());
    }

    @Override
    public ShowtimeResponse getShowTimeById(Long showTimeId) {
        ShowTime showTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        return new ShowtimeResponse(showTime.getShowTimeId(), showTime.getTimeStart(), showTime.getTimeEnd(), showTime.getStatus(), showTime.getMovie().getMovieId(), showTime.getRoom().getRoomID());
    }


    @Override
    public ShowtimeResponse deleteShowTime(Long showTimeId) {
        ShowTime showTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        showTimeRepository.delete(showTime);
        return new ShowtimeResponse(showTime.getShowTimeId(), showTime.getTimeStart(), showTime.getTimeEnd(), showTime.getStatus(), showTime.getMovie().getMovieId(), showTime.getRoom().getRoomID());
    }


    @Override
    public ShowtimeResponse updateShowTime(Long showTimeId, ShowtimeRequest showTime) {

        ShowTime updateShowTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        updateShowTime.setTimeStart(showTime.getTimeStart());
        updateShowTime.setTimeEnd(showTime.getTimeEnd());
        updateShowTime.setStatus(showTime.getStatus());
        updateShowTime.setMovie(movieRepository.findById(showTime.getMovieId()).orElseThrow(() -> new DataNotFoundException("Movie not found")));
        updateShowTime.setRoom(roomRepository.findById(showTime.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found")));
        showTimeRepository.save(updateShowTime);

        return new ShowtimeResponse(updateShowTime.getShowTimeId(), updateShowTime.getTimeStart(), updateShowTime.getTimeEnd(), updateShowTime.getStatus(), updateShowTime.getMovie().getMovieId(), updateShowTime.getRoom().getRoomID());
    }

    @Override
    public List<ShowtimeResponse> getAllShowTime() {
        List<ShowTime> showTimeList = showTimeRepository.findAll();
        List<ShowtimeResponse> showtimeResponseList = new ArrayList<>();
        for (ShowTime showTime: showTimeList) {
            ShowtimeResponse showtimeResponse = new ShowtimeResponse();
            showtimeResponse.setShowTimeId(showTime.getShowTimeId());
            showtimeResponse.setTimeStart(showTime.getTimeStart());
            showtimeResponse.setTimeEnd(showTime.getTimeEnd());
            showtimeResponse.setStatus(showTime.getStatus());
            showtimeResponse.setMovieId(showTime.getMovie().getMovieId());
            showtimeResponse.setRoomId(showTime.getRoom().getRoomID());
            showtimeResponseList.add(showtimeResponse);
        }
        return showtimeResponseList;
    }

    @Override
    public List<ShowtimeResponse> getShowTimeByMovieId(Long movie) {
        Movie movie1 = movieRepository.findById(movie).orElseThrow(() -> new DataNotFoundException("Movie not found"));
        List<ShowTime> showTimeList = showTimeRepository.findShowTimeByMovieMovieId(movie1.getMovieId());
        List<ShowtimeResponse> showtimeResponseList = new ArrayList<>();
        for (ShowTime showTime: showTimeList) {
            ShowtimeResponse showtimeResponse = new ShowtimeResponse();
            showtimeResponse.setShowTimeId(showTime.getShowTimeId());
            showtimeResponse.setTimeStart(showTime.getTimeStart());
            showtimeResponse.setTimeEnd(showTime.getTimeEnd());
            showtimeResponse.setStatus(showTime.getStatus());
            showtimeResponse.setMovieId(showTime.getMovie().getMovieId());
            showtimeResponse.setRoomId(showTime.getRoom().getRoomID());
            showtimeResponseList.add(showtimeResponse);
        }
        return showtimeResponseList;
    }
}

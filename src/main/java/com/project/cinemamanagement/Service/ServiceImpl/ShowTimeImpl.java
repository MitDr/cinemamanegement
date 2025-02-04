package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Enum.ROOMSTAT;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Exception.InvalidDataException;
import com.project.cinemamanagement.PayLoad.Request.ShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Response.ShowtimeResponse;
import com.project.cinemamanagement.Repository.MovieRepository;
import com.project.cinemamanagement.Repository.RoomRepository;
import com.project.cinemamanagement.Repository.ShowTimeRepository;
import com.project.cinemamanagement.Service.ShowTimeService;
import com.project.cinemamanagement.Specifications.ShowtimeSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowTimeImpl implements ShowTimeService {

    private final ShowTimeRepository showTimeRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    @Override
    public ShowtimeResponse addShowTime(ShowtimeRequest showTime) {

        System.out.println(showTime.getTimeStart() + " " + showTime.getTimeEnd());
        Movie movie = movieRepository.findById(showTime.getMovieId()).orElseThrow(() -> new DataNotFoundException("Movie not found"));

        if(validateMovieTime(showTime, movie)){
            throw new InvalidDataException("Time is not valid");
        }
        Date temporaryTime = calculateTimeEnd(showTime.getTimeStart(), movie.getDuration());
        if(temporaryTime.after(showTime.getTimeEnd())){
            showTime.setTimeEnd(calculateTimeEnd(temporaryTime, 15));
        }
        ShowTime newShowTime = new ShowTime();
        newShowTime.setPrice(showTime.getPrice());
        newShowTime.setTimeStart(showTime.getTimeStart());
        newShowTime.setTimeEnd(showTime.getTimeEnd());
        newShowTime.setMovie(movie);
        if(showTime.getRoomId()!=null){
            Room room = roomRepository.findById(showTime.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
            if(validateShowTime(room, showTime.getTimeStart(), showTime.getTimeEnd())){
                throw new InvalidDataException("Room has another showtime that has the same time");
            }
            if(room.getStatus() == ROOMSTAT.MAINTENANCE){
                throw new InvalidDataException("Room is under maintenance");
            }
            newShowTime.setRoom(room);
        }

        showTimeRepository.save(newShowTime);

        return new ShowtimeResponse(newShowTime.getShowTimeId(), newShowTime.getTimeStart(), newShowTime.getTimeEnd(), newShowTime.getPrice(),newShowTime.getMovie().getMovieId(), null);
    }


    private boolean validateShowTime(Room room, Date newStartTime, Date newEndTime){
        // Tạo Specification kiểm tra xung đột
        Specification<ShowTime> specification = ShowtimeSpecifications.overlappingShowtime(room, newStartTime, newEndTime);

        // Tìm các suất chiếu trùng giờ
        List<ShowTime> overlappingShowtimes = showTimeRepository.findAll(specification);

        // Trả về false nếu có suất chiếu trùng giờ
        return !overlappingShowtimes.isEmpty();
    }

    private Date calculateTimeEnd(Date timeStart, int durationInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeStart);
        calendar.add(Calendar.MINUTE, durationInMinutes);
        return calendar.getTime();
    }
    private boolean validateMovieTime(ShowtimeRequest showTime, Movie movie) {
        Date showTimeStart = showTime.getTimeStart();
        Date showTimeEnd = showTime.getTimeEnd();
        Date movieStart = movie.getReleaseDate();
        Date movieEnd = movie.getEndDate();

        return (!showTimeStart.after(movieStart)
                || !showTime.getTimeEnd().before(movieEnd)
                || !showTimeStart.before(showTimeEnd))
                &&
                (!showTimeStart.equals(movieStart)
                        || !showTimeEnd.equals(movieEnd)
                        || !showTimeStart.before(showTimeEnd));
    }

    @Override
    public ShowtimeResponse getShowTimeById(Long showTimeId) {
        ShowTime showTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        return new ShowtimeResponse(showTime);
    }


    @Override
    public ShowtimeResponse deleteShowTime(Long showTimeId) {
        ShowTime showTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        showTimeRepository.delete(showTime);
        return new ShowtimeResponse(showTime);
    }


    @Override
    public ShowtimeResponse updateShowTime(Long showTimeId, ShowtimeRequest showTime) {
        ShowTime updateShowTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        Movie movie = movieRepository.findById(showTime.getMovieId()).orElseThrow(() -> new DataNotFoundException("Movie not found"));

        if(validateMovieTime(showTime, movie)){
            throw new InvalidDataException("Time is not valid");
        }
        Date temporaryTime = calculateTimeEnd(showTime.getTimeStart(), movie.getDuration());
//        System.out.println(temporaryTime);
        if(temporaryTime.after(showTime.getTimeEnd())){
            showTime.setTimeEnd(calculateTimeEnd(temporaryTime, 15));
        }
        updateShowTime.setPrice(showTime.getPrice());
        updateShowTime.setTimeStart(showTime.getTimeStart());
        updateShowTime.setTimeEnd(showTime.getTimeEnd());
        updateShowTime.setMovie(movie);

        if(showTime.getRoomId()!=null){
            Room room = roomRepository.findById(showTime.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
            if(validateShowTime(room, showTime.getTimeStart(), showTime.getTimeEnd())){
                throw new InvalidDataException("Room has another showtime that has the same time");
            }
            if(room.getStatus() == ROOMSTAT.MAINTENANCE){
                throw new InvalidDataException("Room is under maintenance");
            }
            updateShowTime.setRoom(room);
        }

        showTimeRepository.save(updateShowTime);

        return new ShowtimeResponse(updateShowTime);
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
            showtimeResponse.setPrice(showTime.getPrice());
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
            showtimeResponse.setPrice(showTime.getPrice());
            showtimeResponse.setMovieId(showTime.getMovie().getMovieId());
            showtimeResponse.setRoomId(showTime.getRoom().getRoomID());
            showtimeResponseList.add(showtimeResponse);
        }
        return showtimeResponseList;
    }

    @Override
    public void addShowTimeList(List<ShowtimeRequest> showTimeList, Movie movie) {
        List<ShowTime> showTimeList1 = new ArrayList<>();
        for (ShowtimeRequest showTime: showTimeList) {
            ShowTime showTime1 = new ShowTime();
            showTime1.setMovie(movie);
            showTime1.setTimeStart(showTime.getTimeStart());
            showTime1.setTimeEnd(showTime.getTimeEnd());
            showTime1.setPrice(showTime.getPrice());
            if(showTime.getRoomId()!=null){
                Room room = roomRepository.findById(showTime.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
                if(validateShowTime(room, showTime.getTimeStart(), showTime.getTimeEnd())){
                    throw new InvalidDataException("Room has another showtime that has the same time");
                }
                showTime1.setRoom(room);
            }
            showTimeList1.add(showTime1);
        }
        showTimeRepository.saveAll(showTimeList1);
    }
}

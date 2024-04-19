package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.PayLoad.Request.SeatRequest;
import com.project.cinemamanagement.PayLoad.Response.SeatResponse;
import com.project.cinemamanagement.Repository.RoomRepository;
import com.project.cinemamanagement.Repository.SeatRepository;
import com.project.cinemamanagement.Service.SeatService;
import com.project.cinemamanagement.Specifications.SeatSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatImpl implements SeatService {
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    RoomRepository roomRepository;
    @Override
    public List<SeatResponse> getAllSeat() {
        List<Seat> seatList = seatRepository.findAll();
        List<SeatResponse> seatResponseList = new ArrayList<>();
        for (Seat seat: seatList) {
            seatResponseList.add(new SeatResponse(seat.getSeatID(), seat.getSeatStatus(), seat.getSeatNumber(), seat.getSeatType()));
        }
        return seatResponseList;
    }

    @Override
    public List<SeatResponse> getSeatByScreenId(Long screenId) {
        return List.of();
    }

    @Override
    public SeatResponse addSeat(SeatRequest seatRequest) {

        Room temp = roomRepository.findById(seatRequest.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(seatRequest.getSeatType());
        seat.setSeatStatus(seatRequest.getSeatStatus());
        seat.setRoom(temp);
        seatRepository.save(seat);


        return new SeatResponse(seat.getSeatID(), seat.getSeatStatus(), seat.getSeatNumber(), seat.getSeatType());
    }

    @Override
    public SeatResponse getSeatById(Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found"));

        return new SeatResponse(seat.getSeatID(), seat.getSeatStatus(), seat.getSeatNumber(), seat.getSeatType());
    }

    @Override
    public SeatResponse updateSeat(Long seatId, SeatRequest seat) {
        Room temp = roomRepository.findById(seat.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
        Seat tempSeat = seatRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found"));
        tempSeat.setSeatNumber(seat.getSeatNumber());
        tempSeat.setSeatType(seat.getSeatType());
        tempSeat.setSeatStatus(seat.getSeatStatus());
        tempSeat.setRoom(temp);

        seatRepository.save(tempSeat);

        return new SeatResponse(tempSeat.getSeatID(), tempSeat.getSeatStatus(), tempSeat.getSeatNumber(), tempSeat.getSeatType());
    }

    @Override
    public SeatResponse deleteSeat(Long seatId) {
        Seat temp = seatRepository.findById(seatId).orElseThrow(() -> new DataNotFoundException("Seat not found"));
        seatRepository.delete(temp);

        return new SeatResponse(temp.getSeatID(), temp.getSeatStatus(), temp.getSeatNumber(), temp.getSeatType());
    }

    @Override
    public List<SeatResponse> getAllSeatResponse() {
        List<Seat> seatList = seatRepository.findAll();
        List<SeatResponse> seatResponseList = new ArrayList<>();
        for (Seat seat: seatList) {
            SeatResponse seatResponse = new SeatResponse();
            seatResponse.setSeatNumber(seat.getSeatNumber());
            seatResponse.setSeatType(seat.getSeatType());
            seatResponse.setSeatStatus(seat.getSeatStatus());
            seatResponseList.add(seatResponse);
        }
        return seatResponseList;
    }

    @Override
    public List<SeatResponse> getAllSeatResponseByRoomType(String roomType) {
        Specification<Seat> spec = SeatSpecifications.getSeatByRoomType(roomType);
        List<Seat> seatList = seatRepository.findAll(spec);
        System.out.println(seatList.size());
        List<SeatResponse> seatResponseList = new ArrayList<>();
        for (Seat seat: seatList) {
            SeatResponse seatResponse = new SeatResponse();
            seatResponse.setSeatNumber(seat.getSeatNumber());
            seatResponse.setSeatType(seat.getSeatType());
            seatResponse.setSeatStatus(seat.getSeatStatus());
            seatResponseList.add(seatResponse);
        }
        return seatResponseList;
    }

    @Override
    public List<SeatResponse> getUntakenSeat(Long showtimeId) {
        Specification<Seat> spec = SeatSpecifications.getUntakenSeat(showtimeId);
        List<Seat> seatList = seatRepository.findAll(spec);
        List<SeatResponse> seatResponseList = new ArrayList<>();
        for (Seat seat: seatList) {
            SeatResponse seatResponse = new SeatResponse();
            seatResponse.setSeatNumber(seat.getSeatNumber());
            seatResponse.setSeatType(seat.getSeatType());
            seatResponse.setSeatStatus(seat.getSeatStatus());
            seatResponseList.add(seatResponse);
        }
        return seatResponseList;
    }

    @Override
    public List<SeatResponse> getAllSeatbyShowtimeId(Long roomId) {
        Specification<Seat> spec = SeatSpecifications.getAllSeatbyShowtimeId(roomId);
        List<Seat> seatList = seatRepository.findAll(spec);
        List<SeatResponse> seatResponseList = new ArrayList<>();
        for (Seat seat: seatList) {
            SeatResponse seatResponse = new SeatResponse();
            seatResponse.setSeatNumber(seat.getSeatNumber());
            seatResponse.setSeatType(seat.getSeatType());
            seatResponse.setSeatStatus(seat.getSeatStatus());
            seatResponseList.add(seatResponse);
        }
        return seatResponseList;
    }

}

package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.Entity.ShowTime;
import com.project.cinemamanagement.Entity.Ticket;
import com.project.cinemamanagement.Enum.SEATSTAT;
import com.project.cinemamanagement.Enum.SEATTYPE;
import com.project.cinemamanagement.Exception.DataFoundException;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Exception.InvalidDataException;
import com.project.cinemamanagement.PayLoad.Request.SeatRequest;
import com.project.cinemamanagement.PayLoad.Response.SeatResponse;
import com.project.cinemamanagement.PayLoad.Response.SeatResponseUser;
import com.project.cinemamanagement.Repository.RoomRepository;
import com.project.cinemamanagement.Repository.SeatRepository;
import com.project.cinemamanagement.Repository.ShowTimeRepository;
import com.project.cinemamanagement.Repository.TicketRepository;
import com.project.cinemamanagement.Service.BaseService;
import com.project.cinemamanagement.Service.SeatService;
import com.project.cinemamanagement.Specifications.SeatSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatImpl extends BaseService<Seat, Long> implements SeatService {

    private final SeatRepository seatRepository;

    private final RoomRepository roomRepository;

    private final ShowTimeRepository showTimeRepository;

    private final TicketRepository ticketRepository;

    @Override
    public List<SeatResponse> getAllSeat() {
        List<Seat> seatList = getAll();
        List<SeatResponse> seatResponseList = new ArrayList<>();
        for (Seat seat: seatList) {
            seatResponseList.add(new SeatResponse(seat));
        }
        return seatResponseList;
    }


    @Override
    public void addSeat(SeatRequest seatRequest) {
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(seatRequest.getSeatType());
        seat.setSeatStatus(seatRequest.getSeatStatus());
        if (seatRequest.getRoomId() != null) {
            Room room = roomRepository.findById(seatRequest.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
            if(seatRequest.getSeatStatus() == SEATSTAT.MAINTENANCE){
                throw new InvalidDataException("Cannot add maintenance seat to a room ");
            }
            seat.setRoom(room);
        }
        String[] string = seatRequest.getSeatNumber().split("_");
        for (String s : string) {
            if (seatRepository.existsBySeatNumberContainingAndRoom(s, seat.getRoom())) {
                throw new DataFoundException("Seat already exists");
            }
        }
        if(seatRequest.getSeatNumber().matches("^(1[0-9]|20|[1-9]([A-T]))$") && seatRequest.getSeatType()==SEATTYPE.SINGLE){
            create(seat);
            return;
        }
        else
        if(seatRequest.getSeatType() == SEATTYPE.DOUBLE && seatRequest.getSeatNumber().matches("^(1[0-9]|20|[1-9])([A-T])_(1[0-9]|20|[1-9])\\2$")){
            int seat1 = string[0].length()==2?Integer.parseInt(String.valueOf(string[0].charAt(0))):Integer.parseInt(string[0].substring(0,1));
            int seat2 = string[1].length()==2?Integer.parseInt(String.valueOf(string[1].charAt(0))):Integer.parseInt(string[1].substring(0,1));
            if(seat2 - seat1 !=1){
                throw new InvalidDataException("Seat information is incorrect.");
            }else{
                create(seat);
                return;
            }
        }else{
            throw new InvalidDataException("Seat information is incorrect.");
        }

//        CompletableFuture<Seat> createFuture = createSeat(seatRequest);
//        CompletableFuture<Void> validateFuture = validateSeatInfo(seatRequest);
//        CompletableFuture<Room> roomFuture = seatRequest.getRoomId() != null ?
//                findRoom(seatRequest.getRoomId()) :
//                CompletableFuture.completedFuture(null);
//        CompletableFuture.allOf(
//                createFuture,
//                validateFuture,
//                roomFuture
//        ).join();
//
//        Room room = roomFuture.join();
//        Seat seat = createFuture.join();
//        seat.setRoom(room);
//        CompletableFuture<Seat> saveFuture = saveSeat(seat);
//        saveFuture.join();
    }
//    private boolean isDoubleSeat(SeatRequest seatRequest){
//        String regex = "^(1[0-9]|20|[1-9])([A-T])_(1[0-9]|20|[1-9])\\2$";
//        String[] string = seatRequest.getSeatNumber().split("_");
//        System.out.println(Arrays.toString(string));
//        if(seatRequest.getRoomId() == null){
//            return seatRequest.getSeatNumber().matches(regex);
//        }
//        else {
//            Room room = roomRepository.findById(seatRequest.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
//            boolean check = seatRepository.existsBySeatNumberContainingAndRoom(string[0], room);
//            System.out.println(check);
//            boolean check2 = seatRepository.existsBySeatNumberContainingAndRoom(string[1], room);
//            System.out.println(check2);
//            return check && check2;
//        }
//    }
//    @Async
//    public CompletableFuture<Seat> createSeat(SeatRequest seatRequest) {
//        Seat seat = new Seat();
//        seat.setSeatNumber(seatRequest.getSeatNumber());
//        seat.setSeatType(seatRequest.getSeatType());
//        seat.setSeatStatus(seatRequest.getSeatStatus());
//
//        return CompletableFuture.completedFuture(seat);
//    }
//    @Async
//    public CompletableFuture<Void> validateSeatInfo(SeatRequest seatRequest) {
//
//        if (seatRequest.getSeatNumber() == null || seatRequest.getSeatType() == null) {
//            throw new IllegalArgumentException("Seat information is incomplete.");
//        }
//        if(seatRequest.getSeatType()==SEATTYPE.DOUBLE && !seatRequest.getSeatNumber().matches("^((1[0-9]|20|[1-9]))([A-T])_((?=\\1[+1])\\2)$")){
//            throw new IllegalArgumentException("Seat information is incorrect.");
//        }
//        String[] string = seatRequest.getSeatNumber().split("_");
//        Room room = roomRepository.findById(seatRequest.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
//        for(String s : string){
//            if (seatRepository.existsBySeatNumberContainingAndRoom(s,room)){
//                throw new DataFoundException("Seat already exists");
//            }
//        }
//        return CompletableFuture.completedFuture(null);
//    }

//    @Async
//    public CompletableFuture<Room> findRoom(Long roomId) {
//        // Lấy thông tin phòng từ cơ sở dữ liệu
//        return CompletableFuture.supplyAsync(() ->
//                roomRepository.findById(roomId)
//                        .orElseThrow(() -> new DataNotFoundException("Room not found")));
//    }

//    @Async
//    public CompletableFuture<Seat> saveSeat(Seat seat) {
//        return CompletableFuture.supplyAsync((
//                () -> {
//                    try {
//                        return seatRepository.save(seat);
//                    } catch (DataIntegrityViolationException e) {
//                        throw new DataFoundException("Seat already exists");
//                    }
//            }
//        ));
//    }

    @Override
    public Page<SeatResponse> getAllSeatPaging(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Seat> page = seatRepository.findBySeatStatus(1, pageable);

        return page.map(seat -> new SeatResponse(seat.getSeatID(), seat.getSeatStatus(), seat.getSeatNumber(), seat.getSeatType(), seat.getRoom().getRoomID()));
    }

    @Override
    public SeatResponse getSeatById(Long seatId) {
        Seat seat = getById(seatId);
        return new SeatResponse(seat);
    }


    @Override
    public void updateSeat(Long seatId, SeatRequest seat) {
        Seat existingSeat = getById(seatId);
        Optional<Room> room1 = Optional.empty();
        if(existingSeat.getRoom()!=null){
            room1 = roomRepository.findById(existingSeat.getRoom().getRoomID());
        }

        Room room = null;
        if(seat.getRoomId() != null){
            room = roomRepository.findById(seat.getRoomId()).orElseThrow(() -> new DataNotFoundException("Room not found"));
        }

        if(!seat.getSeatNumber().matches("^(1[0-9]|20|[1-9]([A-T]))$") && seat.getSeatType()!=SEATTYPE.SINGLE){
            throw new InvalidDataException("Seat information is incorrect.");
        }
        String[] string = seat.getSeatNumber().split("_");
        if(seat.getSeatType() == SEATTYPE.DOUBLE && seat.getSeatNumber().matches("^(1[0-9]|20|[1-9])([A-T])_(1[0-9]|20|[1-9])\\2$")){
            int seat1 = string[0].length()==2?Integer.parseInt(String.valueOf(string[0].charAt(0))):Integer.parseInt(string[0].substring(0,1));
            int seat2 = string[1].length()==2?Integer.parseInt(String.valueOf(string[1].charAt(0))):Integer.parseInt(string[1].substring(0,1));
            if(seat2 - seat1 !=1){
                throw new InvalidDataException("Seat information is incorrect.");
            }
        }

        if(seat.getSeatStatus() == SEATSTAT.MAINTENANCE){
            List<Ticket> tickets = ticketRepository.findBySeat(existingSeat);
            existingSeat.setSeatStatus(SEATSTAT.MAINTENANCE);
            existingSeat.setSeatNumber(seat.getSeatNumber());
            existingSeat.setSeatType(seat.getSeatType());
            existingSeat.setRoom(null);

            seatRepository.save(existingSeat);
            Seat newSeat = new Seat();
            newSeat.setSeatStatus(SEATSTAT.WORKING);
            newSeat.setRoom(existingSeat.getRoom());
            newSeat.setSeatNumber(existingSeat.getSeatNumber());
            newSeat.setSeatType(existingSeat.getSeatType());
            newSeat.setRoom(room1.get());
            seatRepository.save(newSeat);

            for (Ticket ticket : tickets) {
                ticket.setSeat(newSeat);
            }
            ticketRepository.saveAll(tickets);
        }
        else{
            if(seat.getRoomId() != null){
                for (String s : string) {
                    if (seatRepository.existsBySeatNumberContainingAndRoom(s, room)) {
                        throw new DataFoundException("Seat already exists in this room");
                    }
                }
            }

            existingSeat.setSeatNumber(seat.getSeatNumber());
            existingSeat.setSeatType(seat.getSeatType());
            existingSeat.setSeatStatus(seat.getSeatStatus());
            existingSeat.setRoom(room);
            seatRepository.save(existingSeat);
        }
    }

    @Override
    public void deleteSeat(Long seatId) {
        delete(seatId);
    }

    @Override
    public List<SeatResponse> getAllSeatResponseByRoomType(String roomType) {
        Specification<Seat> spec = SeatSpecifications.getSeatByRoomType(roomType);
        List<Seat> seatList = seatRepository.findAll(spec);
        List<SeatResponse> seatResponseList = new ArrayList<>();
        return getSeatResponses(seatList, seatResponseList);
    }

    private List<SeatResponse> getSeatResponses(List<Seat> seatList, List<SeatResponse> seatResponseList) {
        for (Seat seat: seatList) {
            SeatResponse seatResponse = new SeatResponse();
            seatResponse.setSeatId(seat.getSeatID());
            seatResponse.setRoomId(seat.getRoom().getRoomID());
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
        if(seatList.isEmpty()){
            throw new DataNotFoundException("There are no seats");
        }
        return getSeatResponses(seatList);
    }

    private List<SeatResponse> getSeatResponses(List<Seat> seatList) {
        List<SeatResponse> seatResponseList = new ArrayList<>();
        for (Seat seat: seatList) {
            SeatResponse seatResponse = new SeatResponse();
            seatResponse.setSeatId(seat.getSeatID());
            seatResponse.setSeatNumber(seat.getSeatNumber());
            seatResponse.setSeatType(seat.getSeatType());
            seatResponse.setSeatStatus(seat.getSeatStatus());
            seatResponse.setRoomId(seat.getRoom().getRoomID());
            seatResponseList.add(seatResponse);

        }
        return seatResponseList;
    }

    @Override
    public List<SeatResponse> getAllSeatbyShowtimeId(Long showtimeId) {
        Specification<Seat> spec = SeatSpecifications.getAllSeatbyShowtimeId(showtimeId);
        List<Seat> seatList = seatRepository.findAll(spec);
        return getSeatResponses(seatList);
    }

    @Override
    public List<SeatResponseUser> getRealAllSeat(Long showtimeId) {

        ShowTime showTime = showTimeRepository.findById(showtimeId).orElseThrow(() -> new DataNotFoundException("ShowTime not found"));
        Specification<Seat> spec = SeatSpecifications.getTakenSeat(showtimeId);
        Specification<Seat> specs = SeatSpecifications.getAllSeatbyShowtimeId(showtimeId);
        List<Seat> AllseatList = seatRepository.findAll(specs);
        List<Seat> AllTakenSeat = seatRepository.findAll(spec);
        if(AllseatList.isEmpty()){
            throw new DataNotFoundException("There are no seats");
        }
        Hashtable<String,Boolean> table = new Hashtable<>();
        for (Seat value : AllseatList) {
            table.put(value.getSeatNumber(), true);
        }
        for (Seat value : AllTakenSeat) {
            if (table.containsKey(value.getSeatNumber())) {
                table.replace(value.getSeatNumber(), false);
            }
        }
        List<SeatResponseUser> seatResponseList = new ArrayList<>();
        for (Seat seat: AllseatList) {
            SeatResponseUser seatResponse = new SeatResponseUser();
            seatResponse.setSeatNumber(seat.getSeatNumber());
            seatResponse.setSeatType(seat.getSeatType());
            seatResponse.setSeatStatus(table.get(seat.getSeatNumber()) ? 0 : 1);
            seatResponse.setSeatId(seat.getSeatID());
            seatResponse.setRoomId(seat.getRoom().getRoomID());
            seatResponseList.add(seatResponse);
        }
        return seatResponseList;
    }


    @Override
    protected JpaRepository<Seat, Long> getRepository() {
        return seatRepository;
    }
}

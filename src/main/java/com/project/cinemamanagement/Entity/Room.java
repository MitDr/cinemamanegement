package com.project.cinemamanagement.Entity;

import com.project.cinemamanagement.Enum.ROOMSTAT;
import com.project.cinemamanagement.PayLoad.Request.RoomRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ROOMSTAT status;
    @Column(name = "seatQuantity")
    private int seatQuantity = 0;
    @Column(name = "roomType")
    private String roomType;

    @ToString.Exclude
    @OneToMany(mappedBy = "room")
    private List<ShowTime> showTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST)
    private List<Seat> Seat;

    public void addSeatQuantity() {
        this.seatQuantity++;
    }
    public void reduceSeatQuantity() {
        this.seatQuantity--;
    }


    public Room(RoomRequest roomRequest) {
        this.status = roomRequest.getStatus();
        this.roomType = roomRequest.getRoomType();
    }

    @PreRemove
    public void preRemove(){
        for (Seat seat : Seat) {
            seat.setRoom(null);
        }
    }

}

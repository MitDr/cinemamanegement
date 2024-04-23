package com.project.cinemamanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;
    @Column(name = "status")
    private int status;
    @Column(name = "seatQuantity")
    private int seatQuantity = 0;
    @Column(name = "roomType")
    private String roomType;

    @OneToMany(mappedBy = "room")
    private List<ShowTime> showTime;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> Seat;

    public void addSeatQuantity() {
        this.seatQuantity++;
    }
    public void reduceSeatQuantity() {
        this.seatQuantity--;
    }


}

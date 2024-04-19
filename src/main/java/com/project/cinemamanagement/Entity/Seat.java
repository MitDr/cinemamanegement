package com.project.cinemamanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "tbl_seat")
@NoArgsConstructor
@AllArgsConstructor

public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatID;
    @Column(name = "seatStatus")
    private int seatStatus;
    @Column(name = "seatNumber")
    private String seatNumber;
    @Column(name = "seatType")
    private String seatType;
    @ManyToOne
    @JoinColumn(name = "roomID")
    private Room room;


}

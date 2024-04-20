package com.project.cinemamanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Remove;

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

    @Transient
    private Room originalRoom; // Lưu trữ giá trị ban đầu của room

    @PostLoad
    public void postLoad() {
        this.originalRoom = this.room; // Khởi tạo originalRoom khi entity được load
    }

    @PostPersist
    public void addSeatQuantity() {
        this.room.addSeatQuantity();
    }

    @PreRemove
    public void reduceSeatQuantity() {
        this.room.reduceSeatQuantity();
    }

    @PreUpdate
    public void preUpdateSeatQuantity() {
        if (this.originalRoom != null) {
            this.originalRoom.reduceSeatQuantity(); // Sử dụng originalRoom
        }
    }

    @PostUpdate
    public void postUpdateSeatQuantity() {
        if (this.room != null) {
            this.room.addSeatQuantity(); // Sử dụng room hiện tại
        }
    }
}

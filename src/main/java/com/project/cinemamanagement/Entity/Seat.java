package com.project.cinemamanagement.Entity;

import com.project.cinemamanagement.Enum.SEATSTAT;
import com.project.cinemamanagement.Enum.SEATTYPE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name = "tbl_seat",uniqueConstraints = @UniqueConstraint(columnNames = {"seatNumber","roomId"}))
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatID;
    @Column(name = "seatNumber")
    private String seatNumber;
    @Column(name = "seatType")
    @Enumerated(EnumType.STRING)
    private SEATTYPE seatType;
    @Column(name = "seatStatus")
    @Enumerated(EnumType.STRING)
    private SEATSTAT seatStatus;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "roomId", nullable = true)
    private Room room;

    @ToString.Exclude
    @OneToMany(mappedBy = "seat",fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = false)
    private List<Ticket> ticketList;

    @Transient
    private Room originalRoom; // Lưu trữ giá trị ban đầu của room

    @PostLoad
    public void postLoad() {
        this.originalRoom = this.room; // Khởi tạo originalRoom khi entity được load
    }

    @PostPersist
    public void addSeatQuantity() {
        if(this.room == null) return;
        this.room.addSeatQuantity();
    }


    @PreRemove
    public void reduceSeatQuantity() {
        this.room.reduceSeatQuantity();
    }

//    @PreUpdate
//    public void preUpdateSeatQuantity() {
//        if (this.originalRoom != null) {
//            this.originalRoom.reduceSeatQuantity(); // Sử dụng originalRoom
//        }
//    }

    @PostUpdate
    public void postUpdateSeatQuantity() {
        if (this.room != null) {
            this.room.addSeatQuantity(); // Sử dụng room hiện tại
        }
    }

}

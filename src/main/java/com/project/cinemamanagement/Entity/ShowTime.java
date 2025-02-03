package com.project.cinemamanagement.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.cinemamanagement.PayLoad.Request.ShowtimeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_showtime")
@AllArgsConstructor
@NoArgsConstructor
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_time_Id")
    private Long showTimeId;
    @Column(name ="show_time_time_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;
    @Column(name ="show_time_time_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;

    @Column(name = "show_time_price")
    private Long price;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "movie_Id")
    private Movie movie;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "roomID")
    private Room room;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL)
    private List<Ticket> ticket;


    public ShowTime(ShowtimeRequest showtimeRequest){

    }
}

package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.Seat;
import com.project.cinemamanagement.PayLoad.Response.SeatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {

    Page<Seat> findBySeatStatus(int seatStatus, Pageable pageable);
}

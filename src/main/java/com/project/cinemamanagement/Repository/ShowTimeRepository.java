package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findShowTimeByMovieMovieId(Long movieId);
}

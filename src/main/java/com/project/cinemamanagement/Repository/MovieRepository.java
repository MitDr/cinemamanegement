package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>, JpaSpecificationExecutor<Movie> {
    List<Movie>  findAllByOrderByMovieId();
    Optional<Movie> findByMovieName(String movieName);
}

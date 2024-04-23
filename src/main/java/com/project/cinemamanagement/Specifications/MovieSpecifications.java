package com.project.cinemamanagement.Specifications;

import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Entity.ShowTime;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class MovieSpecifications {
    public static Specification<Movie> GetMovieByDate (Date date) {
        return ((root, query, criteriaBuilder) -> {
            Join<Movie, ShowTime> showtimeJoin = root.join("showTimes");
            return criteriaBuilder.equal(criteriaBuilder.function("DATE", Date.class, showtimeJoin.get("timeStart")), date);
        });
    }
}

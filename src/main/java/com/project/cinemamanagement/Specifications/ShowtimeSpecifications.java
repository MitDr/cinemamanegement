package com.project.cinemamanagement.Specifications;

import com.project.cinemamanagement.Entity.Room;
import com.project.cinemamanagement.Entity.ShowTime;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ShowtimeSpecifications {
    public static Specification<ShowTime> overlappingShowtime(Room room, Date newStartTime, Date newEndTime) {
        return (Root<ShowTime> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            // Điều kiện phòng chiếu
            Predicate roomPredicate = cb.equal(root.get("room"), room);

            // Điều kiện thời gian không chồng lấn
            Predicate noOverlapPredicate = cb.or(
                    cb.lessThanOrEqualTo(root.get("timeEnd"), newStartTime),
                    cb.greaterThanOrEqualTo(root.get("timeStart"), newEndTime)
            );

            // Phủ định điều kiện để lấy các suất chiếu chồng lấn
            Predicate overlapPredicate = cb.not(noOverlapPredicate);

            // Kết hợp điều kiện phòng chiếu và chồng lấn
            return cb.and(roomPredicate, overlapPredicate);
        };
    }

    public static Specification<ShowTime> findByMovieId(Long movieId){
        return (Root<ShowTime> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get("movie").get("movieId"), movieId);
        };
    }
}

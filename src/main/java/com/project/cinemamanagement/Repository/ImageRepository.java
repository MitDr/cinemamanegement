package com.project.cinemamanagement.Repository;

import com.project.cinemamanagement.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

    Optional<Image> findByUrl(String imageUrl);
}

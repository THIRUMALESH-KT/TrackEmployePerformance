package com.talent.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talent.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByStartDate(LocalDate date);

}

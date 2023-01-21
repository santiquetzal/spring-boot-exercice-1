package com.virtualcave.excercise.repository;

import com.virtualcave.excercise.model.Rate;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RateRepository extends CrudRepository<Rate, Integer> {

    List<Rate> findAll();

    List<Rate> findByProductIdAndBrandIdAndStartDateAndEndDate(int productId, int brandId, LocalDate startDate, LocalDate endDate);
}

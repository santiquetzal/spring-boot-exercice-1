package com.virtualcave.excercise.repository;

import com.virtualcave.excercise.repository.model.Rate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RateRepository extends CrudRepository<Rate, Integer> {

    @NotNull
    List<Rate> findAll();

    @NotNull
    List<Rate> findByStartDateAfterAndEndDateBeforeAndProductIdAndBrandId(LocalDate date, LocalDate sameDate, int productId, int brandId);

}

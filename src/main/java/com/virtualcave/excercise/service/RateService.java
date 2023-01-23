package com.virtualcave.excercise.service;

import com.virtualcave.excercise.service.dto.RateDto;
import com.virtualcave.excercise.service.dto.UpdateDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RateService {

    Optional<RateDto> findById(@NotNull Integer id);

    List<RateDto> findAll();

    RateDto findByProductIdAndBrandIdAndStartDateAndEndDate(int productId, int brandId, LocalDate startDate, LocalDate endDate);

    RateDto save(RateDto rateDto);

    RateDto update(UpdateDto updateDto);

    void delete(int id);
}

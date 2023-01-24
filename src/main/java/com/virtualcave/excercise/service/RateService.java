package com.virtualcave.excercise.service;

import com.virtualcave.excercise.service.dto.RateDto;
import com.virtualcave.excercise.service.dto.UpdateDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface RateService {

    RateDto findById(@NotNull Integer id);

    List<RateDto> findAll();

    List<RateDto> findByProductIdAndBrandIdAndStartDateAndEndDate(LocalDate date, int productId, int brandId);

    RateDto save(RateDto rateDto);

    RateDto update(UpdateDto updateDto);

    void delete(int id);
}

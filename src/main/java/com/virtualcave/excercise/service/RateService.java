package com.virtualcave.excercise.service;

import com.virtualcave.excercise.model.Rate;
import com.virtualcave.excercise.model.dto.RateDto;
import com.virtualcave.excercise.model.dto.RateWithDecimalPriceDTO;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface RateService {

    Rate findById(@NotNull Integer id);

    List<RateDto> findAll();

    RateWithDecimalPriceDTO findByProductIdAndBrandIdAndStartDateAndEndDate(int productId, int brandId, LocalDate startDate, LocalDate endDate);

    RateDto save(Rate rate);

    Rate update(Rate rate);

    void delete(int id);
}

package com.virtualcave.excercise.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PriceDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer price;
    private String currencyCode;

}

package com.virtualcave.excercise.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RateApiResponseDto {

    private Integer id;
    private Integer brandId;
    private Integer productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer price;
    private String currencyCode;

}

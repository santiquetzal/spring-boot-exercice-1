package com.virtualcave.excercise.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateApiRequestDto {

    private Integer id;
    private Integer brandId;
    private Integer productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer price;
    private String currencyCode;

}

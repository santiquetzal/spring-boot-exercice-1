package com.virtualcave.excercise.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RateWithPricesDto {

    private Integer id;
    private Integer brandId;
    private Integer productId;

    private List<PriceDto> prices = new ArrayList<>();

    public void addRate(LocalDate startDate, LocalDate endDate, Integer price, String currencyCode) {
        PriceDto priceDto = new PriceDto(startDate, endDate, price, currencyCode);
        prices.add(priceDto);
    }
}

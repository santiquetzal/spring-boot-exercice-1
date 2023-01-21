package com.virtualcave.excercise.model.dto;

import com.virtualcave.excercise.model.Rate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RateWithDecimalPriceDTO {

    private Integer id;
    private Integer brandId;
    private Integer productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private String currencyCode;

    public RateWithDecimalPriceDTO(Rate rate, int decimalPositions) {
        this.id = rate.getId();
        this.brandId = rate.getBrandId();
        this.productId = rate.getProductId();
        this.startDate = rate.getStartDate();
        this.endDate = rate.getEndDate();
        this.price = BigDecimal.valueOf(rate.getPrice()).setScale(decimalPositions, RoundingMode.HALF_UP);
        this.currencyCode = rate.getCurrencyCode();
    }

}

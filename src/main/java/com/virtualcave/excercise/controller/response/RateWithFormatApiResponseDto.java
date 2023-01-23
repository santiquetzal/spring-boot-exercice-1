package com.virtualcave.excercise.controller.response;

import com.virtualcave.excercise.service.dto.RateDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RateWithFormatApiResponseDto {

    private Integer id;
    private Integer brandId;
    private Integer productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String price;
    private String currencySymbol;

    public RateWithFormatApiResponseDto(RateDto rateDto, int decimalPositions, String currencySymbol) {
        this.id = rateDto.getId();
        this.brandId = rateDto.getBrandId();
        this.productId = rateDto.getProductId();
        this.startDate = rateDto.getStartDate();
        this.endDate = rateDto.getEndDate();
        this.price = BigDecimal.valueOf(rateDto.getPrice()).setScale(decimalPositions, RoundingMode.HALF_UP).toPlainString();
        this.currencySymbol = currencySymbol;
    }

}

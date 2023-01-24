package com.virtualcave.excercise.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyDto {

    private String symbol;
    private String code;
    private int decimals;

}

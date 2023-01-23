package com.virtualcave.excercise.remote.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyDto {

    private String symbol;
    private String code;
    private int decimals;

}

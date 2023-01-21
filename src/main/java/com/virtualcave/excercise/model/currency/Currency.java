package com.virtualcave.excercise.model.currency;

import lombok.Data;

@Data
public class Currency {
    private String symbol;
    private String code;
    private int decimals;
}

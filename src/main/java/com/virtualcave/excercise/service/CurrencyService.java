package com.virtualcave.excercise.service;

import com.virtualcave.excercise.service.dto.CurrencyDto;

public interface CurrencyService {
    CurrencyDto getCurrency(String currencyCode);
}

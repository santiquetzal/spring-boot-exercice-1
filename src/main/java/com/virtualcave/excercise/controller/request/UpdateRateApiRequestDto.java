package com.virtualcave.excercise.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRateApiRequestDto {

    private Integer id;
    private Integer price;

}

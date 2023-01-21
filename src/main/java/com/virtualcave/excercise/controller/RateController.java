package com.virtualcave.excercise.controller;

import com.virtualcave.excercise.model.Rate;
import com.virtualcave.excercise.model.dto.RateDto;
import com.virtualcave.excercise.model.dto.RateWithDecimalPriceDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rate")
public interface RateController {

    @Operation(description = "Creates a new rate")
    @PostMapping
    ResponseEntity<RateDto> create(@RequestBody Rate rate);

    @Operation(description = "Retrieves a rate by id")
    @GetMapping("/{id}")
    ResponseEntity<Rate> getById(@PathVariable int id);

    @Operation(description = "Retrieves all existing rates")
    @GetMapping("/all")
    ResponseEntity<List<RateDto>> getAll();

    @Operation(description = "Retrieves a rate by date, product and brand")
    @GetMapping("")
    ResponseEntity<RateWithDecimalPriceDTO> getByProductIdAndBrandIdAndStartDateAndEndDate(
            @RequestParam String startDate, @RequestParam String endDate,
            @RequestParam int productId, @RequestParam int brandId);

    @Operation(description = "Updates a rate")
    @PutMapping
    Rate update(@RequestBody Rate rate);

    @Operation(description = "Deletes a rate")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id);

}

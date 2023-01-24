package com.virtualcave.excercise.controller;

import com.virtualcave.excercise.controller.request.RateApiRequestDto;
import com.virtualcave.excercise.controller.request.UpdateRateApiRequestDto;
import com.virtualcave.excercise.controller.response.RateApiResponseDto;
import com.virtualcave.excercise.controller.response.RateWithFormatApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rates")
public interface RateController {

    @Operation(description = "Creates a new rate")
    @PostMapping
    ResponseEntity<RateApiResponseDto> create(@RequestBody RateApiRequestDto createRateApiRequest);

    @Operation(description = "Retrieves a rate by id")
    @GetMapping("/{id}")
    ResponseEntity<RateWithFormatApiResponseDto> getById(@PathVariable int id);

    @Operation(description = "Retrieves all existing rates")
    @GetMapping
    ResponseEntity<List<RateApiResponseDto>> getAll();

    @Operation(description = "Retrieves a rate between date, product and brand")
    @GetMapping("/search")
    ResponseEntity<List<RateWithFormatApiResponseDto>> getByProductIdAndBrandIdAndStartDateAndEndDate(
            @RequestParam String date, @RequestParam int productId, @RequestParam int brandId);

    @Operation(description = "Updates a rate")
    @PatchMapping("/{id}")
    RateApiResponseDto update(@RequestParam int id, @RequestBody UpdateRateApiRequestDto updateRateApiRequestDto);

    @Operation(description = "Deletes a rate")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id);

}

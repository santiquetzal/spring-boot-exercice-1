package com.virtualcave.excercise.controller;

import com.virtualcave.excercise.controller.request.RateApiRequestDto;
import com.virtualcave.excercise.controller.request.UpdateRateApiRequestDto;
import com.virtualcave.excercise.controller.response.RateApiResponseDto;
import com.virtualcave.excercise.controller.response.RateWithFormatApiResponseDto;
import com.virtualcave.excercise.repository.RateRepository;
import com.virtualcave.excercise.service.CurrencyService;
import com.virtualcave.excercise.service.RateService;
import com.virtualcave.excercise.service.dto.CurrencyDto;
import com.virtualcave.excercise.service.dto.RateDto;
import com.virtualcave.excercise.service.dto.UpdateDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RateControllerImpl implements RateController {

    private final RateService rateService;
    private final CurrencyService currencyService;
    private final RateRepository rateRepository;

    private ModelMapper mapper;

    @Override
    public ResponseEntity<RateApiResponseDto> create(@RequestBody final RateApiRequestDto createRateApiRequest) {

        RateDto rateDto = rateService.save(mapper.map(createRateApiRequest, RateDto.class));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(rateDto, RateApiResponseDto.class));
    }

    @Override
    public ResponseEntity<List<RateApiResponseDto>> getAll() {
        // Ejemplo usando ResponseEntity
        List<RateApiResponseDto> all = rateService.findAll()
                .stream().map(rate -> mapper.map(rate, RateApiResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(all);
    }

    @Override
    public ResponseEntity<RateWithFormatApiResponseDto> getById(@PathVariable("id") int id) {

            RateDto rateDto = rateService.findById(id);
            CurrencyDto currencyDto = currencyService.getCurrency(rateDto.getCurrencyCode());

            RateWithFormatApiResponseDto rateWithFormatApiResponseDto =
                    new RateWithFormatApiResponseDto(rateDto, currencyDto.getDecimals(), currencyDto.getSymbol());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(rateWithFormatApiResponseDto);
    }

    @Override
    public ResponseEntity<List<RateWithFormatApiResponseDto>> getByProductIdAndBrandIdAndStartDateAndEndDate(
            @RequestParam String date, @RequestParam int productId, @RequestParam int brandId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        List<RateDto> rateDtoList = rateService.findByProductIdAndBrandIdAndStartDateAndEndDate(localDate, productId, brandId);

        List<RateWithFormatApiResponseDto> result = new ArrayList<>();
        for (RateDto rateDto : rateDtoList) {
            CurrencyDto currencyDto = currencyService.getCurrency(rateDto.getCurrencyCode());
            RateWithFormatApiResponseDto rateWithFormatApiResponseDto =
                    new RateWithFormatApiResponseDto(rateDto, currencyDto.getDecimals(), currencyDto.getSymbol());
            result.add(rateWithFormatApiResponseDto);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public RateApiResponseDto update(@PathVariable("id") int id,
            @RequestBody UpdateRateApiRequestDto updateRateApiRequestDto) {
        UpdateDto updateDto = mapper.map(updateRateApiRequestDto, UpdateDto.class);
        updateDto.setId(id);
        RateDto update = rateService.update(updateDto);

        return mapper.map(update, RateApiResponseDto.class);
    }

    @Override
    public void delete(@PathVariable("id") int id) {
        rateService.delete(id);
    }
}

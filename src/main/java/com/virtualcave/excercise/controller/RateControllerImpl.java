package com.virtualcave.excercise.controller;

import com.virtualcave.excercise.controller.request.RateApiRequestDto;
import com.virtualcave.excercise.controller.request.UpdateRateApiRequestDto;
import com.virtualcave.excercise.controller.response.RateApiResponseDto;
import com.virtualcave.excercise.controller.response.RateWithFormatApiResponseDto;
import com.virtualcave.excercise.exception.RateNotFoundException;
import com.virtualcave.excercise.remote.CurrencyService;
import com.virtualcave.excercise.remote.dto.CurrencyDto;
import com.virtualcave.excercise.repository.RateRepository;
import com.virtualcave.excercise.service.RateService;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@AllArgsConstructor
public class RateControllerImpl implements RateController {

    private final RateService rateService;
    private final CurrencyService currencyService; // TODO: ¿Para hacer las peticiones remotas lo instacio así?
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
    public ResponseEntity<List<RateDto>> getAll() {
        // Ejemplo usando ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rateService.findAll());
    }

    @Override
    public ResponseEntity<RateWithFormatApiResponseDto> getById(@PathVariable("id") int id) {
        // Ejemplo usando ResponseEntity y un controlador de errores para permitir establecer
        // los codigos de errores y respuestas en funcions de los errores generados
        try {

            // TODO: Revisar gestión de optional, comprobar si existe... y gestionar exceptions
            // TODO: Servidor local
            RateDto rateDto = rateService.findById(id).orElseThrow(() -> new RateNotFoundException("Rate not found"));

            // TODO: 2: Recupero currency del servidor remoto
            CurrencyDto currencyDto = currencyService.getCurrency(rateDto.getCurrencyCode());
            RateWithFormatApiResponseDto rateWithFormatApiResponseDto =
                    new RateWithFormatApiResponseDto(rateDto, currencyDto.getDecimals(), currencyDto.getSymbol());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(rateWithFormatApiResponseDto);

        } catch (RateNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate Not Found");
        }
    }

    // TODO
    @Override
    public ResponseEntity<RateWithFormatApiResponseDto> getByProductIdAndBrandIdAndStartDateAndEndDate(
            @RequestParam String startDate, @RequestParam String endDate,
            @RequestParam int productId, @RequestParam int brandId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localStartDate = LocalDate.parse(startDate, formatter);
        LocalDate localEndDate = LocalDate.parse(endDate, formatter);

        // TODO: Servidor local
        RateDto rateDto = rateService.findByProductIdAndBrandIdAndStartDateAndEndDate(productId, brandId, localStartDate, localEndDate);

        // TODO: Copiado del otro método, intentar generalizarlo, es exactamente igual?
        // TODO: 2: Recupero currency del servidor remoto
        CurrencyDto currencyDto = currencyService.getCurrency(rateDto.getCurrencyCode());
        RateWithFormatApiResponseDto rateWithFormatApiResponseDto =
                new RateWithFormatApiResponseDto(rateDto, currencyDto.getDecimals(), currencyDto.getSymbol());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rateWithFormatApiResponseDto);
    }

    @Override
    public RateApiResponseDto update(@RequestBody UpdateRateApiRequestDto updateRateApiRequestDto) {
        // TODO: Revisar
        // Ejemplo permitiendo que Spring detecte automáticamente el codigo del estado que tiene que devolver
        // y no usando ResponseEntity (Es decir, sin el try / catch de control de excepcion)
        // TODO: Control de null
//        Rate byId = rateService.findById(updateRateApiRequestDto.getId());

        //rateService.findById(updateRateApiRequestDto)
//        mapper.map(updateRateApiRequestDto, RateDto.class);
        RateDto update = rateService.update(mapper.map(updateRateApiRequestDto, UpdateDto.class));
        return mapper.map(update, RateApiResponseDto.class);
    }

    @Override
    public void delete(int id) {
        // Ejemplo sencillo sin ningún control
        rateService.delete(id);
    }
}

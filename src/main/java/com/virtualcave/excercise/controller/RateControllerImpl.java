package com.virtualcave.excercise.controller;

import com.virtualcave.excercise.exception.RateNotFoundException;
import com.virtualcave.excercise.model.Rate;
import com.virtualcave.excercise.model.dto.RateDto;
import com.virtualcave.excercise.model.dto.RateWithDecimalPriceDTO;
import com.virtualcave.excercise.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class RateControllerImpl implements RateController {

    // TODO: Al ser una aplicación base la he creado inicialmente con las mismas entidades a través de las capas,
    // lo ideal es hacer DTOs y que únicamente se trabaje con las entities en si en el repositorio.

    @Autowired
    RateService rateService;

    @Override
    public ResponseEntity<RateDto> create(@RequestBody final Rate rate) {
        // Ejemplo sencillo usando ResponseEntity
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rateService.save(rate));
    }

    @Override
    public ResponseEntity<Rate> getById(@PathVariable("id") int id) {
        // Ejemplo usando ResponseEntity y un controlador de errores para permitir establecer
        // los codigos de errores y respuestas en funcions de los errores generados
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(rateService.findById(id));

            // TODO: Aquí hay que hacer una llamada al servidor remoto de forma equivalente a como esta en
            // getByProductIdAndBrandIdAndStartDateAndEndDate
        } catch (RateNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate Not Found");
        }
    }

    @Override
    public ResponseEntity<List<RateDto>> getAll() {
        // Ejemplo usando ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rateService.findAll());
    }

    // TODO
    @Override
    public ResponseEntity<RateWithDecimalPriceDTO> getByProductIdAndBrandIdAndStartDateAndEndDate(
            @RequestParam String startDate, @RequestParam String endDate,
            @RequestParam int productId, @RequestParam int brandId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localStartDate = LocalDate.parse(startDate, formatter);
        LocalDate localEndDate = LocalDate.parse(endDate, formatter);
        RateWithDecimalPriceDTO rate = rateService.findByProductIdAndBrandIdAndStartDateAndEndDate(productId, brandId, localStartDate, localEndDate);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rate);
    }

    @Override
    public Rate update(Rate rate) {
        // Ejemplo permitiendo que Spring detecte automáticamente el codigo del estado que tiene que devolver
        // y no usando ResponseEntity (Es decir, sin el try / catch de control de excepcion)
        return rateService.update(rate);
    }

    @Override
    public void delete(int id) {
        // Ejemplo sencillo sin ningún control
        rateService.delete(id);
    }
}

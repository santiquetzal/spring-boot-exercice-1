package com.virtualcave.excercise.service;

import com.virtualcave.excercise.exception.RateNotFoundException;
import com.virtualcave.excercise.repository.RateRepository;
import com.virtualcave.excercise.repository.model.Rate;
import com.virtualcave.excercise.service.dto.RateDto;
import com.virtualcave.excercise.service.dto.UpdateDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final CurrencyService currencyService;
    private ModelMapper mapper;

    @Override
    @Transactional
    public List<RateDto> findAll() {
        return rateRepository.findAll()
                .stream()
                .map(rate -> mapper.map(rate, RateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RateDto findById(@NotNull Integer id) {
        Rate rate =  rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException("The requested rate has not been found."));
        return mapper.map(rate, RateDto.class);
    }

    @Override
    @Transactional
    public List<RateDto> findByProductIdAndBrandIdAndStartDateAndEndDate(LocalDate localDate, int productId, int brandId) {

        List<Rate> rates = rateRepository.findByStartDateAfterAndEndDateBeforeAndProductIdAndBrandId(localDate, localDate, productId, brandId);

        if (rates.isEmpty()) {
            throw new RateNotFoundException("No se han encontrado entidades con los valores proporcionados");
        }

        return rates.stream().map(rate -> mapper.map(rate, RateDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RateDto update(UpdateDto updateDto) {
        Rate rateInDb = rateRepository.findById(updateDto.getId())
                .orElseThrow(() -> new RateNotFoundException("The rate cannot be updated because it does not exist."));

        rateInDb.setPrice(updateDto.getPrice());
        Rate updatedRate = rateRepository.save(rateInDb);
        return mapper.map(updatedRate, RateDto.class);
    }

    @Override
    @Transactional
    public void delete(int id) {
        if (!rateRepository.findById(id).isPresent()) {
            throw new RateNotFoundException("The entity cannot be deleted because it has not been found.");
        }

        rateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RateDto save(RateDto rateDto) {
        Rate rate = rateRepository.save(mapper.map(rateDto, Rate.class));
        return mapper.map(rate, RateDto.class);
    }
}

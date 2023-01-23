package com.virtualcave.excercise.service;

import com.virtualcave.excercise.exception.RateNotFoundException;
import com.virtualcave.excercise.remote.CurrencyService;
import com.virtualcave.excercise.repository.RateRepository;
import com.virtualcave.excercise.repository.model.Rate;
import com.virtualcave.excercise.service.dto.RateDto;
import com.virtualcave.excercise.service.dto.UpdateDto;
import com.virtualcave.excercise.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
                .map(Utils::convertRateToRateDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<RateDto> findById(@NotNull Integer id) {
        Optional<Rate> rate =  rateRepository.findById(id);
        return rate.flatMap(a -> Optional.ofNullable(mapper.map(a, RateDto.class)));
    }

    @Override
    @Transactional
    public RateDto findByProductIdAndBrandIdAndStartDateAndEndDate(int productId, int brandId, LocalDate startDate, LocalDate endDate) {

        List<Rate> rates = rateRepository.findByProductIdAndBrandIdAndStartDateAndEndDate(productId, brandId, startDate, endDate);
        if (rates.size() != 1) { // TODO: Revisar
            log.warn("Hay más de 1 rate controlaría el error. Pero ahora no tengo tiempo.");
            // throw new ApplicationException("More han 1 rates found for product/brand/startDate/endDate");
        }

        // TODO: Revisar

        return mapper.map(rates.get(0), RateDto.class);
//        return rateWithDecimalPriceDTO;
    }

    @Override
    @Transactional
    public RateDto update(UpdateDto updateDto) {
        Rate rateInDb = rateRepository.findById(updateDto.getId())
                .orElseThrow(() -> new RateNotFoundException("Update not found while trying to perform rate update."));

        rateInDb.setPrice(updateDto.getPrice());
        Rate updatedRate = rateRepository.save(rateInDb);
        return mapper.map(updatedRate, RateDto.class);
    }

    @Override
    @Transactional
    public void delete(int id) {
        rateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RateDto save(RateDto rateDto) {
        Rate rate = rateRepository.save(mapper.map(rateDto, Rate.class));
        return mapper.map(rate, RateDto.class);
    }
}

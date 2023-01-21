package com.virtualcave.excercise.service;

import com.virtualcave.excercise.exception.RateNotFoundException;
import com.virtualcave.excercise.model.Rate;
import com.virtualcave.excercise.model.currency.Currency;
import com.virtualcave.excercise.model.dto.RateDto;
import com.virtualcave.excercise.model.dto.RateWithDecimalPriceDTO;
import com.virtualcave.excercise.remote.CurrencyServer;
import com.virtualcave.excercise.repository.RateRepository;
import com.virtualcave.excercise.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    @Autowired
    CurrencyServer currencyServer;

    public RateServiceImpl(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    @Transactional
    public List<RateDto> findAll() {
        return rateRepository.findAll().stream().map(Utils::convertRateToRateDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Rate findById(@NotNull Integer id) {
        return rateRepository
                .findById(id)
                .orElseThrow(() -> new RateNotFoundException("Rate Not Found"));

        // TODO: Falta recuperar currency
    }

    @Override
    @Transactional
    public RateWithDecimalPriceDTO findByProductIdAndBrandIdAndStartDateAndEndDate(int productId, int brandId, LocalDate startDate, LocalDate endDate) {

        List<Rate> rates = rateRepository.findByProductIdAndBrandIdAndStartDateAndEndDate(productId, brandId, startDate, endDate);
        if (rates.size() != 1) {
            log.warn("Hay más de 1 rate controlaría el error. Pero ahora no tengo tiempo.");
            // throw new ApplicationException("More han 1 rates found for product/brand/startDate/endDate");
        }

        // Obtenemos posiciones decimales y simbolo del servidor remoto
        Currency currency = currencyServer.getCurrency(rates.get(0).getCurrencyCode());
        RateWithDecimalPriceDTO rateWithDecimalPriceDTO = new RateWithDecimalPriceDTO(rates.get(0), currency.getDecimals());
        rateWithDecimalPriceDTO.setCurrencyCode(currency.getSymbol());

        return rateWithDecimalPriceDTO;
    }

    @Override
    @Transactional
    public Rate update(Rate rate) {
        Rate rateInDb = findById(rate.getId());
        rateInDb.setPrice(rate.getPrice());
        return rateRepository.save(rateInDb);
    }

    @Override
    @Transactional
    public void delete(int id) {
        rateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RateDto save(Rate rate) {
        return Utils.convertRateToRateDto(rateRepository.save(rate));
    }
}

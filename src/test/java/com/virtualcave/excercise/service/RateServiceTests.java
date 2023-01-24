package com.virtualcave.excercise.service;

import com.virtualcave.excercise.repository.RateRepository;
import com.virtualcave.excercise.repository.model.Rate;
import com.virtualcave.excercise.service.dto.RateDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = { com.virtualcave.excercise.configuration.ModelMapperConfig.class })
class RateServiceTests {

    @Autowired
    ModelMapper mapper;

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateServiceImpl rateService;

    static Rate rate;


    @BeforeAll
    public static void setUp() {
        rate = Rate.builder()
                .brandId(1)
                .productId(2)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .price(1000)
                .currencyCode("EUR")
                .build();

    }

    @Test
    void saveTest() {
        LocalDate now = LocalDate.now();
        LocalDate nowPlus5 = LocalDate.now().plusDays(5);

        RateDto rateToCreate = RateDto.builder()
                .id(9999999)
                .brandId(11)
                .productId(22)
                .startDate(now)
                .endDate(nowPlus5)
                .price(1000)
                .currencyCode("EUR")
                .build();

        when(rateRepository.save(any())).thenReturn(rateToCreate);

        RateDto rateDto = rateService.save(rateToCreate);

        assertNotEquals(9999999, rateDto.getId());
        assertEquals(11, rateDto.getBrandId());
        assertEquals(22, rateDto.getProductId());
        assertEquals(now, rateDto.getStartDate());
        assertEquals(nowPlus5, rateDto.getEndDate());
        assertEquals(1000, rateDto.getPrice());
        assertEquals("EUR", rateDto.getCurrencyCode());
    }

}

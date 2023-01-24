package com.virtualcave.excercise.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        // Sí necesito hacer alguna conversion personalizada añadirla aquí

        return new ModelMapper();
    }
}

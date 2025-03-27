package com.microservice.post_service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIContainer {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

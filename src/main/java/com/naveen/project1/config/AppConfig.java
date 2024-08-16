package com.naveen.project1.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{
    @Bean
     public ModelMapper modelMapper()
     {
         return new ModelMapper();
     }
}

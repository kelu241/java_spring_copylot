package com.example.springcopylot.config;

import com.example.springcopylot.service.IProjetoService;
import com.example.springcopylot.service.ProjetoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public IProjetoService projetoService() {
        return new ProjetoService();
    }
}

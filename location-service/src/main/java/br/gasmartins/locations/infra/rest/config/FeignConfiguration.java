package br.gasmartins.locations.infra.rest.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLever() {
        return Logger.Level.FULL;
    }

    @Bean
    public Logger feignLogger() {
        return new FeignLogger();
    }

}

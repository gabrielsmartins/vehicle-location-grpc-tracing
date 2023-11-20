package br.gasmartins.locations.infra.config;

import io.opentelemetry.api.OpenTelemetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestOtelConfiguration {

    @Bean
    public OpenTelemetry openTelemetry() {
        return OpenTelemetry.noop();
    }

}

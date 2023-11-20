package br.gasmartins.locations.infra.common.tracing;

import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.extension.trace.propagation.JaegerPropagator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.tracing.otlp.OtlpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OtlpProperties.class)
public class OtelConfiguration {

    private final OtlpProperties otlpProperties;

    @Bean
    public TextMapPropagator jaegerPropagator() {
        return JaegerPropagator.getInstance();
    }

    @Bean
    public OtlpGrpcSpanExporter otlpExporter() {
        return OtlpGrpcSpanExporter.builder()
                                    .setEndpoint(this.otlpProperties.getEndpoint())
                                    .setTimeout(this.otlpProperties.getTimeout())
                                    .build();
    }

}
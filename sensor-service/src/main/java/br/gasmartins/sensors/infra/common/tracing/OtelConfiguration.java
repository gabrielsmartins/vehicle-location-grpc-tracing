package br.gasmartins.sensors.infra.common.tracing;

import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.extension.trace.propagation.JaegerPropagator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.tracing.otlp.OtlpProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OtelConfiguration {

    private final OtlpProperties otlpProperties;

    @Bean
    public TextMapPropagator jaegerPropagator() {
        return JaegerPropagator.getInstance();
    }

    @Bean
    public OtlpGrpcSpanExporter otlpExporter() {
        return OtlpGrpcSpanExporter.getDefault();
    }

}
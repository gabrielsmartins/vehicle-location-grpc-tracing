package br.gasmartins.sensors.infra.grpc.config.interceptor;

import io.grpc.ClientInterceptor;
import io.grpc.ServerInterceptor;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.grpc.v1_6.GrpcTelemetry;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import net.devh.boot.grpc.common.util.InterceptorOrder;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class OtelTraceInterceptorConfiguration {

    @Bean
    @GrpcGlobalServerInterceptor
    @Order(InterceptorOrder.ORDER_TRACING_METRICS + 1)
    public ServerInterceptor serverInterceptor(OpenTelemetry openTelemetry) {
        return GrpcTelemetry.create(openTelemetry).newServerInterceptor();
    }

    @Bean
    @GrpcGlobalClientInterceptor
    public ClientInterceptor clientInterceptor(OpenTelemetry openTelemetry) {
        return GrpcTelemetry.create(openTelemetry).newClientInterceptor();
    }

}

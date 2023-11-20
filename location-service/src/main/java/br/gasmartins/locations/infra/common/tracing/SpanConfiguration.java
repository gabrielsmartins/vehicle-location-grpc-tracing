package br.gasmartins.locations.infra.common.tracing;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationFilter;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.*;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration(proxyBeanMethods = false)
public class SpanConfiguration {

    @Bean
    public NewSpanParser newSpanParser() {
        return new DefaultNewSpanParser();
    }

    @Bean
    public MethodInvocationProcessor methodInvocationProcessor(NewSpanParser newSpanParser, Tracer tracer, BeanFactory beanFactory) {
        return new ImperativeMethodInvocationProcessor(newSpanParser, tracer, beanFactory::getBean, beanFactory::getBean);
    }

    @Bean
    public SpanAspect spanAspect(MethodInvocationProcessor methodInvocationProcessor) {
        return new SpanAspect(methodInvocationProcessor);
    }

    @Bean
    public ObservationFilter urlObservationFilter() {
        return context -> {
            if (context.getName().startsWith("spring.security.")) {
                Observation.Context root = getRoot(context);
                if (root.getName().equals("http.server.requests")) {
                    context.addHighCardinalityKeyValue(Objects.requireNonNull(root.getHighCardinalityKeyValue("http.url")));
                }
            }
            return context;
        };
    }

    private Observation.Context getRoot(Observation.Context context) {
        if (context.getParentObservation() == null) {
            return context;
        } else {
            return getRoot((Observation.Context) context.getParentObservation().getContextView());
        }
    }

    @Bean
    public SpanExportingPredicate actuatorSpanExportingPredicate() {
        return span -> {
            if (span.getTags().get("http.url") != null)  {
                return !span.getTags().get("http.url").startsWith("/actuator");
            }
            return true;
        };
    }

}

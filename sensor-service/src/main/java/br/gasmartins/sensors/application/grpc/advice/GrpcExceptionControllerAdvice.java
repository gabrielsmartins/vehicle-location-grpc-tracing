package br.gasmartins.sensors.application.grpc.advice;

import br.gasmartins.sensors.domain.exceptions.SensorNotFoundException;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcExceptionControllerAdvice {

    @GrpcExceptionHandler
    public StatusException handleInvalidArgument(SensorNotFoundException e) {
        Status status = Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e);
        var metadata = new Metadata();
        metadata.put(Metadata.Key.of("sensor_id", Metadata.ASCII_STRING_MARSHALLER), e.getSensorId().toString());
        return status.asException(metadata);
    }

    @GrpcExceptionHandler(Exception.class)
    public Status handleException(Exception e) {
        return Status.INTERNAL.withDescription(e.getMessage()).withCause(e);
    }

}
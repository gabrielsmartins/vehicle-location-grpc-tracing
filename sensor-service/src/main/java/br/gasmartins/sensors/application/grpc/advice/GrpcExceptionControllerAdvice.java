package br.gasmartins.sensors.application.grpc.advice;

import br.gasmartins.grpc.common.ErrorDetail;
import br.gasmartins.sensors.domain.exceptions.SensorNotFoundException;
import com.google.protobuf.Any;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import java.util.Map;

@GrpcAdvice
@Slf4j
public class GrpcExceptionControllerAdvice {

    @GrpcExceptionHandler
    public StatusException handleInvalidArgument(SensorNotFoundException e) {
        log.warn("Error processing request", e);
        Status status = Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e);
        var metadata = new Metadata();
        metadata.put(Metadata.Key.of("sensor_id", Metadata.ASCII_STRING_MARSHALLER), e.getSensorId().toString());
        return status.asException(metadata);
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleException(Exception e) {
        log.error("Error processing request", e);
        var code = Status.Code.INTERNAL;
        var cause = e.getCause();
        var errorDetail = ErrorDetail.newBuilder()
                                     .setErrorCode(code.toStatus().toString())
                                     .setMessage(e.getMessage())
                                     .putAllMetadata(Map.of("cause", String.valueOf(cause)))
                                     .build();
        var status = com.google.rpc.Status.newBuilder()
                                          .setCode(code.ordinal())
                                          .setMessage("Internal Server Error")
                                          .addDetails(Any.pack(errorDetail))
                                          .build();
        return StatusProto.toStatusRuntimeException(status);
    }

}
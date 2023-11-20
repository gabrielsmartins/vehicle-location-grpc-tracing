package br.gasmartins.locations.interfaces.grpc.advice;

import br.gasmartins.grpc.common.ErrorDetail;
import com.google.protobuf.Any;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import java.util.Map;

@GrpcAdvice
@Slf4j
public class GrpcExceptionControllerAdvice {

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleException(Exception e) {
        log.error("Error processing request", e);
        var code = Status.Code.INTERNAL;
        var cause = e.getCause();
        var message = e.getMessage() == null ? String.valueOf(e.getCause()) : e.getMessage();
        var errorDetail = ErrorDetail.newBuilder()
                                     .setErrorCode(code.toStatus().toString())
                                     .setMessage(message)
                                     .putAllMetadata(Map.of("cause", String.valueOf(cause)))
                                     .build();
        var status = com.google.rpc.Status.newBuilder()
                                          .setCode(code.ordinal())
                                          .setMessage(message)
                                          .addDetails(Any.pack(errorDetail))
                                          .build();
        return StatusProto.toStatusRuntimeException(status);
    }

}
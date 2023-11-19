package br.gasmartins.sensors.application.grpc.advice;

import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.grpc.sensors.SensorServiceGrpc;
import br.gasmartins.sensors.application.grpc.SensorGrpcController;
import br.gasmartins.sensors.domain.exceptions.SensorNotFoundException;
import br.gasmartins.sensors.domain.service.SensorService;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
import net.devh.boot.grpc.server.autoconfigure.GrpcAdviceAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcReflectionServiceAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static br.gasmartins.sensors.application.grpc.support.SensorDataDtoSupport.defaultSensorDataDto;
import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import({SensorGrpcController.class, GrpcExceptionControllerAdvice.class})
@ImportAutoConfiguration({
        GrpcReflectionServiceAutoConfiguration.class,
        GrpcAdviceAutoConfiguration.class,
        GrpcServerAutoConfiguration.class,
        GrpcServerFactoryAutoConfiguration.class
})
@ActiveProfiles("test")
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@DirtiesContext
class GrpcExceptionControllerAdviceTest {

    @MockBean
    private SensorService service;

    private ManagedChannel channel;
    private SensorServiceGrpc.SensorServiceBlockingStub blockingStub;
    private SensorServiceGrpc.SensorServiceStub stub;

    @BeforeEach
    public void setup() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8087)
                                            .usePlaintext()
                                            .build();
        this.blockingStub = SensorServiceGrpc.newBlockingStub(this.channel);
        this.stub = SensorServiceGrpc.newStub(this.channel);
    }

    @AfterEach
    public void tearDown() {
        this.channel.shutdown();
    }

    @Test
    @DisplayName("Given Sensor Data When Error Then Return Internal Error")
    public void givenSensorDataWhenErrorThenReturnInternalError() {


        var responseObserver = new StreamObserver<SensorData>() {

            @Getter
            private Throwable throwable;

            @Override
            public void onNext(SensorData value) {
                fail("Error while processing request");
            }

            @Override
            public void onError(Throwable throwable) {
                this.throwable = throwable;
            }

            @Override
            public void onCompleted() {
                System.out.println("Response is complete");
            }
        };

        var message = "Internal Server Error";
        when(this.service.store(any(br.gasmartins.sensors.domain.SensorData.class))).thenThrow(new RuntimeException(message));

        var requestObserver = this.stub.store(responseObserver);

        var sensorDataDto = defaultSensorDataDto().build();
        requestObserver.onNext(sensorDataDto);
        requestObserver.onCompleted();

        await().atMost(30, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(responseObserver.getThrowable()).isNotNull());
    }

    @Test
    @DisplayName("Given Sensor Id When Not Exists Then Throw Exception")
    public void givenSensorIdWhenNotExistsThenThrowException() {
        var id = UUID.randomUUID();
        var exception = new SensorNotFoundException(id);
        when(this.service.findById(id)).thenThrow(exception);

        var request = StringValue.newBuilder()
                                .setValue(id.toString())
                                .build();

        assertThatThrownBy(() -> this.blockingStub.findBySensorId(request))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessageContaining(exception.getMessage());
    }

}
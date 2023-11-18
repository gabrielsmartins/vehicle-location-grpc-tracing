package br.gasmartins.sensors.application.grpc;


import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.grpc.sensors.SensorDataPage;
import br.gasmartins.grpc.sensors.SensorServiceGrpc;
import br.gasmartins.sensors.application.grpc.advice.GrpcExceptionControllerAdvice;
import br.gasmartins.sensors.domain.service.SensorService;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
import net.devh.boot.grpc.server.autoconfigure.GrpcAdviceAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcReflectionServiceAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static br.gasmartins.sensors.application.grpc.support.SensorDataDtoSupport.defaultSensorDataDto;
import static br.gasmartins.sensors.application.grpc.support.SensorDataDtoSupport.defaultSensorDataPage;
import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static org.assertj.core.api.Assertions.*;
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
class SensorGrpcControllerTest {

    @MockBean
    private SensorService service;

    private static ManagedChannel channel;
    private static SensorServiceGrpc.SensorServiceBlockingStub blockingStub;
    private static SensorServiceGrpc.SensorServiceStub stub;

    @BeforeAll
    public static void setupAll() {
        channel = ManagedChannelBuilder.forAddress("localhost", 8087)
                                        .usePlaintext()
                                        .build();
        blockingStub = SensorServiceGrpc.newBlockingStub(channel);
        stub = SensorServiceGrpc.newStub(channel);
    }

    @AfterAll
    public static void tearDownAll() {
        channel.shutdown();
    }

    @Test
    @DisplayName("Given Sensor Data When Store Then Return Stored Sensor Data")
    public void givenSensorDataWhenStoreThenReturnStoredSensorData() throws InterruptedException {

        var latch = new CountDownLatch(1);
        var responseObserver = new StreamObserver<SensorData>() {

            @Getter
            private final List<SensorData> data = new ArrayList<>();
            @Override
            public void onNext(SensorData value) {
                this.data.add(value);
            }

            @Override
            public void onError(Throwable t) {
                fail("Error while processing request");
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        when(this.service.store(any(br.gasmartins.sensors.domain.SensorData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var streamObserver = stub.store(responseObserver);

        var sensorDataDto = defaultSensorDataDto().build();
        responseObserver.onNext(sensorDataDto);
        responseObserver.onCompleted();

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(streamObserver).isNotNull();
        assertThat(responseObserver.getData()).isNotNull();
    }

    @Test
    @DisplayName("Given Sensor Id When Not Exists Then Throw Exception")
    public void givenSensorIdWhenNotExistsThenThrowException() {
        var sensorData = defaultSensorData().build();
        var id = UUID.randomUUID();
        when(this.service.findById(id)).thenReturn(sensorData);

        var request = StringValue.newBuilder()
                                 .setValue(id.toString())
                                 .build();
        var existingSensorData = blockingStub.findBySensorId(request);
        assertThat(existingSensorData).isNotNull();
    }

    @Test
    @DisplayName("Given Vehicle Id And Interval When Exists Then Return Sensor Data Page")
    public void givenVehicleIdAndIntervalWhenExistsThenReturnSensorDataPage() throws InterruptedException {
        var latch = new CountDownLatch(1);
        var responseObserver = new StreamObserver<SensorDataPage>() {

            @Getter
            private final List<SensorDataPage> data = new ArrayList<>();

            @Override
            public void onNext(SensorDataPage value) {
                this.data.add(value);
            }

            @Override
            public void onError(Throwable t) {
                fail("Error while processing request");
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        var existingSensorData = stub.findByVehicleIdAndOccurredOnBetween(responseObserver);
        var sensorDataPage = defaultSensorDataPage().build();
        responseObserver.onNext(sensorDataPage);
        responseObserver.onCompleted();

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(existingSensorData).isNotNull();
        assertThat(responseObserver.getData()).isNotEmpty();
    }

}
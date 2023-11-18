package br.gasmartins.sensors.application.grpc;


import br.gasmartins.grpc.sensors.SensorServiceGrpc;
import br.gasmartins.sensors.application.grpc.advice.GrpcExceptionControllerAdvice;
import br.gasmartins.sensors.application.grpc.support.SensorDataOutputStreamObserver;
import br.gasmartins.sensors.application.grpc.support.SensorDataPageOutputStreamObserver;
import br.gasmartins.sensors.domain.service.SensorService;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.server.autoconfigure.GrpcAdviceAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcReflectionServiceAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.junit.jupiter.api.*;
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
import static br.gasmartins.sensors.application.grpc.support.SensorDataDtoSupport.defaultSensorDataPage;
import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static org.assertj.core.api.Assertions.assertThat;
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
    public void givenSensorDataWhenStoreThenReturnStoredSensorData()  {
        var sensorDataStreamObserver = new SensorDataOutputStreamObserver();

        when(this.service.store(any(br.gasmartins.sensors.domain.SensorData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var streamObserver = stub.store(sensorDataStreamObserver);

        var sensorDataDto = defaultSensorDataDto().build();
        sensorDataStreamObserver.onNext(sensorDataDto);
        streamObserver.onCompleted();

        await().pollDelay(5, TimeUnit.SECONDS)
               .untilAsserted(() -> assertThat(streamObserver).isNotNull());
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
    public void givenVehicleIdAndIntervalWhenExistsThenReturnSensorDataPage() {
        var responseObserver = new SensorDataPageOutputStreamObserver();

        var existingSensorData = stub.findByVehicleIdAndOccurredOnBetween(responseObserver);
        var sensorDataPage = defaultSensorDataPage().build();
        responseObserver.onNext(sensorDataPage);
        responseObserver.onCompleted();

        await().pollDelay(5, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(existingSensorData).isNotNull());
    }
}
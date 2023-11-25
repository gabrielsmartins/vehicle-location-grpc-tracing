package br.gasmartins.sensors.interfaces.grpc;


import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.grpc.sensors.SensorDataPage;
import br.gasmartins.grpc.sensors.SensorServiceGrpc;
import br.gasmartins.sensors.application.service.SensorService;
import br.gasmartins.sensors.interfaces.grpc.advice.GrpcExceptionControllerAdvice;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static br.gasmartins.sensors.interfaces.grpc.support.SensorDataDtoSupport.defaultSearchSensorDataByVehicleIdParamDto;
import static br.gasmartins.sensors.interfaces.grpc.support.SensorDataDtoSupport.defaultSensorDataDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SensorGrpcControllerTest {

    @MockBean
    private SensorService service;

    private ManagedChannel channel;
    private SensorServiceGrpc.SensorServiceBlockingStub blockingStub;
    private SensorServiceGrpc.SensorServiceStub stub;

    @BeforeAll
    public void setup() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8087)
                .usePlaintext()
                .build();
        this.blockingStub = SensorServiceGrpc.newBlockingStub(this.channel);
        this.stub = SensorServiceGrpc.newStub(this.channel);
    }

    @AfterAll
    public void tearDown() {
        this.channel.shutdown();
    }

    @Test
    @DisplayName("Given Sensor Data When Store Then Return Stored Sensor Data")
    public void givenSensorDataWhenStoreThenReturnStoredSensorData() {
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
                System.out.println("Response is complete");
            }
        };

        when(this.service.store(any(br.gasmartins.sensors.domain.SensorData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var requestObserver = stub.store(responseObserver);

        var sensorDataDto = defaultSensorDataDto().build();
        requestObserver.onNext(sensorDataDto);
        requestObserver.onCompleted();

        await().atMost(30, TimeUnit.SECONDS)
                .untilAsserted(() ->  assertThat(responseObserver.getData()).isNotNull());
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
        var existingSensorData = this.blockingStub.findBySensorId(request);
        assertThat(existingSensorData).isNotNull();
    }

    @Test
    @DisplayName("Given Vehicle Id And Interval When Exists Then Return Sensor Data Page")
    public void givenVehicleIdAndIntervalWhenExistsThenReturnSensorDataPage() {
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
                System.out.println("Response is complete");
            }
        };

        var requestObserver = this.stub.findByVehicleIdAndOccurredOnBetween(responseObserver);
        var paramDto = defaultSearchSensorDataByVehicleIdParamDto().build();
        requestObserver.onNext(paramDto);
        requestObserver.onCompleted();

        await().atMost(30, TimeUnit.SECONDS)
                .untilAsserted(() ->  assertThat(responseObserver.getData()).isNotEmpty());
    }

}
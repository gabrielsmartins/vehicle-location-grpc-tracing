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

import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static org.assertj.core.api.Assertions.assertThat;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SensorGrpcControllerTest {

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
    @DisplayName("Given Sensor Data When Store Then Return Stored Sensor Data")
    public void givenSensorDataWhenStoreThenReturnStoredSensorData() {

        var sensorDataStreamObserver = new StreamObserver<SensorData>() {
            @Override
            public void onNext(SensorData value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        };


        when(this.service.store(any(br.gasmartins.sensors.domain.SensorData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var streamObserver = this.stub.store(sensorDataStreamObserver);
        assertThat(streamObserver).isNotNull();
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
            @Override
            public void onNext(SensorDataPage value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        };


        var existingSensorData = this.stub.findByVehicleIdAndOccurredOnBetween(responseObserver);
        assertThat(existingSensorData).isNotNull();
    }
}
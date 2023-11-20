package br.gasmartins.sensors.interfaces.grpc.observer;

import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.sensors.application.service.SensorService;
import br.gasmartins.sensors.interfaces.grpc.observer.SensorDataStreamObserver;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.sensors.interfaces.grpc.support.SensorDataDtoSupport.defaultSensorDataDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorDataStreamObserverTest {

    private SensorDataStreamObserver observer;
    private SensorService service;

    private StreamObserver<SensorData> responseObserver;

    @BeforeEach
    public void setup() {
        this.service = mock(SensorService.class);
        this.responseObserver = mock(StreamObserver.class);
        this.observer = new SensorDataStreamObserver(this.service, responseObserver);
    }

    @Test
    @DisplayName("Given Sensor Data When Next Then Store")
    public void givenSensorDataWhenNextThenStore() {
        var sensorDataDto = defaultSensorDataDto().build();

        when(this.service.store(any(br.gasmartins.sensors.domain.SensorData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        this.observer.onNext(sensorDataDto);

        verify(this.service, times(1)).store(any(br.gasmartins.sensors.domain.SensorData.class));
        verify(this.responseObserver, times(1)).onNext(any(SensorData.class));
    }

    @Test
    @DisplayName("Given Error When Throw Then Log")
    public void givenErrorWhenThrowThenLog() {
        this.observer.onError(new RuntimeException("Error"));
    }


    @Test
    @DisplayName("Given Request When Completes Then Call Observer")
    public void givenRequestWhenCompletesThenCallObserver() {
        this.observer.onCompleted();
        verify(this.responseObserver, times(1)).onCompleted();
    }

}
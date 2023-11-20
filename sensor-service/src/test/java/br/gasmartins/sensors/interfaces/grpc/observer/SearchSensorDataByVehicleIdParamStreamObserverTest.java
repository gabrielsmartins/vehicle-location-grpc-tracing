package br.gasmartins.sensors.interfaces.grpc.observer;


import br.gasmartins.grpc.sensors.SensorDataPage;
import br.gasmartins.sensors.application.service.SensorService;
import br.gasmartins.sensors.interfaces.grpc.observer.SearchSensorDataByVehicleIdParamStreamObserver;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static br.gasmartins.sensors.interfaces.grpc.support.SensorDataDtoSupport.defaultSearchSensorDataByVehicleIdParamDto;
import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchSensorDataByVehicleIdParamStreamObserverTest {

    private SearchSensorDataByVehicleIdParamStreamObserver observer;
    private SensorService sensorService;
    private StreamObserver<SensorDataPage> responseObserver;

    @BeforeEach
    public void setup() {
        this.sensorService = mock(SensorService.class);
        this.responseObserver = mock(StreamObserver.class);
        this.observer = new SearchSensorDataByVehicleIdParamStreamObserver(this.sensorService, this.responseObserver);
    }

    @Test
    @DisplayName("Given Param When Exists Then Return Page")
    public void givenParamWhenExistsThenReturnPage() {
        var param = defaultSearchSensorDataByVehicleIdParamDto().build();

        var sensorData = defaultSensorData().build();
        when(this.sensorService.findByVehicleIdAndOccurredOnBetween(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(sensorData), PageRequest.of(0, 30), 1));

        this.observer.onNext(param);

        verify(this.sensorService, times(1)).findByVehicleIdAndOccurredOnBetween(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));
    }

}
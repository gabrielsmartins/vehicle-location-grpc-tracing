package br.gasmartins.sensors.infra.persistence.adapter;

import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import br.gasmartins.sensors.infra.persistence.service.SensorPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static br.gasmartins.sensors.infra.persistence.support.SensorDataEntitySupport.defaultSensorDataEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorPersistenceAdapterTest {

    private SensorPersistenceAdapter adapter;
    private SensorPersistenceService service;

    @BeforeEach
    public void setup() {
        this.service = mock(SensorPersistenceService.class);
        this.adapter = new SensorPersistenceAdapter(this.service);
    }

    @Test
    @DisplayName("Given Sensor Data When Store Then Return Stored Sensor Data")
    public void givenSensorDataWhenStoreThenReturnStoredSensorData() {
        when(this.service.store(any(SensorDataEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var sensorData = defaultSensorData().build();
        var storedSensorDataEntity = this.adapter.store(sensorData);
        assertThat(storedSensorDataEntity).isNotNull();
    }

    @Test
    @DisplayName("Given Sensor Id When Exists Then Return Sensor Data")
    public void givenSensorIdWhenExistsThenReturnSensorData() {
        var sensorDataEntity = defaultSensorDataEntity().build();
        var id = sensorDataEntity.getSensorId();

        when(this.service.findById(id)).thenReturn(Optional.of(sensorDataEntity));

        var optionalSensorData = this.adapter.findById(id);
        assertThat(optionalSensorData).isPresent();
    }

    @Test
    @DisplayName("Given Vehicle Id And Interval When Exists Then Return Sensor Data Page")
    public void givenVehicleIdAndIntervalWhenExistsThenReturnSensorDataPage() {
        var sensorDataEntity = defaultSensorDataEntity().build();
        var id = sensorDataEntity.getVehicleId();
        var pageRequest = PageRequest.of(0, 30);

        var content = List.of(sensorDataEntity);
        var startOccurredOn = LocalDateTime.now().minusDays(2);
        var endOccurredOn = LocalDateTime.now();
        when(this.service.findByVehicleIdAndOccurredOnBetween(id, startOccurredOn, endOccurredOn, pageRequest)).thenReturn(new PageImpl<>(content, pageRequest, 1));

        var page = this.adapter.findByVehicleIdAndOccurredOnBetween(id, startOccurredOn, endOccurredOn, pageRequest);
        assertThat(page).isNotNull();
    }
}
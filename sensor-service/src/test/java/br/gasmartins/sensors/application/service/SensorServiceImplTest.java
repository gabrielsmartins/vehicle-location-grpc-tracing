package br.gasmartins.sensors.application.service;

import br.gasmartins.sensors.application.query.LocationQuery;
import br.gasmartins.sensors.domain.Coordinates;
import br.gasmartins.sensors.domain.exceptions.SensorNotFoundException;
import br.gasmartins.sensors.application.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.gasmartins.sensors.domain.support.LocationSupport.defaultLocation;
import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorServiceImplTest {

    private SensorServiceImpl service;
    private LocationQuery query;
    private SensorRepository repository;

    @BeforeEach
    public void setup() {
        this.query = mock(LocationQuery.class);
        this.repository = mock(SensorRepository.class);
        this.service = new SensorServiceImpl(this.query, this.repository);
    }

    @Test
    @DisplayName("Given Sensor Data When Store Then Return Stored Sensor Data")
    public void givenSensorDataWhenStoreThenReturnStoredSensorData() {
        var sensorData = defaultSensorData().build();

        when(this.query.findByCoordinates(any(Coordinates.class))).thenReturn(defaultLocation().build());
        when(this.repository.store(sensorData)).thenAnswer(invocation -> invocation.getArgument(0));

        var storedSensorDataEntity = this.service.store(sensorData);
        assertThat(storedSensorDataEntity).isNotNull();
    }

    @Test
    @DisplayName("Given Sensor Id When Exists Then Return Sensor Data")
    public void givenSensorIdWhenExistsThenReturnSensorData() {
        var sensorData = defaultSensorData().build();
        var id = sensorData.getSensorId();

        when(this.repository.findById(id)).thenReturn(Optional.of(sensorData));

        var existingSensorData = this.service.findById(id);
        assertThat(existingSensorData).isNotNull();
    }

    @Test
    @DisplayName("Given Sensor Id When Not Exists Then Throw Exception")
    public void givenSensorIdWhenNotExistsThenThrowException() {
        var id = UUID.randomUUID();

        when(this.repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.service.findById(id))
                .isInstanceOf(SensorNotFoundException.class);
    }

    @Test
    @DisplayName("Given Vehicle Id And Interval When Exists Then Return Sensor Data Page")
    public void givenVehicleIdAndIntervalWhenExistsThenReturnSensorDataPage() {
        var sensorData = defaultSensorData().build();
        var id = sensorData.getVehicleId();
        var pageRequest = PageRequest.of(0, 30);

        var content = List.of(sensorData);
        var startOccurredOn = LocalDateTime.now().minusDays(2);
        var endOccurredOn = LocalDateTime.now();
        when(this.repository.findByVehicleIdAndOccurredOnBetween(id, startOccurredOn, endOccurredOn, pageRequest)).thenReturn(new PageImpl<>(content, pageRequest, 1));

        var page = this.service.findByVehicleIdAndOccurredOnBetween(id, startOccurredOn, endOccurredOn, pageRequest);
        assertThat(page).isNotNull();
    }

}
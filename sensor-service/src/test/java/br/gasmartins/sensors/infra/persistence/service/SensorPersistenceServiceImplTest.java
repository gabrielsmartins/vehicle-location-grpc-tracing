package br.gasmartins.sensors.infra.persistence.service;

import br.gasmartins.sensors.infra.persistence.repository.SensorDataElasticSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.gasmartins.sensors.infra.persistence.support.SensorDataEntitySupport.defaultSensorDataEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorPersistenceServiceImplTest {

    private SensorPersistenceServiceImpl service;
    private SensorDataElasticSearchRepository repository;

    @BeforeEach
    public void setup() {
        this.repository = mock(SensorDataElasticSearchRepository.class);
        this.service = new SensorPersistenceServiceImpl(this.repository);
    }

    @Test
    @DisplayName("Given Sensor Data When Store Then Return Stored Sensor Data")
    public void givenSensorDataWhenStoreThenReturnStoredSensorData() {
        var sensorDataEntity = defaultSensorDataEntity().build();

        when(this.repository.save(sensorDataEntity)).thenAnswer(invocation -> invocation.getArgument(0));

        var storedSensorDataEntity = this.service.store(sensorDataEntity);
        assertThat(storedSensorDataEntity).isNotNull();
    }

    @Test
    @DisplayName("Given Sensor Id When Exists Then Return Sensor Data")
    public void givenSensorIdWhenExistsThenReturnSensorData() {
        var sensorDataEntity = defaultSensorDataEntity().build();
        var id = sensorDataEntity.getSensorId();

        when(this.repository.findById(id)).thenReturn(Optional.of(sensorDataEntity));

        var optionalSensorDataEntity = this.service.findById(id);
        assertThat(optionalSensorDataEntity).isPresent();
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
        when(this.repository.findByVehicleIdAndOccurredOnBetween(id, startOccurredOn, endOccurredOn, pageRequest)).thenReturn(new PageImpl<>(content, pageRequest, 1));

        var page = this.service.findByVehicleIdAndOccurredOnBetween(id, startOccurredOn, endOccurredOn, pageRequest);
        assertThat(page).isNotNull();
    }

}
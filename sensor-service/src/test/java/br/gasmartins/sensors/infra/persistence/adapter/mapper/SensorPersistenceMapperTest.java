package br.gasmartins.sensors.infra.persistence.adapter.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static br.gasmartins.sensors.infra.persistence.support.SensorDataEntitySupport.defaultSensorDataEntity;
import static org.assertj.core.api.Assertions.assertThat;

class SensorPersistenceMapperTest {

    @Test
    @DisplayName("Given Sensor When Map Then Return Sensor Entity")
    public void givenSensorWhenMapThenReturnSensorEntity() {
        var sensorData = defaultSensorData().build();

        var sensorDataEntity = SensorPersistenceMapper.mapToEntity(sensorData);

        assertThat(sensorDataEntity).isNotNull();
        assertThat(sensorDataEntity).hasNoNullFieldsOrProperties();
        assertThat(sensorDataEntity.getSensorId()).isEqualTo(sensorData.getSensorId());
        assertThat(sensorDataEntity.getVehicleId()).isEqualTo(sensorData.getVehicleId());
        assertThat(sensorDataEntity.getVehicleState()).isEqualTo(sensorData.getVehicleState());
        assertThat(sensorDataEntity.getOccurredOn()).isEqualTo(sensorData.getOccurredOn());
        assertThat(sensorDataEntity.getSpeed()).isEqualTo(sensorData.getSpeed());
        assertThat(sensorDataEntity.getCoordinates().getLatitude()).isEqualTo(sensorData.getCoordinates().getLatitude());
        assertThat(sensorDataEntity.getCoordinates().getLongitude()).isEqualTo(sensorData.getCoordinates().getLongitude());
        assertThat(sensorData.getLocation()).isPresent();
        assertThat(sensorDataEntity.getLocation().getCountry()).isEqualTo(sensorData.getLocation().get().getCountry());
        assertThat(sensorDataEntity.getLocation().getState()).isEqualTo(sensorData.getLocation().get().getState());
        assertThat(sensorDataEntity.getLocation().getCity()).isEqualTo(sensorData.getLocation().get().getCity());
        assertThat(sensorDataEntity.getLocation().getDistrict()).isEqualTo(sensorData.getLocation().get().getDistrict());
        assertThat(sensorDataEntity.getLocation().getAddress()).isEqualTo(sensorData.getLocation().get().getAddress());
        assertThat(sensorDataEntity.getLocation().getZipCode()).isEqualTo(sensorData.getLocation().get().getZipCode());
    }

    @Test
    @DisplayName("Given Sensor Entity When Map Then Return Sensor")
    public void givenSensorEntityWhenMapThenReturnSensor() {
        var sensorDataEntity = defaultSensorDataEntity().build();

        var sensorData = SensorPersistenceMapper.mapToDomain(sensorDataEntity);

        assertThat(sensorData).isNotNull();
        assertThat(sensorData).hasNoNullFieldsOrProperties();
        assertThat(sensorData.getSensorId()).isEqualTo(sensorDataEntity.getSensorId());
        assertThat(sensorData.getVehicleId()).isEqualTo(sensorDataEntity.getVehicleId());
        assertThat(sensorData.getVehicleState()).isEqualTo(sensorDataEntity.getVehicleState());
        assertThat(sensorData.getOccurredOn()).isEqualTo(sensorDataEntity.getOccurredOn());
        assertThat(sensorData.getSpeed()).isEqualTo(sensorDataEntity.getSpeed());
        assertThat(sensorData.getCoordinates().getLatitude()).isEqualTo(sensorDataEntity.getCoordinates().getLatitude());
        assertThat(sensorData.getCoordinates().getLongitude()).isEqualTo(sensorDataEntity.getCoordinates().getLongitude());
        assertThat(sensorData.getLocation()).isPresent();
        assertThat(sensorData.getLocation().get().getCountry()).isEqualTo(sensorDataEntity.getLocation().getCountry());
        assertThat(sensorData.getLocation().get().getState()).isEqualTo(sensorDataEntity.getLocation().getState());
        assertThat(sensorData.getLocation().get().getCity()).isEqualTo(sensorDataEntity.getLocation().getCity());
        assertThat(sensorData.getLocation().get().getDistrict()).isEqualTo(sensorDataEntity.getLocation().getDistrict());
        assertThat(sensorData.getLocation().get().getAddress()).isEqualTo(sensorDataEntity.getLocation().getAddress());
        assertThat(sensorData.getLocation().get().getZipCode()).isEqualTo(sensorDataEntity.getLocation().getZipCode());
    }

}
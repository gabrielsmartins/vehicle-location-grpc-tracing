package br.gasmartins.sensors.interfaces.grpc.mapper;


import br.gasmartins.sensors.infra.persistence.adapter.mapper.TimestampGrpcMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.sensors.interfaces.grpc.support.SensorDataDtoSupport.defaultSensorDataDto;
import static br.gasmartins.sensors.domain.support.SensorDataSupport.defaultSensorData;
import static org.assertj.core.api.Assertions.assertThat;

class SensorGrpcMapperTest {

    @Test
    @DisplayName("Given Sensor Dto When Map Then Return Sensor")
    public void givenSensorDtoWhenMapThenReturnSensor() {
        var sensorDataDto = defaultSensorDataDto().build();
        var sensorData = SensorGrpcMapper.mapToDomain(sensorDataDto);

        assertThat(sensorData).isNotNull();
        assertThat(sensorData).hasNoNullFieldsOrProperties();
        assertThat(sensorData.getSensorId().toString()).isEqualTo(sensorDataDto.getSensorId());
        assertThat(sensorData.getVehicleId().toString()).isEqualTo(sensorDataDto.getVehicleId());
        assertThat(sensorData.getVehicleState().name()).isEqualTo(sensorDataDto.getVehicleState().name());
        assertThat(sensorData.getSpeed()).isEqualTo(sensorDataDto.getSpeed());
        assertThat(sensorData.getOccurredOn()).isEqualTo(TimestampGrpcMapper.toLocalDatetime(sensorDataDto.getOccurredOn()));
        assertThat(sensorData.getCoordinates().getLatitude()).isEqualTo(sensorDataDto.getCoordinates().getLatitude());
        assertThat(sensorData.getCoordinates().getLongitude()).isEqualTo(sensorDataDto.getCoordinates().getLongitude());
    }

    @Test
    @DisplayName("Given Sensor When Map Then Return Sensor Dto")
    public void givenSensorWhenMapThenReturnSensorDto() {
        var sensorData = defaultSensorData().build();
        var sensorDataDto = SensorGrpcMapper.mapToDto(sensorData);

        assertThat(sensorDataDto).hasNoNullFieldsOrProperties();
        assertThat(sensorDataDto.getSensorId()).isEqualTo(sensorData.getSensorId().toString());
        assertThat(sensorDataDto.getVehicleId()).isEqualTo(sensorData.getVehicleId().toString());
        assertThat(sensorDataDto.getVehicleState().name()).isEqualTo(sensorData.getVehicleState().name());
        assertThat(sensorDataDto.getSpeed()).isEqualTo(sensorData.getSpeed());
        assertThat(sensorDataDto.getOccurredOn()).isEqualTo(TimestampGrpcMapper.toTimestamp(sensorData.getOccurredOn()));
        assertThat(sensorDataDto.getCoordinates().getLatitude()).isEqualTo(sensorData.getCoordinates().getLatitude());
        assertThat(sensorDataDto.getCoordinates().getLongitude()).isEqualTo(sensorData.getCoordinates().getLongitude());
        assertThat(sensorData.getLocation()).isPresent();
        assertThat(sensorDataDto.getLocation().getCountry()).isEqualTo(sensorData.getLocation().get().getCountry());
        assertThat(sensorDataDto.getLocation().getState()).isEqualTo(sensorData.getLocation().get().getState());
        assertThat(sensorDataDto.getLocation().getCity()).isEqualTo(sensorData.getLocation().get().getCity());
        assertThat(sensorDataDto.getLocation().getDistrict()).isEqualTo(sensorData.getLocation().get().getDistrict());
        assertThat(sensorDataDto.getLocation().getAddress()).isEqualTo(sensorData.getLocation().get().getAddress());
        assertThat(sensorDataDto.getLocation().getZipCode()).isEqualTo(sensorData.getLocation().get().getZipCode());
    }

}
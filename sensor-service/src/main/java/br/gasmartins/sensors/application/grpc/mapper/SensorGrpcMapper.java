package br.gasmartins.sensors.application.grpc.mapper;

import br.gasmartins.sensors.domain.Location;
import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.infra.persistence.adapter.mapper.TimestampGrpcMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorGrpcMapper {

    public static SensorData mapToDomain(br.gasmartins.grpc.sensors.SensorData sensorDataDto) {
        if (sensorDataDto == null) {
            return null;
        }
        var location = sensorDataDto.getLocation();
        var latitude = location.getLatitude();
        var longitude = location.getLongitude();
        return SensorData.builder()
                        .withId(UUID.fromString(sensorDataDto.getDeviceId()))
                        .withVehicleId(UUID.fromString(sensorDataDto.getVehicleId()))
                        .withLocation(new Location(latitude, longitude))
                        .withSpeed(sensorDataDto.getSpeed())
                        .withOccurredOn(TimestampGrpcMapper.toLocalDatetime(sensorDataDto.getOccurredOn()))
                        .build();
    }

}

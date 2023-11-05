package br.gasmartins.sensors.application.grpc.mapper;

import br.gasmartins.sensors.domain.Coordinates;
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
        var coordinates = sensorDataDto.getCoordinates();
        var latitude = coordinates.getLatitude();
        var longitude = coordinates.getLongitude();
        return SensorData.builder()
                         .withId(UUID.fromString(sensorDataDto.getDeviceId()))
                         .withVehicleId(UUID.fromString(sensorDataDto.getVehicleId()))
                         .withCoordinates(new Coordinates(latitude, longitude))
                         .withSpeed(sensorDataDto.getSpeed())
                         .withOccurredOn(TimestampGrpcMapper.toLocalDatetime(sensorDataDto.getOccurredOn()))
                         .build();
    }

    public static br.gasmartins.grpc.sensors.SensorData mapToDto(SensorData sensorData) {
        if (sensorData == null) {
            return null;
        }
        var coordinates = sensorData.getCoordinates();
        var latitude = coordinates.getLatitude();
        var longitude = coordinates.getLongitude();
        return br.gasmartins.grpc.sensors.SensorData.newBuilder()
                                         .setDeviceId(sensorData.toString())
                                         .setVehicleId(sensorData.getVehicleId().toString())
                                         .setCoordinates(br.gasmartins.grpc.sensors.Coordinates.newBuilder()
                                                 .setLatitude(latitude)
                                                 .setLongitude(longitude)
                                                 .build())
                                         .setSpeed(sensorData.getSpeed())
                                         .setOccurredOn(TimestampGrpcMapper.toTimestamp(sensorData.getOccurredOn()))
                                         .build();
        }

}

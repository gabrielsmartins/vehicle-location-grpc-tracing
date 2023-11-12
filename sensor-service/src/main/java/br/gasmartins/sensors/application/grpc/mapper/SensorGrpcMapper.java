package br.gasmartins.sensors.application.grpc.mapper;

import br.gasmartins.grpc.sensors.Location;
import br.gasmartins.grpc.sensors.SensorDataPage;
import br.gasmartins.sensors.domain.Coordinates;
import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.domain.enums.VehicleState;
import br.gasmartins.sensors.infra.persistence.adapter.mapper.TimestampGrpcMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.UUID;
import java.util.stream.Collectors;

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
                         .withSensorId(UUID.fromString(sensorDataDto.getSensorId()))
                         .withVehicleId(UUID.fromString(sensorDataDto.getVehicleId()))
                         .withVehicleState(VehicleState.fromDescription(sensorDataDto.getVehicleState().name()))
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
                                         .setSensorId(sensorData.getSensorId().toString())
                                         .setVehicleId(sensorData.getVehicleId().toString())
                                         .setVehicleState(br.gasmartins.grpc.sensors.VehicleState.valueOf(sensorData.getVehicleState().name()))
                                         .setCoordinates(br.gasmartins.grpc.sensors.Coordinates.newBuilder()
                                                 .setLatitude(latitude)
                                                 .setLongitude(longitude)
                                                 .build())
                                         .setSpeed(sensorData.getSpeed())
                                         .setLocation(sensorData.getLocation().isEmpty() ? Location.getDefaultInstance() : mapToLocationDto(sensorData.getLocation().get()))
                                         .setOccurredOn(TimestampGrpcMapper.toTimestamp(sensorData.getOccurredOn()))
                                         .build();
    }

    public static SensorDataPage mapToDto(Page<SensorData> page) {
        if (page == null) {
            return null;
        }
        var data = page.getContent()
                       .stream()
                       .map(SensorGrpcMapper::mapToDto)
                       .collect(Collectors.toList());
        return SensorDataPage.newBuilder()
                             .addAllData(data)
                             .setPage(mapToPageDto(page))
                             .build();
    }

    private static br.gasmartins.grpc.common.Page mapToPageDto(Page<SensorData> page) {
        return br.gasmartins.grpc.common.Page.newBuilder()
                                             .setPage(page.getNumber())
                                             .setPageSize(page.getSize())
                                             .setTotalPages(page.getTotalPages())
                                             .setTotalElements(page.getTotalElements())
                                             .setFirst(page.isFirst())
                                             .setLast(page.isLast())
                                             .setHasNext(page.hasNext())
                                             .setHasPrevious(page.hasPrevious())
                                             .build();
    }

    private static Location mapToLocationDto(br.gasmartins.sensors.domain.Location location) {
        if (location == null) {
            return null;
        }
        return Location.newBuilder()
                        .setCountry(location.getCountry())
                        .setState(location.getState())
                        .setCity(location.getCity())
                        .setDistrict(location.getDistrict())
                        .setAddress(location.getAddress())
                        .setZipCode(location.getZipCode())
                        .build();
    }

}

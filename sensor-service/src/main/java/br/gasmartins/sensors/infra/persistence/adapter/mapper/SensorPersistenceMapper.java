package br.gasmartins.sensors.infra.persistence.adapter.mapper;

import br.gasmartins.sensors.domain.Coordinates;
import br.gasmartins.sensors.domain.Location;
import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.infra.persistence.entity.CoordinatesEntity;
import br.gasmartins.sensors.infra.persistence.entity.LocationEntity;
import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorPersistenceMapper {

    public static SensorDataEntity mapToEntity(SensorData sensorData) {
        if (sensorData == null) {
            return null;
        }
        var coordinates = sensorData.getCoordinates();
        var latitude = coordinates.getLatitude();
        var longitude = coordinates.getLongitude();
        return SensorDataEntity.builder()
                               .withSensorId(sensorData.getSensorId())
                               .withVehicleId(sensorData.getVehicleId())
                               .withVehicleState(sensorData.getVehicleState())
                               .withCoordinates(new CoordinatesEntity(latitude, longitude))
                               .withLocation(sensorData.getLocation().map(SensorPersistenceMapper::mapToLocationEntity).orElse(null))
                               .withOccurredOn(sensorData.getOccurredOn())
                               .withSpeed(sensorData.getSpeed())
                               .build();
    }

    private static LocationEntity mapToLocationEntity(Location location) {
        if (location == null) {
            return null;
        }
        return LocationEntity.builder()
                             .withCountry(location.getCountry())
                             .withState(location.getState())
                             .withCity(location.getCity())
                             .withDistrict(location.getDistrict())
                             .withAddress(location.getAddress())
                             .withZipCode(location.getZipCode())
                             .build();
    }

    public static SensorData mapToDomain(SensorDataEntity sensorDataEntity) {
        if (sensorDataEntity == null) {
            return null;
        }
        var coordinates = sensorDataEntity.getCoordinates();
        var latitude = coordinates.getLatitude();
        var longitude = coordinates.getLongitude();
        return SensorData.builder()
                         .withSensorId(sensorDataEntity.getSensorId())
                         .withVehicleId(sensorDataEntity.getVehicleId())
                         .withVehicleState(sensorDataEntity.getVehicleState())
                         .withCoordinates(new Coordinates(latitude, longitude))
                         .withLocation(mapToLocation(sensorDataEntity.getLocation()))
                         .withOccurredOn(sensorDataEntity.getOccurredOn())
                         .withSpeed(sensorDataEntity.getSpeed())
                         .build();
    }

    private static Location mapToLocation(LocationEntity locationEntity) {
        if (locationEntity == null) {
            return null;
        }
        return Location.builder()
                       .withCountry(locationEntity.getCountry())
                       .withState(locationEntity.getState())
                       .withCity(locationEntity.getCity())
                       .withDistrict(locationEntity.getDistrict())
                       .withAddress(locationEntity.getAddress())
                       .withZipCode(locationEntity.getZipCode())
                       .build();
    }

    public static Page<SensorData> mapToDomain(Page<SensorDataEntity> pageEntity) {
        if (pageEntity == null) {
            return null;
        }
        var content = pageEntity.getContent();
        var sensorData = content.stream()
                                .map(SensorPersistenceMapper::mapToDomain)
                                .collect(Collectors.toList());
        return new PageImpl<>(sensorData, pageEntity.getPageable(), pageEntity.getTotalElements());
    }

}

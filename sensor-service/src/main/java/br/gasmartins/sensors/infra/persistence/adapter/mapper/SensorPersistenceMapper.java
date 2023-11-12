package br.gasmartins.sensors.infra.persistence.adapter.mapper;

import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.domain.Coordinates;
import br.gasmartins.sensors.infra.persistence.entity.LocationEntity;
import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorPersistenceMapper {

    public static SensorDataEntity mapToEntity(SensorData sensorData) {
        if (sensorData == null) {
            return null;
        }
        var location = sensorData.getCoordinates();
        var latitude = location.getLatitude();
        var longitude = location.getLongitude();
        return SensorDataEntity.builder()
                               .withId(sensorData.getSensorId())
                               .withVehicleId(sensorData.getVehicleId())
                               .withLocation(new LocationEntity(latitude, longitude))
                               .withOccurredOn(sensorData.getOccurredOn())
                               .withSpeed(sensorData.getSpeed())
                               .build();
    }

    public static SensorData mapToDomain(SensorDataEntity sensorDataEntity) {
        if (sensorDataEntity == null) {
            return null;
        }
        var location = sensorDataEntity.getLocation();
        var latitude = location.getLatitude();
        var longitude = location.getLongitude();
        return SensorData.builder()
                         .withId(sensorDataEntity.getId())
                         .withVehicleId(sensorDataEntity.getVehicleId())
                         .withCoordinates(new Coordinates(latitude, longitude))
                         .withOccurredOn(sensorDataEntity.getOccurredOn())
                         .withSpeed(sensorDataEntity.getSpeed())
                         .build();
    }

}

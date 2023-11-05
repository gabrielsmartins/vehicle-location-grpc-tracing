package br.gasmartins.sensors.infra.persistence.adapter.mapper;

import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.domain.Location;
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
        var location = sensorData.getLocation();
        var latitude = location.getLatitude();
        var longitude = location.getLongitude();
        return SensorDataEntity.builder()
                               .withId(sensorData.getId())
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
                         .withLocation(new Location(latitude, longitude))
                         .withOccurredOn(sensorDataEntity.getOccurredOn())
                         .withSpeed(sensorDataEntity.getSpeed())
                         .build();
    }

}

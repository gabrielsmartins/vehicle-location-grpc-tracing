package br.gasmartins.sensors.application.grpc.support;


import br.gasmartins.grpc.sensors.Coordinates;
import br.gasmartins.grpc.sensors.Location;
import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.grpc.sensors.VehicleState;
import com.google.protobuf.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorDataDtoSupport {

    public static SensorData.Builder defaultSensorDataDto() {
        return SensorData.newBuilder()
                .setSensorId(UUID.randomUUID().toString())
                .setVehicleId(UUID.randomUUID().toString())
                .setSpeed(50)
                .setVehicleStateValue(VehicleState.MOVING_VALUE)
                .setOccurredOn(Timestamp.getDefaultInstance())
                .setCoordinates(Coordinates.newBuilder()
                        .setLongitude(10)
                        .setLatitude(20)
                        .build())
                .setLocation(Location.newBuilder()
                        .setCountry("USA")
                        .setState("Foo")
                        .setCity("Bar")
                        .setDistrict("District")
                        .setAddress("3rd Street")
                        .setZipCode("0646454")
                        .build())
                ;
    }

}

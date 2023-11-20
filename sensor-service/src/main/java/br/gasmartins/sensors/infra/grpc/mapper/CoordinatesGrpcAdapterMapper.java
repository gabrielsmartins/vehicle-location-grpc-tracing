package br.gasmartins.sensors.infra.grpc.mapper;

import br.gasmartins.grpc.locations.Coordinates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinatesGrpcAdapterMapper {

    public static Coordinates mapToDto(br.gasmartins.sensors.domain.Coordinates coordinates) {
        if (coordinates == null) {
            return null;
        }
        return Coordinates.newBuilder()
                          .setLatitude(coordinates.getLatitude())
                          .setLongitude(coordinates.getLongitude())
                          .build();
    }
}

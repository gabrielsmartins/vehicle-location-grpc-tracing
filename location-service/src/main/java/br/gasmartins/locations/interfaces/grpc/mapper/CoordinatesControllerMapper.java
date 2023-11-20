package br.gasmartins.locations.interfaces.grpc.mapper;

import br.gasmartins.grpc.locations.Coordinates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinatesControllerMapper {

    public static br.gasmartins.locations.domain.Coordinates mapToDomain(Coordinates coordinates) {
        if (coordinates == null) {
            return null;
        }
        return br.gasmartins.locations.domain.Coordinates.builder()
                                             .withLatitude(coordinates.getLatitude())
                                             .withLongitude(coordinates.getLongitude())
                                             .build();
    }

}

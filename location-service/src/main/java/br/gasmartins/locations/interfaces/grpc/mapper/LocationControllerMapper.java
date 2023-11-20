package br.gasmartins.locations.interfaces.grpc.mapper;

import br.gasmartins.grpc.locations.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationControllerMapper {

    public static Location mapToDto(br.gasmartins.locations.domain.Location location) {
        if (location == null) {
            return null;
        }
        return Location.newBuilder()
                       .setAddress(location.getAddress() == null ? "" : location.getAddress())
                       .setCity(location.getCity() == null ? "" : location.getCity())
                       .setCountry(location.getCountry() == null ? "" : location.getCountry())
                       .setDistrict(location.getDistrict() == null ? "" : location.getDistrict())
                       .setState(location.getState() == null ? "" : location.getState())
                       .setZipCode(location.getZipCode() == null ? "" : location.getZipCode())
                       .build();
    }
}

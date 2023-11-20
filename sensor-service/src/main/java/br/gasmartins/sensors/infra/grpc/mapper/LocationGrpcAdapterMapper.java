package br.gasmartins.sensors.infra.grpc.mapper;

import br.gasmartins.sensors.domain.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationGrpcAdapterMapper {

    public static Location mapToDomain(br.gasmartins.grpc.locations.Location locationDto) {
        if (locationDto == null) {
            return null;
        }
        return Location.builder()
                       .withAddress(locationDto.getAddress())
                       .withCity(locationDto.getCity())
                       .withCountry(locationDto.getCountry())
                       .withDistrict(locationDto.getDistrict())
                       .withState(locationDto.getState())
                       .withZipCode(locationDto.getZipCode())
                       .build();
    }

}

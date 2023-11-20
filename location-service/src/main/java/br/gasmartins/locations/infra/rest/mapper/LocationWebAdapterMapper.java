package br.gasmartins.locations.infra.rest.mapper;

import br.gasmartins.locations.domain.Location;
import br.gasmartins.locations.infra.rest.client.dto.LocationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationWebAdapterMapper {

    public static Location mapToDto(LocationDto locationDto) {
        if (locationDto == null) {
            return null;
        }
        return Location.builder()
                       .withAddress(locationDto.getStreet())
                       .withCity(locationDto.getLocality())
                       .withZipCode(locationDto.getPostalCode())
                       .withState(locationDto.getRegion())
                       .withCountry(locationDto.getCountry())
                       .withDistrict(locationDto.getNeighbourhood())
                       .build();
    }

 }

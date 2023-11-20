package br.gasmartins.sensors.infra.grpc.mapper;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.sensors.infra.grpc.support.LocationDtoSupport.defaultLocationDto;
import static org.assertj.core.api.Assertions.assertThat;

class LocationGrpcAdapterMapperTest {

    @Test
    @DisplayName("Given Location When Map Then Return Location Dto")
    public void givenLocationWhenMapThenReturnLocationDto() {
        var locationDto = defaultLocationDto().build();
        var location = LocationGrpcAdapterMapper.mapToDomain(locationDto);

        assertThat(location).isNotNull();
        assertThat(location).isNotNull();
        assertThat(location).isNotNull();
        assertThat(location).isNotNull();
        assertThat(location).isNotNull();
        assertThat(location).isNotNull();
    }

}
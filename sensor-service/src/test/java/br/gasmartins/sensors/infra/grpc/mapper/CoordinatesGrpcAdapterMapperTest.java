package br.gasmartins.sensors.infra.grpc.mapper;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.sensors.domain.support.CoordinatesSupport.defaultCoordinates;
import static org.assertj.core.api.Assertions.assertThat;

class CoordinatesGrpcAdapterMapperTest {

    @Test
    @DisplayName("Given Coordinates When Map Then Return CoordinatesDto")
    public void givenCoordinatesWhenMapThenReturnCoordinatesDto() {
        var coordinates = defaultCoordinates().build();
        var coordinatesDto = CoordinatesGrpcAdapterMapper.mapToDto(coordinates);

        assertThat(coordinatesDto).isNotNull();
        assertThat(coordinatesDto.getLatitude()).isEqualTo(coordinates.getLatitude());
        assertThat(coordinatesDto.getLongitude()).isEqualTo(coordinates.getLongitude());
    }

}
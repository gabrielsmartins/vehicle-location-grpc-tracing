package br.gasmartins.locations.infra.rest.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class LocationDataDto {

    @JsonProperty("data")
    private List<LocationDto> data;

}

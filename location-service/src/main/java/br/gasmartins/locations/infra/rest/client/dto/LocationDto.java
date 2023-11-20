package br.gasmartins.locations.infra.rest.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class LocationDto {

    @JsonProperty("latitude")
    private Float latitude;

    @JsonProperty("longitude")
    private Float longitude;

    @JsonProperty("type")
    private String type;

    @JsonProperty("distance")
    private Double distance;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number")
    private String number;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("street")
    private String street;

    @JsonProperty("confidence")
    private Double confidence;

    @JsonProperty("region")
    private String region;

    @JsonProperty("region_code")
    private String regionCode;

    @JsonProperty("county")
    private String county;

    @JsonProperty("locality")
    private String locality;

    @JsonProperty("administrative_area")
    private String administrativeArea;

    @JsonProperty("neighbourhood")
    private String neighbourhood;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("continent")
    private String continent;

    @JsonProperty("label")
    private String label;

}

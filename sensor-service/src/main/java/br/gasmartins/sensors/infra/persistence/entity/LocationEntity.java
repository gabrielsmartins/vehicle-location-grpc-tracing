package br.gasmartins.sensors.infra.persistence.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class LocationEntity {

    private String country;
    private String state;
    private String city;
    private String district;
    private String address;
    private String zipCode;

}

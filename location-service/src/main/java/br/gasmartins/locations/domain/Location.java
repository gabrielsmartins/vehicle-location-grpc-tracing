package br.gasmartins.locations.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class Location {

    private String country;
    private String state;
    private String city;
    private String district;
    private String address;
    private String zipCode;

}

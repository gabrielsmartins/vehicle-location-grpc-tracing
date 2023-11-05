package br.gasmartins.sensors.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class Location {

    private Float latitude;
    private Float longitude;


}

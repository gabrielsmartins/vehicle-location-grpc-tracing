package br.gasmartins.sensors.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class Coordinates {

    private Float latitude;
    private Float longitude;


}

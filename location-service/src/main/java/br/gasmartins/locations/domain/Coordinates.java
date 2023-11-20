package br.gasmartins.locations.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class Coordinates {

    private Double latitude;
    private Double longitude;

}

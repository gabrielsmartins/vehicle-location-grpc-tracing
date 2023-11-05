package br.gasmartins.sensors.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class SensorData {

    private UUID id;
    private UUID vehicleId;
    private Coordinates coordinates;
    private Float speed;
    private LocalDateTime occurredOn;


}

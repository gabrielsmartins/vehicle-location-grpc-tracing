package br.gasmartins.sensors.domain;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;

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
    private Location location;
    private Float speed;
    private LocalDateTime occurredOn;


}

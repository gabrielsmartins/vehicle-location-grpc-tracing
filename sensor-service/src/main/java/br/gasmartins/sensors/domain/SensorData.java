package br.gasmartins.sensors.domain;

import br.gasmartins.sensors.domain.enums.VehicleState;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class SensorData {

    private UUID sensorId;
    private UUID vehicleId;
    private VehicleState vehicleState;
    private Coordinates coordinates;
    private Float speed;
    private LocalDateTime occurredOn;

    @Getter(AccessLevel.NONE)
    private Location location;

    public Optional<Location> getLocation() {
        return Optional.ofNullable(this.location);
    }
}

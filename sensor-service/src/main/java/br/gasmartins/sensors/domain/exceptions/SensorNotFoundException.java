package br.gasmartins.sensors.domain.exceptions;

import lombok.Getter;

import java.io.Serial;
import java.util.UUID;

@Getter
public class SensorNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final UUID sensorId;

    public SensorNotFoundException(UUID sensorId) {
        super(String.format("Sensor %s not found", sensorId));
        this.sensorId = sensorId;
    }

}

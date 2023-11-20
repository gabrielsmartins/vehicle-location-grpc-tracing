package br.gasmartins.sensors.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum VehicleState {

    MOVING("MOVING"),
    STOPPING("STOPPING"),
    STOPPED("STOPPED");

    @JsonValue
    private final String description;

    public static VehicleState fromDescription(String description) {
        return Stream.of(VehicleState.values())
                     .filter(vehicleState -> vehicleState.getDescription().equals(description))
                     .findFirst()
                     .orElse(null);
    }

}

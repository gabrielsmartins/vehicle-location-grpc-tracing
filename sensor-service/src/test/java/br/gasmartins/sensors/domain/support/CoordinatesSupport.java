package br.gasmartins.sensors.domain.support;

import br.gasmartins.sensors.domain.Coordinates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.instancio.Instancio;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinatesSupport {

    public static Coordinates.CoordinatesBuilder defaultCoordinates() {
        return Instancio.create(Coordinates.CoordinatesBuilder.class);
    }

}

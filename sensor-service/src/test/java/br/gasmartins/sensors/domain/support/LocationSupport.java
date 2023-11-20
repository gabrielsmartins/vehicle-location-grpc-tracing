package br.gasmartins.sensors.domain.support;

import br.gasmartins.sensors.domain.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.instancio.Instancio;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationSupport {

    public static Location.LocationBuilder defaultLocation() {
        return Instancio.create(Location.LocationBuilder.class);
    }

}

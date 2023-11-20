package br.gasmartins.sensors.application.query;

import br.gasmartins.sensors.domain.Coordinates;
import br.gasmartins.sensors.domain.Location;

public interface LocationQuery {

    Location findByCoordinates(Coordinates coordinates);

}

package br.gasmartins.locations.application.query;

import br.gasmartins.locations.domain.Coordinates;
import br.gasmartins.locations.domain.Location;

public interface LocationQuery {

    Location findByCoordinates(Coordinates coordinates);

}

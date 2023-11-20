package br.gasmartins.locations.application.service;

import br.gasmartins.locations.domain.Location;
import br.gasmartins.locations.domain.Coordinates;

public interface LocationService {


    Location findByCoordinates(Coordinates coordinates);


}

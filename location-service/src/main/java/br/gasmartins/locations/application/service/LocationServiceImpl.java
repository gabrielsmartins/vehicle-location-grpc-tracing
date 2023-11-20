package br.gasmartins.locations.application.service;

import br.gasmartins.locations.domain.Coordinates;
import br.gasmartins.locations.domain.Location;
import br.gasmartins.locations.application.query.LocationQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationQuery query;

    @Override
    public Location findByCoordinates(Coordinates coordinates) {
        return this.query.findByCoordinates(coordinates);
    }

}

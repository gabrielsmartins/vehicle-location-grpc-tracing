package br.gasmartins.locations.infra.rest;

import br.gasmartins.locations.domain.Coordinates;
import br.gasmartins.locations.domain.Location;
import br.gasmartins.locations.application.query.LocationQuery;
import br.gasmartins.locations.infra.rest.client.LocationFeignClient;
import br.gasmartins.locations.infra.rest.client.LocationFeignClientProperties;
import br.gasmartins.locations.infra.rest.mapper.LocationWebAdapterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static net.logstash.logback.marker.Markers.append;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(LocationFeignClientProperties.class)
public class LocationWebAdapter implements LocationQuery {

    private final LocationFeignClientProperties properties;
    private final LocationFeignClient client;

    @Override
    public Location findByCoordinates(Coordinates coordinates) {
        log.info(append("coordinates", coordinates), "Searching location by coordinates");
        var apiKey = this.properties.getApiKey();
        var coordinatesQuery = new ArrayList<String>();
        coordinatesQuery.add(String.valueOf(coordinates.getLatitude()));
        coordinatesQuery.add(String.valueOf(coordinates.getLongitude()));
        var response = this.client.findByCoordinates(apiKey, coordinatesQuery, 1L);
        log.info(append("response", response), "Location was found successfully");

        var locationDto = response.getBody().getData().get(0);
        log.info(append("location", locationDto), "Mapping location");
        var location = LocationWebAdapterMapper.mapToDto(locationDto);
        log.info(append("location", location), "Location was mapped successfully");
        return location;
    }

}

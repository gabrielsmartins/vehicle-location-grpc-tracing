package br.gasmartins.sensors.infra.grpc;

import br.gasmartins.grpc.locations.LocationServiceGrpc;
import br.gasmartins.sensors.application.query.LocationQuery;
import br.gasmartins.sensors.domain.Coordinates;
import br.gasmartins.sensors.domain.Location;
import br.gasmartins.sensors.infra.grpc.mapper.CoordinatesGrpcAdapterMapper;
import br.gasmartins.sensors.infra.grpc.mapper.LocationGrpcAdapterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static net.logstash.logback.marker.Markers.append;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationGrpcAdapter implements LocationQuery {

    private final LocationServiceGrpc.LocationServiceBlockingStub stub;

    @Override
    public Location findByCoordinates(Coordinates coordinates) {
        log.info(append("coordinates", coordinates), "Mapping coordinates");
        var coordinatesDto = CoordinatesGrpcAdapterMapper.mapToDto(coordinates);
        log.info(append("coordinates", coordinatesDto), "Coordinates were mapped successfully");

        log.info(append("coordinates", coordinatesDto), "Searching location by coordinates");
        var locationDto = this.stub.getLocationByCoordinates(coordinatesDto);
        log.info(append("location", locationDto), "Location was found successfully");

        log.info(append("location", locationDto), "Mapping location");
        var location = LocationGrpcAdapterMapper.mapToDomain(locationDto);
        log.info(append("location", location), "Location was mapped successfully");
        return location;
    }

}

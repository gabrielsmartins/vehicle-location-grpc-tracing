package br.gasmartins.sensors.infra.grpc;

import br.gasmartins.grpc.locations.LocationServiceGrpc;
import br.gasmartins.sensors.application.query.LocationQuery;
import br.gasmartins.sensors.domain.Coordinates;
import br.gasmartins.sensors.domain.Location;
import br.gasmartins.sensors.infra.grpc.mapper.CoordinatesGrpcAdapterMapper;
import br.gasmartins.sensors.infra.grpc.mapper.LocationGrpcAdapterMapper;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class LocationGrpcAdapter implements LocationQuery {

    private LocationServiceGrpc.LocationServiceBlockingStub stub;

    public LocationGrpcAdapter(@GrpcClient("location-client") LocationServiceGrpc.LocationServiceBlockingStub stub) { // @GrpcClient is duplicated because of unit tests
        this.stub = stub;
    }

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

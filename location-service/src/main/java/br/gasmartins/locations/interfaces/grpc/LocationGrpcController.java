package br.gasmartins.locations.interfaces.grpc;

import br.gasmartins.grpc.locations.Coordinates;
import br.gasmartins.grpc.locations.Location;
import br.gasmartins.grpc.locations.LocationServiceGrpc;
import br.gasmartins.locations.interfaces.grpc.mapper.CoordinatesControllerMapper;
import br.gasmartins.locations.application.service.LocationService;
import br.gasmartins.locations.interfaces.grpc.mapper.LocationControllerMapper;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import static net.logstash.logback.marker.Markers.append;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class LocationGrpcController extends LocationServiceGrpc.LocationServiceImplBase {

    private final LocationService service;

    @Timed(value = "location-by-coordinates.time", description = "Time taken to get location by coordinates")
    @Counted(value = "location-by-coordinates.count", description = "Number of requests to get location by coordinates")
    @Override
    public void getLocationByCoordinates(Coordinates coordinatesDto, StreamObserver<Location> responseObserver) {
        log.info(append("request", coordinatesDto), "Receiving requests");

        log.info(append("coordinates", coordinatesDto), "Mapping coordinates");
        var coordinates = CoordinatesControllerMapper.mapToDomain(coordinatesDto);
        log.info(append("coordinates", coordinates), "Coordinates were mapped successfully");

        log.info(append("coordinates", coordinates), "Searching location by coordinates");
        var location = this.service.findByCoordinates(coordinates);
        log.info(append("location", location), "Location was found successfully");

        log.info(append("location", location), "Mapping location");
        var locationDto = LocationControllerMapper.mapToDto(location);
        log.info(append("location", locationDto), "Location was mapped successfully");

        responseObserver.onNext(locationDto);
        responseObserver.onCompleted();
    }

}

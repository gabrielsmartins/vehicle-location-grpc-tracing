package br.gasmartins.sensors.infra.grpc.support;

import br.gasmartins.grpc.locations.Coordinates;
import br.gasmartins.grpc.locations.Location;
import br.gasmartins.grpc.locations.LocationServiceGrpc;
import io.grpc.stub.StreamObserver;

import static br.gasmartins.sensors.infra.grpc.support.LocationDtoSupport.defaultLocationDto;

public class LocationGrpcServiceMock extends LocationServiceGrpc.LocationServiceImplBase {

    @Override
    public void getLocationByCoordinates(Coordinates request, StreamObserver<Location> responseObserver) {
        var locationDto = defaultLocationDto().build();
        responseObserver.onNext(locationDto);
        responseObserver.onCompleted();
    }

}

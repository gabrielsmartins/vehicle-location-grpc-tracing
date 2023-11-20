package br.gasmartins.sensors.infra.grpc.support;

import br.gasmartins.grpc.locations.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDtoSupport {

    public static Location.Builder defaultLocationDto() {
        return Location.newBuilder()
                       .setCountry("USA")
                       .setState("Foo")
                       .setCity("Bar")
                       .setDistrict("District")
                       .setAddress("3rd Street")
                       .setZipCode("0646454");
    }

}

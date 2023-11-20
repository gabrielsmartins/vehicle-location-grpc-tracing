package br.gasmartins.sensors.interfaces.grpc.support;


import br.gasmartins.grpc.common.Page;
import br.gasmartins.grpc.common.Pageable;
import br.gasmartins.grpc.sensors.*;
import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorDataDtoSupport {

    public static SensorData.Builder defaultSensorDataDto() {
        return SensorData.newBuilder()
                        .setSensorId(UUID.randomUUID().toString())
                        .setVehicleId(UUID.randomUUID().toString())
                        .setSpeed(50)
                        .setVehicleStateValue(VehicleState.MOVING_VALUE)
                        .setOccurredOn(Timestamp.getDefaultInstance())
                        .setCoordinates(Coordinates.newBuilder()
                                .setLongitude(10)
                                .setLatitude(20)
                                .build())
                        .setLocation(Location.newBuilder()
                                .setCountry("USA")
                                .setState("Foo")
                                .setCity("Bar")
                                .setDistrict("District")
                                .setAddress("3rd Street")
                                .setZipCode("0646454")
                                .build());
    }

    public static SearchSensorDataByVehicleIdParam.Builder defaultSearchSensorDataByVehicleIdParamDto() {
        return SearchSensorDataByVehicleIdParam.newBuilder()
                                               .setVehicleId(StringValue.newBuilder().setValue(UUID.randomUUID().toString()))
                                               .setStartOccurredOn(Timestamp.getDefaultInstance())
                                               .setEndOccurredOn(Timestamp.getDefaultInstance())
                                               .setPageable(defaultPageable().build());
    }

    private static Pageable.Builder defaultPageable() {
        return Pageable.newBuilder()
                .setPage(1)
                .setPageSize(10);
    }

    public static SensorDataPage.Builder defaultSensorDataPage() {
        return SensorDataPage.newBuilder()
                .addAllData(List.of(defaultSensorDataDto().build()))
                .setPage(defaultPage());
    }

    public static Page defaultPage() {
        return Page.newBuilder()
                    .setPage(1)
                    .setPageSize(1)
                    .setTotalPages(1)
                    .setTotalElements(2)
                    .setFirst(true)
                    .setLast(false)
                    .setHasNext(false)
                    .setHasPrevious(false)
                    .build();
    }

}

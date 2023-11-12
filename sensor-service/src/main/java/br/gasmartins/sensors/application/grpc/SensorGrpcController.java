package br.gasmartins.sensors.application.grpc;


import br.gasmartins.grpc.sensors.SearchSensorDataByVehicleIdParam;
import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.grpc.sensors.SensorDataPage;
import br.gasmartins.grpc.sensors.SensorServiceGrpc;
import br.gasmartins.sensors.application.grpc.mapper.SensorGrpcMapper;
import br.gasmartins.sensors.application.grpc.observer.in.SearchSensorDataByVehicleIdParamStreamObserver;
import br.gasmartins.sensors.application.grpc.observer.out.SensorDataStreamObserver;
import br.gasmartins.sensors.domain.service.SensorService;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class SensorGrpcController extends SensorServiceGrpc.SensorServiceImplBase {

    private final SensorService service;

    @Timed(value = "sensor-data-store.time", description = "Time taken to store sensor data")
    @Counted(value = "sensor-data-store.count", description = "Number of requests to store sensor data")
    @Override
    public StreamObserver<SensorData> store(StreamObserver<SensorData> responseObserver) {
        log.info(append("data", responseObserver), "Storing sensor data");
        return new SensorDataStreamObserver(this.service, responseObserver);
    }

    @Timed(value = "sensor-data-by-sensor-id.time", description = "Time taken to return sensor data by sensor id")
    @Counted(value = "sensor-data-by-sensor-id.count", description = "Number of requests to search sensor data by sensor id")
    @Override
    public void findBySensorId(StringValue sensorId, StreamObserver<SensorData> responseObserver) {
        log.info(append("sensor_id", sensorId), "Searching sensor by id");
        var sensorData = this.service.findById(UUID.fromString(sensorId.getValue()));
        log.info(append("data", sensorData), "Sensor data was found successfully");

        log.info(append("sensor", sensorData), "Mapping sensor data");
        var sensorDataDto = SensorGrpcMapper.mapToDto(sensorData);
        log.info(append("sensor", sensorDataDto), "Sensor was mapped successfully");

        responseObserver.onNext(sensorDataDto);
        responseObserver.onCompleted();
    }

    @Timed(value = "sensor-data-by-vehicle-id.time", description = "Time taken to return sensor data by vehicle id")
    @Counted(value = "sensor-data-by-vehicle-id.count", description = "Number of requests to search sensor data by vehicle id")
    @Override
    public StreamObserver<SearchSensorDataByVehicleIdParam> findByVehicleIdAndOccurredOnBetween(StreamObserver<SensorDataPage> responseObserver) {
        return new SearchSensorDataByVehicleIdParamStreamObserver(this.service, responseObserver);
    }

}

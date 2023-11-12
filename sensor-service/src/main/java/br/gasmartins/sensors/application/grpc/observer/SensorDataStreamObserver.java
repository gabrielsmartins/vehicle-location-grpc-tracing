package br.gasmartins.sensors.application.grpc.observer;

import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.sensors.application.grpc.mapper.SensorGrpcMapper;
import br.gasmartins.sensors.domain.service.SensorService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.marker.Markers.append;

;

@RequiredArgsConstructor
@Slf4j
public class SensorDataStreamObserver implements StreamObserver<SensorData> {

    private final SensorService service;
    private br.gasmartins.sensors.domain.SensorData storedSensorData;
    private final StreamObserver<SensorData> responseObserver;

    @Override
    public void onNext(SensorData sensorDataDto) {
        log.info(append("sensor", sensorDataDto), "Mapping sensor");
        var sensorData = SensorGrpcMapper.mapToDomain(sensorDataDto);
        log.info(append("sensor", sensorData), "Sensor was mapped successfully");

        log.info(append("sensor", sensorData), "Storing sensor data");
        this.storedSensorData = this.service.store(sensorData);
        log.info(append("sensor", sensorData), "Sensor data was stored successfully");
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error processing request", t);
        this.responseObserver.onCompleted();
    }

    @Override
    public void onCompleted() {
        log.info(append("sensor", this.storedSensorData), "Mapping stored sensor");
        var storedSensorDataDto = SensorGrpcMapper.mapToDto(this.storedSensorData);
        log.info(append("sensor", storedSensorDataDto), "Stored sensor was mapped successfully");
        this.responseObserver.onNext(storedSensorDataDto);
        this.responseObserver.onCompleted();
    }

}
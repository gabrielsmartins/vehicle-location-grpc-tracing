package br.gasmartins.sensors.application.grpc.support;

import br.gasmartins.grpc.sensors.SensorData;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.marker.Markers.append;

@Slf4j
public class SensorDataOutputStreamObserver implements StreamObserver<SensorData> {

    @Override
    public void onNext(SensorData data) {
        log.info(append("data", data), "Receiving data");
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error receiving data", t);
    }

    @Override
    public void onCompleted() {
        log.info("All data received");
    }

}

package br.gasmartins.sensors.application.grpc;

import br.gasmartins.grpc.sensors.Sensor;
import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.grpc.sensors.SensorServiceGrpc;
import br.gasmartins.sensors.domain.service.SensorService;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import static net.logstash.logback.marker.Markers.append;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class SensorGrpcController extends SensorServiceGrpc.SensorServiceImplBase {

    private final SensorService service;

    @Observed(name = "store", contextualName = "sensors")
    @Timed(value = "sensor-create.time", description = "Time taken to store sensor data")
    @Counted(value = "sensor-create.time", description = "Number of requests to store sensor data")
    @Override
    public StreamObserver<SensorData> store(StreamObserver<Sensor> responseObserver) {
        log.info(append("data", responseObserver), "Storing sensor data");
        return new SensorDataStreamObserver(this.service, responseObserver);
    }

    @Observed(name = "findById", contextualName = "sensors")
    @Timed(value = "sensor-search.time", description = "Time taken to return sensor data")
    @Counted(value = "sensor-search.time", description = "Number of requests return store sensor data")
    @Override
    public void findById(StringValue id, StreamObserver<Sensor> responseObserver) {
        log.info(append("id", id), "Storing sensor data");
        super.findById(id, responseObserver);
    }
}

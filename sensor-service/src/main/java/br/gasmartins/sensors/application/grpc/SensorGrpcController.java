package br.gasmartins.sensors.application.grpc;

import br.gasmartins.grpc.sensors.Sensor;
import br.gasmartins.grpc.sensors.SensorData;
import br.gasmartins.grpc.sensors.SensorServiceGrpc;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import static net.logstash.logback.marker.Markers.append;

@GrpcService
@Slf4j
public class SensorGrpcController extends SensorServiceGrpc.SensorServiceImplBase {

    @Observed(name = "store", contextualName = "sensors")
    @Override
    public StreamObserver<SensorData> store(StreamObserver<Sensor> responseObserver) {
        log.info(append("data", responseObserver), "Storing sensor data");
        return super.store(responseObserver);
    }

    @Observed(name = "findById", contextualName = "sensors")
    @Override
    public void findById(StringValue id, StreamObserver<Sensor> responseObserver) {
        log.info(append("id", id), "Storing sensor data");
        super.findById(id, responseObserver);
    }
}

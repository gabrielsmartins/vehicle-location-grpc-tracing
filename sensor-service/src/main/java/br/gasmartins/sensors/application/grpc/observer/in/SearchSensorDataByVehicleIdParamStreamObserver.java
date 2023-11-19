package br.gasmartins.sensors.application.grpc.observer.in;

import br.gasmartins.grpc.sensors.SearchSensorDataByVehicleIdParam;
import br.gasmartins.grpc.sensors.SensorDataPage;
import br.gasmartins.sensors.application.grpc.mapper.SensorGrpcMapper;
import br.gasmartins.sensors.domain.service.SensorService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static br.gasmartins.sensors.infra.persistence.adapter.mapper.TimestampGrpcMapper.toLocalDatetime;
import static net.logstash.logback.marker.Markers.append;

@RequiredArgsConstructor
@Slf4j
public class SearchSensorDataByVehicleIdParamStreamObserver implements StreamObserver<SearchSensorDataByVehicleIdParam> {

    private final SensorService service;
    private final StreamObserver<SensorDataPage> responseObserver;

    @Override
    public void onNext(SearchSensorDataByVehicleIdParam request) {
        log.info(append("request", request), "Searching sensor data");
        var vehicleId = UUID.fromString(request.getVehicleId().getValue());
        var startOccurredOn = toLocalDatetime(request.getStartOccurredOn());
        var endOccurredOn = toLocalDatetime(request.getEndOccurredOn());
        var pageable = request.getPageable();
        var pageRequest = PageRequest.of(pageable.getPage(), pageable.getPageSize());
        var page = this.service.findByVehicleIdAndOccurredOnBetween(vehicleId, startOccurredOn, endOccurredOn, pageRequest);
        log.info(append("page", page), "Sensor was found successfully");

        log.info(append("page", page), "Mapping page");
        var pageDto = SensorGrpcMapper.mapToDto(page);
        log.info(append("page", pageDto), "Page was mapped successfully");
        this.responseObserver.onNext(pageDto);
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error searching sensor data", t);
        this.responseObserver.onError(t);
    }

    @Override
    public void onCompleted() {
        log.info("All request data is received");
        this.responseObserver.onCompleted();
    }

}

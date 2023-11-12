package br.gasmartins.sensors.infra.persistence.adapter;

import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.domain.repository.SensorRepository;
import br.gasmartins.sensors.infra.persistence.adapter.mapper.SensorPersistenceMapper;
import br.gasmartins.sensors.infra.persistence.service.SensorPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.marker.Markers.append;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorPersistenceAdapter implements SensorRepository {

    private final SensorPersistenceService service;

    @Override
    public SensorData store(SensorData sensorData) {
        log.info(append("sensor", sensorData), "Mapping sensor");
        var sensorDataEntity = SensorPersistenceMapper.mapToEntity(sensorData);
        log.info(append("sensor", sensorDataEntity), "Sensor was mapped successfully");

        log.info(append("sensor", sensorDataEntity), "Storing sensor data");
        var savedSensorDataEntity = this.service.store(sensorDataEntity);
        log.info(append("sensor", savedSensorDataEntity), "Storing sensor data");

        log.info(append("sensor", savedSensorDataEntity), "Mapping saved sensor");
        var savedSensorData = SensorPersistenceMapper.mapToDomain(savedSensorDataEntity);
        log.info(append("sensor", savedSensorData), "Saved sensor was mapped successfully");
        return savedSensorData;
    }

    @Override
    public Optional<SensorData> findById(UUID id) {
        log.info("Searching sensor by sensor id {}", id);
        return this.service.findById(id)
                           .stream()
                           .peek(sensorDataEntity -> log.info(append("sensor", sensorDataEntity), "Sensor was found successfully"))
                           .map(SensorPersistenceMapper::mapToDomain)
                           .peek(sensorData -> log.info(append("sensor", sensorData), "Sensor was mapping successfully"))
                           .findFirst();
    }

    @Override
    public Page<SensorData> findByVehicleIdAndOccurredOnBetween(UUID vehicleId, LocalDateTime startOccurredOn, LocalDateTime endOccurredOn, Pageable pageable) {
        log.info("Searching sensor by vehicle id {} and start {} and end {} occurred on and pageable {}", kv("vehicle_id", vehicleId), kv("start_occurred_on", startOccurredOn), kv("end_occurred_on", endOccurredOn), kv("pageable", pageable));
        var pageEntity = this.service.findByVehicleIdAndOccurredOnBetween(vehicleId, startOccurredOn, endOccurredOn, pageable);
        log.info(append("page", pageEntity), "Page was found successfully");

        log.info(append("page", pageEntity), "Mapping sensor page");
        var page = SensorPersistenceMapper.mapToDomain(pageEntity);
        log.info(append("page", page), "Sensor page was mapped successfully");
        return page;
    }
}

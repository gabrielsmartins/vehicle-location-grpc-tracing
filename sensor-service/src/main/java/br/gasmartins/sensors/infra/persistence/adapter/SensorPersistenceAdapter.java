package br.gasmartins.sensors.infra.persistence.adapter;

import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.domain.repository.SensorRepository;
import br.gasmartins.sensors.infra.persistence.adapter.mapper.SensorPersistenceMapper;
import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import br.gasmartins.sensors.infra.persistence.service.SensorPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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
        log.info("Searching sensor by id {}", id);
        return this.service.findById(id)
                           .stream()
                           .peek(sensorDataEntity -> log.info(append("sensor", sensorDataEntity), "Sensor was found successfully"))
                           .map(SensorPersistenceMapper::mapToDomain)
                           .peek(sensorData -> log.info(append("sensor", sensorData), "Sensor was mapping successfully"))
                           .findFirst();
    }
}

package br.gasmartins.sensors.infra.persistence.service;

import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SensorPersistenceService {

    SensorDataEntity store(SensorDataEntity sensorData);

    Optional<SensorDataEntity> findById(UUID id);

    Page<SensorDataEntity> findByVehicleIdAndOccurredOnBetween(UUID vehicleId, LocalDateTime startOccurredOn, LocalDateTime endOccurredOn, Pageable pageable);

}

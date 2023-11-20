package br.gasmartins.sensors.application.repository;

import br.gasmartins.sensors.domain.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SensorRepository {

    SensorData store(SensorData sensorData);

    Optional<SensorData> findById(UUID id);

    Page<SensorData> findByVehicleIdAndOccurredOnBetween(UUID vehicleId, LocalDateTime startOccurredOn, LocalDateTime endOccurredOn, Pageable pageable);

}

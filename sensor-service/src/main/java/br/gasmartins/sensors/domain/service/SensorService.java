package br.gasmartins.sensors.domain.service;

import br.gasmartins.sensors.domain.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SensorService {

    SensorData store(SensorData sensorData);

    SensorData findById(UUID id);

    Page<SensorData> findByVehicleIdAndOccurredOnBetween(UUID vehicleId, LocalDateTime startOccurredOn, LocalDateTime endOccurredOn, Pageable pageable);

}

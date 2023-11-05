package br.gasmartins.sensors.domain.repository;

import br.gasmartins.sensors.domain.SensorData;

import java.util.Optional;
import java.util.UUID;

public interface SensorRepository {

    SensorData store(SensorData sensorData);

    Optional<SensorData> findById(UUID id);

}

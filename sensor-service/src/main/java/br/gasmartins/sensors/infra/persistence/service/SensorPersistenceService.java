package br.gasmartins.sensors.infra.persistence.service;

import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;

import java.util.Optional;
import java.util.UUID;

public interface SensorPersistenceService {

    SensorDataEntity store(SensorDataEntity sensorData);

    Optional<SensorDataEntity> findById(UUID id);

}

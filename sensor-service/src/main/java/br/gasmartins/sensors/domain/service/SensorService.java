package br.gasmartins.sensors.domain.service;

import br.gasmartins.sensors.domain.SensorData;

import java.util.UUID;

public interface SensorService {

    SensorData store(SensorData sensorData);

    SensorData findById(UUID id);

}

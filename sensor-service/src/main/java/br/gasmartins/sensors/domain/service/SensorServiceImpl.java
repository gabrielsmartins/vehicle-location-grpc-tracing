package br.gasmartins.sensors.domain.service;

import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.domain.exceptions.SensorNotFoundException;
import br.gasmartins.sensors.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository repository;

    @Override
    public SensorData store(SensorData sensorData) {
        return this.repository.store(sensorData);
    }

    @Override
    public SensorData findById(UUID id) {
        return this.repository.findById(id)
                              .orElseThrow(() -> new SensorNotFoundException(id));
    }

    @Override
    public SensorData findByVehicleId(UUID vehicleId) {
        return this.repository.findById(id)
                              .orElseThrow(() -> new SensorNotFoundException(id));
    }

}

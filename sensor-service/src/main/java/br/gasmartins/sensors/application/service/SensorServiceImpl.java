package br.gasmartins.sensors.application.service;

import br.gasmartins.sensors.application.query.LocationQuery;
import br.gasmartins.sensors.application.repository.SensorRepository;
import br.gasmartins.sensors.domain.SensorData;
import br.gasmartins.sensors.domain.exceptions.SensorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final LocationQuery query;
    private final SensorRepository repository;

    @Override
    public SensorData store(SensorData sensorData) {
        var location = this.query.findByCoordinates(sensorData.getCoordinates());
        sensorData.setLocation(location);
        return this.repository.store(sensorData);
    }

    @Override
    public SensorData findById(UUID id) {
        return this.repository.findById(id)
                              .orElseThrow(() -> new SensorNotFoundException(id));
    }

    @Override
    public Page<SensorData> findByVehicleIdAndOccurredOnBetween(UUID vehicleId, LocalDateTime startOccurredOn, LocalDateTime endOccurredOn, Pageable pageable) {
        return this.repository.findByVehicleIdAndOccurredOnBetween(vehicleId, startOccurredOn, endOccurredOn, pageable);
    }

}

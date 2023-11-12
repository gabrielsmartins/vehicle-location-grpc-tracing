package br.gasmartins.sensors.infra.persistence.service;

import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import br.gasmartins.sensors.infra.persistence.repository.SensorElasticSearchRepository;
import io.micrometer.tracing.annotation.NewSpan;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SensorPersistenceServiceImpl implements SensorPersistenceService {

    private final SensorElasticSearchRepository repository;

    @Override
    @NewSpan("Store Sensor Data")
    public SensorDataEntity store(SensorDataEntity sensorData) {
        return this.repository.save(sensorData);
    }

    @Override
    @NewSpan("Search By Sensor Id")
    public Optional<SensorDataEntity> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Override
    @NewSpan("Search By Sensor Id")
    public Page<SensorDataEntity> findByVehicleId(UUID vehicleId, Pageable pageable) {
        return this.repository.findByVehicleId(vehicleId, pageable);
    }

}

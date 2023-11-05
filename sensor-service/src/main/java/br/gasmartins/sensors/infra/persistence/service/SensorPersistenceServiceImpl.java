package br.gasmartins.sensors.infra.persistence.service;

import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import br.gasmartins.sensors.infra.persistence.repository.SensorElasticSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SensorPersistenceServiceImpl implements SensorPersistenceService {

    private final SensorElasticSearchRepository repository;


    @Override
    public SensorDataEntity store(SensorDataEntity sensorData) {
        return this.repository.save(sensorData);
    }

    @Override
    public Optional<SensorDataEntity> findById(UUID id) {
        return Optional.empty();
    }

}

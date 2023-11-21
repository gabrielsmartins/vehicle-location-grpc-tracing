package br.gasmartins.sensors.infra.persistence.service;

import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import br.gasmartins.sensors.infra.persistence.repository.SensorDataElasticSearchRepository;
import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.SpanTag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SensorPersistenceServiceImpl implements SensorPersistenceService {

    private final SensorDataElasticSearchRepository repository;

    @Override
    @NewSpan("Store Sensor Data")
    public SensorDataEntity store(SensorDataEntity sensorData) {
        return this.repository.save(sensorData);
    }

    @Override
    @NewSpan("Search By Sensor Id")
    public Optional<SensorDataEntity> findById(@SpanTag("id") UUID id) {
        return this.repository.findById(id);
    }

    @Override
    @NewSpan("Search By Vehicle Id")
    public Page<SensorDataEntity> findByVehicleIdAndOccurredOnBetween(@SpanTag("vehicle_id") UUID vehicleId, @SpanTag("start_occurred_on") LocalDateTime startOccurredOn, @SpanTag("end_occurred_on") LocalDateTime endOccurredOn, Pageable pageable) {
        return this.repository.findByVehicleIdAndOccurredOnBetween(vehicleId, startOccurredOn, endOccurredOn, pageable);
    }

}

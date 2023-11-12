package br.gasmartins.sensors.infra.persistence.repository;

import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SensorDataElasticSearchRepository extends ElasticsearchRepository<SensorDataEntity, UUID> {

    Page<SensorDataEntity> findByVehicleIdAndOccurredOnBetween(UUID vehicleId, LocalDateTime startOccurredOn, LocalDateTime endOccurredOn, Pageable pageable);

}

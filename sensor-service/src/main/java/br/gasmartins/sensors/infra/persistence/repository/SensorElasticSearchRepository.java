package br.gasmartins.sensors.infra.persistence.repository;

import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorElasticSearchRepository extends ElasticsearchRepository<SensorDataEntity, UUID> {

}

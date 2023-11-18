package br.gasmartins.sensors.infra.persistence.repository;

import br.gasmartins.sensors.infra.persistence.config.ElasticsearchConfiguration;
import br.gasmartins.sensors.infra.persistence.config.ObjectMapperConfiguration;
import br.gasmartins.sensors.infra.persistence.support.ElasticsearchContainerSupport;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static br.gasmartins.sensors.infra.persistence.support.SensorDataEntitySupport.defaultSensorDataEntity;
import static org.assertj.core.api.Assertions.assertThat;

@DataElasticsearchTest
@Import({ElasticsearchConfiguration.class, ObjectMapperConfiguration.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ActiveProfiles("test")
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class SensorDataElasticSearchRepositoryTest extends ElasticsearchContainerSupport {

    private final SensorDataElasticSearchRepository repository;

    @Test
    @DisplayName("Given Sensor Data When Store Then Return Stored Sensor Data")
    public void givenSensorDataWhenStoreThenReturnStoredSensorData() {
        var sensorDataEntity = defaultSensorDataEntity().build();
        var storedSensorDataEntity = this.repository.save(sensorDataEntity);
        assertThat(storedSensorDataEntity).isNotNull();
    }

    @Test
    @DisplayName("Given Sensor Id When Exists Then Return Sensor Data")
    public void givenSensorIdWhenExistsThenReturnSensorData() {
        var sensorDataEntity = defaultSensorDataEntity().build();
         this.repository.save(sensorDataEntity);
        var id = sensorDataEntity.getSensorId();
        var optionalSensorDataEntity = this.repository.findById(id);
        assertThat(optionalSensorDataEntity).isPresent();
    }

    @Test
    @DisplayName("Given Vehicle Id And Interval When Exists Then Return Sensor Data Page")
    public void givenVehicleIdAndIntervalWhenExistsThenReturnSensorDataPage() {
        var sensorDataEntity = defaultSensorDataEntity().build();
        this.repository.save(sensorDataEntity);
        var id = sensorDataEntity.getSensorId();
        var startOccurredOn = sensorDataEntity.getOccurredOn().minusDays(1);
        var endOccurredOn = startOccurredOn.plusDays(2);

        var pageable = PageRequest.of(0,30);
        var page = this.repository.findByVehicleIdAndOccurredOnBetween(id, startOccurredOn, endOccurredOn, pageable);
        assertThat(page).isNotNull();
    }

}
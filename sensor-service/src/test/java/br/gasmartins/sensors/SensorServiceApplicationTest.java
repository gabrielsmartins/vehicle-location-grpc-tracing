package br.gasmartins.sensors;

import br.gasmartins.sensors.infra.persistence.support.ElasticsearchContainerSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
class SensorServiceApplicationTest extends ElasticsearchContainerSupport {

	@Test
	void contextLoads() {

	}

}

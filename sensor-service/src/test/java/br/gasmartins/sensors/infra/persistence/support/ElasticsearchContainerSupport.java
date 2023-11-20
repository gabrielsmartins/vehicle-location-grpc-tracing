package br.gasmartins.sensors.infra.persistence.support;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class ElasticsearchContainerSupport {

	private static final ElasticsearchContainer ELASTICSEARCH_CONTAINER;

	static {
		ELASTICSEARCH_CONTAINER = new ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.17.10"))
															.withExposedPorts(9200)
															.withEnv("DISABLE_SECURITY_PLUGIN", "true")
															.withEnv("DISABLE_INSTALL_DEMO_CONFIG", "true")
															.withEnv("discovery.type", "single-node");
		ELASTICSEARCH_CONTAINER.start();
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.elasticsearch.uris", ELASTICSEARCH_CONTAINER::getHttpHostAddress);
	}

}

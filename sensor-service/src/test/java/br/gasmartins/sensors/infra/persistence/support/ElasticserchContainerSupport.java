package br.gasmartins.sensors.infra.persistence.support;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@ContextConfiguration(initializers = ElasticserchContainerSupport.DataSourceInitializer.class)
public class ElasticserchContainerSupport {

	private static final ElasticsearchContainer ELASTICSEARCH_CONTAINER;

	static {
		ELASTICSEARCH_CONTAINER = new ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.17.10"));
		ELASTICSEARCH_CONTAINER.start();
	}

	public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					applicationContext,
					"spring.elasticsearch.uris=" + ELASTICSEARCH_CONTAINER.getHttpHostAddress()
			);
		}
	}

}

package br.gasmartins.sensors.infra.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "br.gasmartins.sensors.infra.persistence.repository")
public class ElasticsearchConfiguration {

}

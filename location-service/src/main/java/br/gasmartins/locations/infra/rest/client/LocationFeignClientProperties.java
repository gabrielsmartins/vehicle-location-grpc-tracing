package br.gasmartins.locations.infra.rest.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "webservice.position-stack")
public class LocationFeignClientProperties {

    private String apiKey;
    private String url;


}

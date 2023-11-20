package br.gasmartins.locations.infra.rest.client;

import br.gasmartins.locations.infra.rest.client.dto.LocationDataDto;
import br.gasmartins.locations.infra.rest.config.FeignConfiguration;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "location-client", url = "${webservice.position-stack.url}", configuration = FeignConfiguration.class)
public interface LocationFeignClient {


    @CollectionFormat(feign.CollectionFormat.CSV)
    @GetMapping("/reverse")
    ResponseEntity<LocationDataDto> findByCoordinates(@RequestParam("access_key") String accessKey, @RequestParam("query") List<String> coordinates, @RequestParam("limit") Long limit);


}

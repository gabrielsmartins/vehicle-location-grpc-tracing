package br.gasmartins.locations.infra.rest.client;

import br.gasmartins.locations.infra.rest.client.dto.LocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "location-client", url = "${webservice.position-stack.url}")
public interface LocationFeignClient {

    @GetMapping("/reverse")
    ResponseEntity<LocationDto> findByCoordinates(@RequestParam("access_key") String accessKey, @RequestParam("query") String query, @RequestParam("limit") Long limit);


}

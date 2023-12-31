package br.gasmartins.sensors.infra.persistence.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class CoordinatesEntity {

    @Field(name = "latitude", type = FieldType.Double)
    private Double latitude;

    @Field(name = "longitude", type = FieldType.Double)
    private Double longitude;


}

package br.gasmartins.sensors.infra.persistence.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class LocationEntity {

    @Field(name = "latitude", type = FieldType.Double)
    private Float latitude;

    @Field(name = "longitude", type = FieldType.Double)
    private Float longitude;


}

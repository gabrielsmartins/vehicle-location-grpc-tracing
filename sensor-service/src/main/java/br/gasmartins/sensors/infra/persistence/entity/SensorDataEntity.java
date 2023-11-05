package br.gasmartins.sensors.infra.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
@TypeAlias("sensor")
@Document(indexName = "sensors")
public class SensorDataEntity {

    @Id
    @Field(name = "device_id", type = FieldType.Text)
    private UUID id;

    @Field(name = "vehicle_id", type = FieldType.Text)
    private UUID vehicleId;

    @Field(name = "location", type = FieldType.Nested)
    private LocationEntity location;

    @Field(name = "speed", type = FieldType.Float)
    private Float speed;

    @Field(name = "occurredOn", type = FieldType.Date_Nanos)
    private LocalDateTime occurredOn;

}

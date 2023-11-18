package br.gasmartins.sensors.infra.persistence.entity;

import br.gasmartins.sensors.domain.enums.VehicleState;
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
    @Field(name = "sensor_id", type = FieldType.Text)
    private UUID sensorId;

    @Field(name = "vehicle_id", type = FieldType.Text)
    private UUID vehicleId;

    @Field(name = "vehicle_state", type = FieldType.Text)
    private VehicleState vehicleState;

    @Field(name = "coordinates", type = FieldType.Nested)
    private CoordinatesEntity coordinates;

    @Field(name = "speed", type = FieldType.Float)
    private Float speed;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime occurredOn;

    @Field(name = "location", type = FieldType.Nested)
    private LocationEntity location;

}

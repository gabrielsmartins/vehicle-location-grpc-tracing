package br.gasmartins.sensors.infra.persistence.support;


import br.gasmartins.sensors.infra.persistence.entity.SensorDataEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.instancio.Instancio;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorDataEntitySupport {

    public static SensorDataEntity.SensorDataEntityBuilder defaultSensorDataEntity() {
        return Instancio.create(SensorDataEntity.SensorDataEntityBuilder.class);
    }

}

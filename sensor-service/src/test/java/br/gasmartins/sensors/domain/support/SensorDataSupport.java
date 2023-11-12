package br.gasmartins.sensors.domain.support;


import br.gasmartins.sensors.domain.SensorData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.instancio.Instancio;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorDataSupport {

    public static SensorData.SensorDataBuilder defaultSensorData() {
        return Instancio.create(SensorData.SensorDataBuilder.class);
    }

}

package br.gasmartins.sensors.application.grpc.support;


import br.gasmartins.grpc.sensors.SensorData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.instancio.Instancio;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorDataDtoSupport {

    public static SensorData.Builder defaultSensorDataDto() {
        return Instancio.create(SensorData.Builder.class);
    }

}

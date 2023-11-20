package br.gasmartins.sensors.infra.grpc.config.client;


import br.gasmartins.grpc.locations.LocationServiceGrpc;
import br.gasmartins.sensors.infra.grpc.LocationGrpcAdapter;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@GrpcClientBean(
        clazz = LocationServiceGrpc.LocationServiceBlockingStub.class,
        beanName = "locationClientBlockingStub",
        client = @GrpcClient("location-client")
)
public class LocationGrpcClientConfiguration {

    @Bean
    public LocationGrpcAdapter locationGrpcAdapter(LocationServiceGrpc.LocationServiceBlockingStub stub) {
       return new LocationGrpcAdapter(stub);
    }

}

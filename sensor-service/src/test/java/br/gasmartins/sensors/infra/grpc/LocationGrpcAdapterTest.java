package br.gasmartins.sensors.infra.grpc;

import br.gasmartins.sensors.infra.grpc.support.LocationGrpcServiceMock;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static br.gasmartins.sensors.domain.support.CoordinatesSupport.defaultCoordinates;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({LocationGrpcAdapter.class})
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class,
        GrpcServerFactoryAutoConfiguration.class,
        GrpcClientAutoConfiguration.class
})
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext
class LocationGrpcAdapterTest {

    private final LocationGrpcAdapter adapter;
    private Server server;

    @BeforeEach
    public void setup() throws IOException, InterruptedException {
        this.server = ServerBuilder.forPort(8085)
                                   .addService(new LocationGrpcServiceMock())
                                   .build();
        server.start();
        var serverThread = new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        serverThread.setDaemon(false);
        serverThread.start();
    }

    @AfterEach
    public void afterEach() {
        this.server.shutdown();
    }

    @Test
    @DisplayName("Given Coordinates When Exists Then Return Location")
    public void givenCoordinatesWhenExistsThenReturnLocation() {
        var coordinates = defaultCoordinates().build();

        var location = this.adapter.findByCoordinates(coordinates);

        assertThat(location).isNotNull();
    }

}
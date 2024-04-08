package br.gasmartins.sensors.infra.grpc.config.proxy;


import io.grpc.internal.GrpcUtil;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcProxyConfiguration {

    @Bean
    public GrpcChannelConfigurer grpcChannelConfigurer() {
        return (channelBuilder, name) -> {
            if (channelBuilder instanceof NettyChannelBuilder nettyChannelBuilder) {
                nettyChannelBuilder.keepAliveTime(30, TimeUnit.SECONDS)
                        .keepAliveTimeout(5, TimeUnit.SECONDS)
                        .proxyDetector(GrpcUtil.DEFAULT_PROXY_DETECTOR);
            }
        };
    }

}

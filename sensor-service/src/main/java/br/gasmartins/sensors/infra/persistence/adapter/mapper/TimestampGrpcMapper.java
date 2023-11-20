package br.gasmartins.sensors.infra.persistence.adapter.mapper;

import com.google.protobuf.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimestampGrpcMapper {

    public static Timestamp toTimestamp(LocalDateTime localDateTime) {
        return Timestamp.newBuilder()
                .setSeconds(localDateTime.toEpochSecond(ZoneOffset.UTC))
                .setNanos(localDateTime.getNano())
                .build();
    }

    public static LocalDateTime toLocalDatetime(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                      .atOffset(ZoneOffset.UTC)
                      .toLocalDateTime();
    }

}

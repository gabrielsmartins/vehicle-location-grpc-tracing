package br.gasmartins.sensors.infra.persistence.adapter.mapper;

import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class TimestampGrpcMapperTest {

    @Test
    @DisplayName("Given Timestamp When Map Then Return LocalDatetime")
    public void givenTimestampWhenMapThenReturnLocalDatetime() {
        var timestamp = Timestamp.newBuilder()
                                 .setNanos(712949000)
                                 .setSeconds(1699796805)
                                  .build();
        var localDatetime = TimestampGrpcMapper.toLocalDatetime(timestamp);

        assertThat(localDatetime).isNotNull();
        assertThat(localDatetime.format(DateTimeFormatter.ISO_DATE_TIME)).isEqualTo("2023-11-12T13:46:45.712949");
    }

    @Test
    @DisplayName("Given LocalDatetime When Map Then Return Timestamp")
    public void givenLocalDatetimeWhenMapThenReturnTimestamp() {
        var dateTime = LocalDateTime.parse("2023-11-12T13:46:45.712949", DateTimeFormatter.ISO_DATE_TIME);
        var timestamp = TimestampGrpcMapper.toTimestamp(dateTime);

        assertThat(timestamp).isNotNull();
        assertThat(timestamp.getSeconds()).isEqualTo(1699796805);
        assertThat(timestamp.getNanos()).isEqualTo(712949000);
    }


}
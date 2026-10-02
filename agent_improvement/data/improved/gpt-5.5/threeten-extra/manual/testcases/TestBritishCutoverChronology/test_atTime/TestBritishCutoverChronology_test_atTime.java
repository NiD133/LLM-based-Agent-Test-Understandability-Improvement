package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_atTime {

    @Test
    public void test_atTime() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 10, 12);
        LocalTime noonThirty = LocalTime.of(12, 30);

        ChronoLocalDateTime<BritishCutoverDate> dateTime = date.atTime(noonThirty);

        assertEquals(date, dateTime.toLocalDate());
        assertEquals(LocalTime.of(12, 30), dateTime.toLocalTime());

        ChronoLocalDateTime<BritishCutoverDate> convertedDateTime =
                BritishCutoverChronology.INSTANCE.localDateTime(LocalDateTime.from(dateTime));
        assertEquals(dateTime, convertedDateTime);
    }
}

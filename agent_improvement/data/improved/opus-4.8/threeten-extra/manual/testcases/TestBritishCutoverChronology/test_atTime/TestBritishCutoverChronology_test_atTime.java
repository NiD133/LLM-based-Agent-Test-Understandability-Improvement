package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_atTime {

    /**
     * Verifies that combining a {@link BritishCutoverDate} with a {@link LocalTime}
     * via {@code atTime} preserves both the date and the time, and that the resulting
     * date-time can be round-tripped through the chronology's {@code localDateTime} factory.
     */
    @Test
    public void test_atTime() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 10, 12);
        LocalTime time = LocalTime.of(12, 30);

        ChronoLocalDateTime<BritishCutoverDate> dateTime = date.atTime(time);

        // The combined date-time should expose the original date and time unchanged.
        assertEquals(date, dateTime.toLocalDate());
        assertEquals(time, dateTime.toLocalTime());

        // Round-tripping through the chronology should yield an equal date-time.
        ChronoLocalDateTime<BritishCutoverDate> roundTripped =
                BritishCutoverChronology.INSTANCE.localDateTime(LocalDateTime.from(dateTime));
        assertEquals(dateTime, roundTripped);
    }
}

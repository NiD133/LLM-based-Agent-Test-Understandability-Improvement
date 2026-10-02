package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_atTime {

    /**
     * Verifies that BritishCutoverDate.atTime(LocalTime) produces a ChronoLocalDateTime
     * whose date and time components round-trip correctly, and that converting back via
     * BritishCutoverChronology.localDateTime yields an equal value.
     */
    @Test
    public void test_atTime() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 10, 12);
        LocalTime time = LocalTime.of(12, 30);

        ChronoLocalDateTime<BritishCutoverDate> test = date.atTime(time);

        // The date and time parts must match what we combined.
        assertEquals(date, test.toLocalDate());
        assertEquals(time, test.toLocalTime());

        // Re-creating the same date-time through the chronology must yield an equal result.
        ChronoLocalDateTime<BritishCutoverDate> test2 =
                BritishCutoverChronology.INSTANCE.localDateTime(LocalDateTime.from(test));
        assertEquals(test, test2);
    }
}

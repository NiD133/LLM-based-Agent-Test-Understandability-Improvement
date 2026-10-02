package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link InternationalFixedDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero-length period when a date is measured against itself.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_InternationalFixedDate_until_InternationalFixedDate {

    /**
     * A representative spread of International Fixed dates: ordinary days, leap years,
     * the Leap Day (month 6, day 29), the Year Day (month 13, day 29) and various centuries.
     */
    public static Object[][] sampleDates() {
        return new Object[][] {
            { InternationalFixedDate.of(1, 1, 1) },
            { InternationalFixedDate.of(1, 1, 2) },
            { InternationalFixedDate.of(1, 6, 27) },
            { InternationalFixedDate.of(1, 6, 28) },
            { InternationalFixedDate.of(1, 7, 1) },
            { InternationalFixedDate.of(1, 7, 2) },
            { InternationalFixedDate.of(1, 13, 28) },
            { InternationalFixedDate.of(1, 13, 27) },
            { InternationalFixedDate.of(1, 13, 29) },
            { InternationalFixedDate.of(2, 1, 1) },
            { InternationalFixedDate.of(4, 6, 27) },
            { InternationalFixedDate.of(4, 6, 28) },
            { InternationalFixedDate.of(4, 6, 29) },
            { InternationalFixedDate.of(4, 7, 1) },
            { InternationalFixedDate.of(4, 7, 2) },
            { InternationalFixedDate.of(4, 13, 28) },
            { InternationalFixedDate.of(4, 13, 27) },
            { InternationalFixedDate.of(4, 13, 29) },
            { InternationalFixedDate.of(5, 1, 1) },
            { InternationalFixedDate.of(100, 6, 27) },
            { InternationalFixedDate.of(100, 6, 28) },
            { InternationalFixedDate.of(100, 7, 1) },
            { InternationalFixedDate.of(100, 7, 2) },
            { InternationalFixedDate.of(400, 6, 27) },
            { InternationalFixedDate.of(400, 6, 28) },
            { InternationalFixedDate.of(400, 6, 29) },
            { InternationalFixedDate.of(400, 7, 1) },
            { InternationalFixedDate.of(400, 7, 2) },
            { InternationalFixedDate.of(1582, 9, 28) },
            { InternationalFixedDate.of(1582, 10, 1) },
            { InternationalFixedDate.of(1945, 10, 27) },
            { InternationalFixedDate.of(2012, 6, 15) },
            { InternationalFixedDate.of(2012, 6, 16) },
        };
    }

    @ParameterizedTest
    @MethodSource("sampleDates")
    public void until_sameDate_returnsZeroPeriod(InternationalFixedDate date) {
        assertEquals(InternationalFixedChronology.INSTANCE.period(0, 0, 0), date.until(date));
    }
}

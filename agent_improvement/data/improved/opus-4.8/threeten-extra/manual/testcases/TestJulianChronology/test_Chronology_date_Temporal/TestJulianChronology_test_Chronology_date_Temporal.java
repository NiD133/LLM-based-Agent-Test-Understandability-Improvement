package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianChronology#date(java.time.temporal.TemporalAccessor)}
 * converts an ISO {@link LocalDate} into the equivalent {@link JulianDate}.
 */
public class TestJulianChronology_test_Chronology_date_Temporal {

    /**
     * Pairs of equivalent dates: each {@link JulianDate} and the ISO {@link LocalDate}
     * that denotes the same point on the time-line.
     * <p>
     * Note that {@code 0001-01-01 (Julian)} aligns with {@code 0000-12-30 (ISO)}, and
     * that the Julian/Gregorian offset grows over the centuries (e.g. 13 days by 2012).
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Julian date              equivalent ISO date
            { JulianDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),    LocalDate.of(1, 1, 1) },
            { JulianDate.of(1, 2, 28),   LocalDate.of(1, 2, 26) },
            { JulianDate.of(1, 3, 1),    LocalDate.of(1, 2, 27) },
            { JulianDate.of(1, 3, 2),    LocalDate.of(1, 2, 28) },
            { JulianDate.of(1, 3, 3),    LocalDate.of(1, 3, 1) },
            // year 4 is a Julian leap year, so it has a 29th of February
            { JulianDate.of(4, 2, 28),   LocalDate.of(4, 2, 26) },
            { JulianDate.of(4, 2, 29),   LocalDate.of(4, 2, 27) },
            { JulianDate.of(4, 3, 1),    LocalDate.of(4, 2, 28) },
            { JulianDate.of(4, 3, 2),    LocalDate.of(4, 2, 29) },
            { JulianDate.of(4, 3, 3),    LocalDate.of(4, 3, 1) },
            // year 100 is a Julian leap year but NOT an ISO leap year
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3, 1),  LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3, 2),  LocalDate.of(100, 3, 1) },
            { JulianDate.of(100, 3, 3),  LocalDate.of(100, 3, 2) },
            { JulianDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },
            // around the 1582 Gregorian reform the offset is 10 days
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            // by the 20th/21st century the offset has grown to 13 days
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012, 6, 22),  LocalDate.of(2012, 7, 5) },
            { JulianDate.of(2012, 6, 23),  LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(JulianDate expectedJulian, LocalDate iso) {
        assertEquals(expectedJulian, JulianChronology.INSTANCE.date(iso));
    }
}

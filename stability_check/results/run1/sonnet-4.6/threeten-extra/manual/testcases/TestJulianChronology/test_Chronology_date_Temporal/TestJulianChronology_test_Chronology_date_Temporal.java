package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianChronology#date(java.time.temporal.TemporalAccessor)}
 * correctly converts ISO {@link LocalDate} values to their Julian calendar equivalents.
 */
public class TestJulianChronology_test_Chronology_date_Temporal {

    /**
     * Pairs of (Julian date, ISO date) that represent the same moment in time.
     * Julian dates are offset from ISO/Gregorian due to the difference in leap-year rules.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early AD era: Julian year 1 maps to ISO year 0 (proleptic)
            { JulianDate.of(1, 1, 1),    LocalDate.of(0,    12, 30) },
            { JulianDate.of(1, 1, 2),    LocalDate.of(0,    12, 31) },
            { JulianDate.of(1, 1, 3),    LocalDate.of(1,     1,  1) },
            { JulianDate.of(1, 2, 28),   LocalDate.of(1,     2, 26) },
            { JulianDate.of(1, 3,  1),   LocalDate.of(1,     2, 27) },
            { JulianDate.of(1, 3,  2),   LocalDate.of(1,     2, 28) },
            { JulianDate.of(1, 3,  3),   LocalDate.of(1,     3,  1) },
            // Julian leap year 4 (Feb 29 exists in Julian but not in proleptic Gregorian)
            { JulianDate.of(4, 2, 28),   LocalDate.of(4,     2, 26) },
            { JulianDate.of(4, 2, 29),   LocalDate.of(4,     2, 27) },
            { JulianDate.of(4, 3,  1),   LocalDate.of(4,     2, 28) },
            { JulianDate.of(4, 3,  2),   LocalDate.of(4,     2, 29) },
            { JulianDate.of(4, 3,  3),   LocalDate.of(4,     3,  1) },
            // Year 100: leap in Julian but not in Gregorian — offset grows by one day
            { JulianDate.of(100, 2, 28), LocalDate.of(100,   2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100,   2, 27) },
            { JulianDate.of(100, 3,  1), LocalDate.of(100,   2, 28) },
            { JulianDate.of(100, 3,  2), LocalDate.of(100,   3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of(100,   3,  2) },
            // Dates before year 1 (proleptic BC)
            { JulianDate.of(0, 12, 31),  LocalDate.of(0,    12, 29) },
            { JulianDate.of(0, 12, 30),  LocalDate.of(0,    12, 28) },
            // Around the Gregorian calendar reform of 1582
            { JulianDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },
            // Modern dates with 13-day Julian/Gregorian offset
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that converting an ISO {@link LocalDate} via {@link JulianChronology#date}
     * yields the expected {@link JulianDate}.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(JulianDate julian, LocalDate iso) {
        assertEquals(julian, JulianChronology.INSTANCE.date(iso));
    }
}

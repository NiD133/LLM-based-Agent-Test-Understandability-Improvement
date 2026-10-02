package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that JulianChronology.date(TemporalAccessor) correctly converts ISO LocalDates
 * into their corresponding JulianDate equivalents.
 *
 * The Julian calendar differs from the ISO/Gregorian calendar only in its leap-year rule
 * (every 4 years, with no century exception), which causes a growing offset between the
 * two calendars over time.
 */
public class TestJulianChronology_test_Chronology_date_Temporal {

    /**
     * Pairs of (Julian date, ISO date) that represent the same moment in time.
     * Each row verifies a specific conversion point, covering:
     * - Early dates near the epoch (year 1)
     * - Leap-year boundaries (years 4, 100, 1900, 2012)
     * - The 1582 calendar reform boundary
     * - Modern dates (1945, 2012)
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early dates near the Julian epoch
            { JulianDate.of(1, 1, 1),   LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),   LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),   LocalDate.of(1,  1,  1) },
            { JulianDate.of(1, 2, 28),  LocalDate.of(1,  2, 26) },
            { JulianDate.of(1, 3,  1),  LocalDate.of(1,  2, 27) },
            { JulianDate.of(1, 3,  2),  LocalDate.of(1,  2, 28) },
            { JulianDate.of(1, 3,  3),  LocalDate.of(1,  3,  1) },

            // Year 0 (1 BC) dates
            { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // Year 4: first Julian leap year — Feb 29 exists in Julian but shifts ISO dates
            { JulianDate.of(4, 2, 28),  LocalDate.of(4,  2, 26) },
            { JulianDate.of(4, 2, 29),  LocalDate.of(4,  2, 27) },
            { JulianDate.of(4, 3,  1),  LocalDate.of(4,  2, 28) },
            { JulianDate.of(4, 3,  2),  LocalDate.of(4,  2, 29) },
            { JulianDate.of(4, 3,  3),  LocalDate.of(4,  3,  1) },

            // Year 100: Julian leap year (Gregorian is not), offset grows by 1 day
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // The 1582 Gregorian calendar reform boundary (10-day gap)
            { JulianDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // Modern dates (Julian runs 13 days behind Gregorian by the 20th century)
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that converting an ISO {@link LocalDate} via
     * {@link JulianChronology#date(java.time.temporal.TemporalAccessor)} yields the expected
     * {@link JulianDate}.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(JulianDate julian, LocalDate iso) {
        assertEquals(julian, JulianChronology.INSTANCE.date(iso));
    }
}

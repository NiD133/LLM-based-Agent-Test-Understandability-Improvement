package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the conversion from an ISO {@link LocalDate} to a {@link JulianDate}
 * via {@link JulianDate#from(java.time.temporal.TemporalAccessor)}.
 */
public class TestJulianChronology_test_JulianDate_from_LocalDate {

    /**
     * Pairs of equivalent dates: each Julian date and the ISO date that
     * denotes the same point on the time-line.
     * <p>
     * By definition {@code 0001-01-01 (Julian)} equals {@code 0000-12-30 (ISO)},
     * and the gap between the two calendars widens over the centuries.
     *
     * @return rows of {expected JulianDate, equivalent ISO LocalDate}
     */
    public static Object[][] equivalentJulianAndIsoDates() {
        return new Object[][] {
            // Around the Julian/ISO epoch alignment
            { JulianDate.of(1, 1, 1), LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2), LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3), LocalDate.of(1, 1, 1) },
            { JulianDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
            { JulianDate.of(1, 3, 1), LocalDate.of(1, 2, 27) },
            { JulianDate.of(1, 3, 2), LocalDate.of(1, 2, 28) },
            { JulianDate.of(1, 3, 3), LocalDate.of(1, 3, 1) },

            // Year 4 is a Julian leap year (Feb 29 exists)
            { JulianDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { JulianDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { JulianDate.of(4, 3, 1), LocalDate.of(4, 2, 28) },
            { JulianDate.of(4, 3, 2), LocalDate.of(4, 2, 29) },
            { JulianDate.of(4, 3, 3), LocalDate.of(4, 3, 1) },

            // Year 100 is a Julian leap year but NOT an ISO leap year
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3, 1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3, 2), LocalDate.of(100, 3, 1) },
            { JulianDate.of(100, 3, 3), LocalDate.of(100, 3, 2) },

            // Dates just before the epoch alignment
            { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // Around the 1582 Gregorian reform date (proleptic, 10-day offset)
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // Modern dates (13-day offset)
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012, 6, 22), LocalDate.of(2012, 7, 5) },
            { JulianDate.of(2012, 6, 23), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("equivalentJulianAndIsoDates")
    public void test_JulianDate_from_LocalDate(JulianDate expectedJulianDate, LocalDate isoDate) {
        assertEquals(expectedJulianDate, JulianDate.from(isoDate));
    }
}

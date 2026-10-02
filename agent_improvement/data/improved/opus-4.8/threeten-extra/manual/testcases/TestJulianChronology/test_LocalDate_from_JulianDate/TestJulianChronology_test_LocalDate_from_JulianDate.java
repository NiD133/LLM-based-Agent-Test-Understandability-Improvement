package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that an ISO {@link LocalDate} can be created from a {@link JulianDate}
 * via {@link LocalDate#from(java.time.temporal.TemporalAccessor)}.
 * <p>
 * The Julian and ISO calendars differ only in their leap-year rule, and the two
 * time-lines are aligned so that {@code 0001-01-01 (Julian)} equals
 * {@code 0000-12-30 (ISO)}. The sample pairs below cover that alignment offset,
 * Julian-only leap days (e.g. years 100, 1900) and the Gregorian-reform region.
 */
public class TestJulianChronology_test_LocalDate_from_JulianDate {

    /**
     * Pairs of {@code [expected Julian date, equivalent ISO date]} used to check
     * the Julian-to-ISO conversion.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Near the epoch: the Julian time-line runs two days behind ISO here.
            { JulianDate.of(1, 1, 1), LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2), LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3), LocalDate.of(1, 1, 1) },
            { JulianDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
            { JulianDate.of(1, 3, 1), LocalDate.of(1, 2, 27) },
            { JulianDate.of(1, 3, 2), LocalDate.of(1, 2, 28) },
            { JulianDate.of(1, 3, 3), LocalDate.of(1, 3, 1) },

            // Year 4 is a leap year in both calendars (Julian Feb 29 exists).
            { JulianDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { JulianDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { JulianDate.of(4, 3, 1), LocalDate.of(4, 2, 28) },
            { JulianDate.of(4, 3, 2), LocalDate.of(4, 2, 29) },
            { JulianDate.of(4, 3, 3), LocalDate.of(4, 3, 1) },

            // Year 100 is a Julian leap year but not an ISO one, so the gap widens.
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3, 1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3, 2), LocalDate.of(100, 3, 1) },
            { JulianDate.of(100, 3, 3), LocalDate.of(100, 3, 2) },

            // Just before the epoch.
            { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // Around the 1582 Gregorian reform and into modern dates.
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012, 6, 22), LocalDate.of(2012, 7, 5) },
            { JulianDate.of(2012, 6, 23), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_JulianDate(JulianDate julian, LocalDate expectedIso) {
        assertEquals(expectedIso, LocalDate.from(julian));
    }
}

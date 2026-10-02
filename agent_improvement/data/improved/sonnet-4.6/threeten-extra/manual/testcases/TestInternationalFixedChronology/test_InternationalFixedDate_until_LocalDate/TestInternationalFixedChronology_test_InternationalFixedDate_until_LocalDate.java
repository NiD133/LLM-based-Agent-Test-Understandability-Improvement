package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that InternationalFixedDate.until(LocalDate) returns a zero period
 * when both dates represent the same point in time (equivalent IFC and ISO dates).
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_InternationalFixedDate_until_LocalDate {

    /**
     * Pairs of equivalent International Fixed and ISO (LocalDate) dates.
     * Each row: (ifcDate, isoDate) where both represent the same calendar day.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1: regular days and month boundaries
            { InternationalFixedDate.of(1,  1,  1),  LocalDate.of(1,  1,  1) },
            { InternationalFixedDate.of(1,  1,  2),  LocalDate.of(1,  1,  2) },
            // Year 1: around the Sol/July boundary (IFC months 6 and 7 straddle ISO June)
            { InternationalFixedDate.of(1,  6, 27),  LocalDate.of(1,  6, 16) },
            { InternationalFixedDate.of(1,  6, 28),  LocalDate.of(1,  6, 17) },
            { InternationalFixedDate.of(1,  7,  1),  LocalDate.of(1,  6, 18) },
            { InternationalFixedDate.of(1,  7,  2),  LocalDate.of(1,  6, 19) },
            // Year 1: end of year (IFC month 13, days 27-29 map to ISO Dec 29-31)
            { InternationalFixedDate.of(1, 13, 27),  LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 28),  LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 29),  LocalDate.of(1, 12, 31) },
            // Year 2: first day
            { InternationalFixedDate.of(2,  1,  1),  LocalDate.of(2,  1,  1) },
            // Year 4 (leap year): around the Leap Day in IFC month 6 (day 29)
            { InternationalFixedDate.of(4,  6, 27),  LocalDate.of(4,  6, 15) },
            { InternationalFixedDate.of(4,  6, 28),  LocalDate.of(4,  6, 16) },
            { InternationalFixedDate.of(4,  6, 29),  LocalDate.of(4,  6, 17) },  // Leap Day
            { InternationalFixedDate.of(4,  7,  1),  LocalDate.of(4,  6, 18) },
            { InternationalFixedDate.of(4,  7,  2),  LocalDate.of(4,  6, 19) },
            // Year 4 (leap year): end of year
            { InternationalFixedDate.of(4, 13, 27),  LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 28),  LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 29),  LocalDate.of(4, 12, 31) },
            // Year 5: first day (after first leap year)
            { InternationalFixedDate.of(5,  1,  1),  LocalDate.of(5,  1,  1) },
            // Year 100 (century, not a leap year): around IFC month 6/7 boundary
            { InternationalFixedDate.of(100,  6, 27),  LocalDate.of(100,  6, 16) },
            { InternationalFixedDate.of(100,  6, 28),  LocalDate.of(100,  6, 17) },
            { InternationalFixedDate.of(100,  7,  1),  LocalDate.of(100,  6, 18) },
            { InternationalFixedDate.of(100,  7,  2),  LocalDate.of(100,  6, 19) },
            // Year 400 (400-year cycle, is a leap year): around IFC Leap Day
            { InternationalFixedDate.of(400,  6, 27),  LocalDate.of(400,  6, 15) },
            { InternationalFixedDate.of(400,  6, 28),  LocalDate.of(400,  6, 16) },
            { InternationalFixedDate.of(400,  6, 29),  LocalDate.of(400,  6, 17) },  // Leap Day
            { InternationalFixedDate.of(400,  7,  1),  LocalDate.of(400,  6, 18) },
            { InternationalFixedDate.of(400,  7,  2),  LocalDate.of(400,  6, 19) },
            // Historical dates
            { InternationalFixedDate.of(1582,  9, 28),  LocalDate.of(1582,  9,  9) },
            { InternationalFixedDate.of(1582, 10,  1),  LocalDate.of(1582,  9, 10) },
            { InternationalFixedDate.of(1945, 10, 27),  LocalDate.of(1945, 10,  6) },
            // Modern dates (2012, leap year): around IFC month 6 Leap Day
            { InternationalFixedDate.of(2012,  6, 15),  LocalDate.of(2012,  6,  3) },
            { InternationalFixedDate.of(2012,  6, 16),  LocalDate.of(2012,  6,  4) },
        };
    }

    /**
     * Verifies that calling until(isoDate) on an IFC date that is equivalent to
     * the given ISO date returns a zero-length period (years=0, months=0, days=0).
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_InternationalFixedDate_until_LocalDate(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(InternationalFixedChronology.INSTANCE.period(0, 0, 0), fixed.until(iso));
    }
}

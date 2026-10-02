package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_InternationalFixedDate_chronology_dateEpochDay {

    /**
     * Pairs of (InternationalFixedDate, ISO LocalDate) that represent the same day.
     * The test verifies that converting the ISO epoch-day back through the chronology
     * produces the expected International Fixed date.
     *
     * Grouped by scenario:
     *   - Start of year 1
     *   - Non-leap year boundary around Sol (month 6 / month 7) and year-end
     *   - Leap year 4: Leap Day (6/29) and year-end
     *   - First day of year 5 (after leap year 4)
     *   - Non-leap century year 100 boundary
     *   - Leap 400-year boundary
     *   - Historical spot-checks (1582, 1945, 2012)
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Start of era: year 1, day 1 and day 2
            { InternationalFixedDate.of(1, 1, 1),  LocalDate.of(1, 1, 1) },
            { InternationalFixedDate.of(1, 1, 2),  LocalDate.of(1, 1, 2) },

            // Non-leap year 1: boundary around the Sol/July transition (month 6 → 7)
            { InternationalFixedDate.of(1, 6, 27), LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28), LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1),  LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2),  LocalDate.of(1, 6, 19) },

            // Non-leap year 1: last three days of the year (month 13)
            { InternationalFixedDate.of(1, 13, 27), LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 28), LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 29), LocalDate.of(1, 12, 31) },

            // First day of year 2
            { InternationalFixedDate.of(2, 1, 1),  LocalDate.of(2, 1, 1) },

            // Leap year 4: Sol/July transition (month 6 → 7), including Leap Day (6/29)
            { InternationalFixedDate.of(4, 6, 27), LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28), LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29), LocalDate.of(4, 6, 17) },
            { InternationalFixedDate.of(4, 7, 1),  LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2),  LocalDate.of(4, 6, 19) },

            // Leap year 4: last three days of the year (month 13)
            { InternationalFixedDate.of(4, 13, 27), LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 28), LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 29), LocalDate.of(4, 12, 31) },

            // First day of year 5 (year after first leap year)
            { InternationalFixedDate.of(5, 1, 1),  LocalDate.of(5, 1, 1) },

            // Non-leap century year 100: Sol/July transition (no Leap Day)
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1),  LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2),  LocalDate.of(100, 6, 19) },

            // Leap 400-year boundary: year 400 has a Leap Day (divisible by 400)
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) },
            { InternationalFixedDate.of(400, 7, 1),  LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2),  LocalDate.of(400, 6, 19) },

            // Historical spot-check: Julian/Gregorian calendar reform date (1582)
            { InternationalFixedDate.of(1582, 9, 28), LocalDate.of(1582, 9,  9) },
            { InternationalFixedDate.of(1582, 10, 1), LocalDate.of(1582, 9, 10) },

            // Historical spot-check: end of WWII (1945)
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10, 6) },

            // Leap year 2012: two consecutive days around the Sol boundary
            { InternationalFixedDate.of(2012, 6, 15), LocalDate.of(2012, 6, 3) },
            { InternationalFixedDate.of(2012, 6, 16), LocalDate.of(2012, 6, 4) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_InternationalFixedDate_chronology_dateEpochDay(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(fixed, InternationalFixedChronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}

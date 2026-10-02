package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_until_InternationalFixedDate {

    /**
     * Pairs of (InternationalFixedDate, LocalDate) that represent the same day.
     * Verifies that iso.until(fixed) returns Period.ZERO when both dates refer to the same point in time.
     *
     * Grouped by scenario:
     *   - Simple early dates (year 1, months 1 and 6)
     *   - Year-Day boundary (IFC month 13 day 29 = ISO Dec 31)
     *   - Leap-year boundary in year 4 (IFC month 6 day 29 = Leap Day)
     *   - Year boundary for year 5 (normal year following leap year 4)
     *   - Century year 100 (not a leap year in Gregorian)
     *   - 400-year cycle boundary (year 400, which IS a leap year)
     *   - Historical dates: 1582, 1945, 2012
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 — early dates and the Sol/June boundary
            { InternationalFixedDate.of(1, 1, 1),  LocalDate.of(1, 1, 1)  },
            { InternationalFixedDate.of(1, 1, 2),  LocalDate.of(1, 1, 2)  },
            { InternationalFixedDate.of(1, 6, 27), LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28), LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1),  LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2),  LocalDate.of(1, 6, 19) },

            // Year 1 — Year Day (IFC 1/13/29 = ISO Dec 31)
            { InternationalFixedDate.of(1, 13, 27), LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 28), LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 29), LocalDate.of(1, 12, 31) },

            // Year 2 — start of year after non-leap year 1
            { InternationalFixedDate.of(2, 1, 1),  LocalDate.of(2, 1, 1)  },

            // Year 4 — Leap Day in IFC month 6 (IFC 4/6/29 = ISO Jun 17)
            { InternationalFixedDate.of(4, 6, 27), LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28), LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29), LocalDate.of(4, 6, 17) },
            { InternationalFixedDate.of(4, 7, 1),  LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2),  LocalDate.of(4, 6, 19) },

            // Year 4 — Year Day
            { InternationalFixedDate.of(4, 13, 27), LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 28), LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 29), LocalDate.of(4, 12, 31) },

            // Year 5 — start of year after leap year 4
            { InternationalFixedDate.of(5, 1, 1),  LocalDate.of(5, 1, 1)  },

            // Year 100 — century year (not a Gregorian leap year; no IFC Leap Day)
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1),  LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2),  LocalDate.of(100, 6, 19) },

            // Year 400 — divisible by 400, so it IS a Gregorian leap year; IFC Leap Day present
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) },
            { InternationalFixedDate.of(400, 7, 1),  LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2),  LocalDate.of(400, 6, 19) },

            // Historical dates
            { InternationalFixedDate.of(1582, 9, 28),  LocalDate.of(1582, 9,  9) },
            { InternationalFixedDate.of(1582, 10, 1),  LocalDate.of(1582, 9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10,  6) },

            // Recent dates in 2012 (a leap year)
            { InternationalFixedDate.of(2012, 6, 15), LocalDate.of(2012, 6, 3) },
            { InternationalFixedDate.of(2012, 6, 16), LocalDate.of(2012, 6, 4) },
        };
    }

    /**
     * Verifies that an ISO LocalDate and its corresponding InternationalFixedDate represent the same day,
     * i.e. that iso.until(fixed) returns Period.ZERO.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_InternationalFixedDate(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(fixed));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_from_InternationalFixedDate {

    /**
     * Pairs of (InternationalFixedDate, expected ISO LocalDate) used to verify
     * that converting an IFC date to a LocalDate yields the correct Gregorian date.
     *
     * The IFC calendar has 13 months of 28 days each, plus a Year Day (month 13 day 29)
     * and, in leap years, a Leap Day (month 6 day 29). These extra days shift the
     * mapping relative to the Gregorian calendar.
     *
     * Groups below cover:
     *  - Non-leap year (year 1): basic dates and the Sol/month-7 boundary
     *  - Non-leap year (year 1): end-of-year (Year Day) mapping
     *  - Leap year (year 4): Sol month with Leap Day, and end-of-year
     *  - Century non-leap year (year 100): Sol boundary without Leap Day
     *  - 400-year leap year (year 400): Sol boundary with Leap Day
     *  - Historical spot checks (1582, 1945, 2012)
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Year 1 (non-leap): early months ---
            { InternationalFixedDate.of(1, 1, 1),  LocalDate.of(1, 1, 1)  },
            { InternationalFixedDate.of(1, 1, 2),  LocalDate.of(1, 1, 2)  },

            // --- Year 1 (non-leap): around the Sol/month-7 boundary ---
            // Month 6 ends at day 28; month 7 day 1 follows immediately (no Leap Day)
            { InternationalFixedDate.of(1, 6, 27), LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28), LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1),  LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2),  LocalDate.of(1, 6, 19) },

            // --- Year 1 (non-leap): end-of-year / Year Day ---
            { InternationalFixedDate.of(1, 13, 27), LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 28), LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 29), LocalDate.of(1, 12, 31) }, // Year Day

            // --- Year 2: first day of year ---
            { InternationalFixedDate.of(2, 1, 1),  LocalDate.of(2, 1, 1)  },

            // --- Year 4 (leap): Leap Day present in month 6 ---
            // Month 6 day 28 -> ISO shifts one day earlier than non-leap
            { InternationalFixedDate.of(4, 6, 27), LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28), LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29), LocalDate.of(4, 6, 17) }, // Leap Day
            { InternationalFixedDate.of(4, 7, 1),  LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2),  LocalDate.of(4, 6, 19) },

            // --- Year 4 (leap): end-of-year / Year Day ---
            { InternationalFixedDate.of(4, 13, 27), LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 28), LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 29), LocalDate.of(4, 12, 31) }, // Year Day

            // --- Year 5: first day of year after a leap year ---
            { InternationalFixedDate.of(5, 1, 1),  LocalDate.of(5, 1, 1)  },

            // --- Year 100 (century, non-leap): Sol boundary without Leap Day ---
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1),  LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2),  LocalDate.of(100, 6, 19) },

            // --- Year 400 (400-year cycle, leap): Sol boundary with Leap Day ---
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) }, // Leap Day
            { InternationalFixedDate.of(400, 7, 1),  LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2),  LocalDate.of(400, 6, 19) },

            // --- Historical spot checks ---
            { InternationalFixedDate.of(1582, 9, 28),  LocalDate.of(1582, 9, 9)  },
            { InternationalFixedDate.of(1582, 10, 1),  LocalDate.of(1582, 9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10, 6) },
            { InternationalFixedDate.of(2012, 6, 15),  LocalDate.of(2012, 6, 3)  },
            { InternationalFixedDate.of(2012, 6, 16),  LocalDate.of(2012, 6, 4)  },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_InternationalFixedDate(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(iso, LocalDate.from(fixed));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link InternationalFixedChronology#date(java.time.temporal.TemporalAccessor)}
 * correctly converts ISO {@link LocalDate} values into the equivalent
 * {@link InternationalFixedDate} in the International Fixed calendar.
 *
 * <p>The International Fixed calendar has 13 months of 28 days each, plus a
 * Year Day (month 13, day 29) and, in leap years, a Leap Day (month 6, day 29).
 * The parameterised data below covers ordinary dates, Year Day, Leap Day, and
 * boundary years (year 1, leap years 4 / 400, century non-leap year 100, and
 * recent dates such as 1945 and 2012).
 */
@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_Chronology_date_Temporal {

    /**
     * Pairs of (expected InternationalFixedDate, ISO LocalDate to convert).
     *
     * <p>Each row asserts that converting the ISO date on the right produces
     * the International Fixed date on the left.  The table is organised in
     * roughly chronological order and deliberately includes:
     * <ul>
     *   <li>The epoch (year 1, month 1, day 1)</li>
     *   <li>Dates straddling the mid-year boundary where the IFC and ISO month
     *       numbering diverges (around June in ISO / month 6–7 in IFC)</li>
     *   <li>Year Day (month 13, day 29) in both ordinary and leap years</li>
     *   <li>Leap Day (month 6, day 29) present only in year 4, 400, and 2012
     *       (skipped in century year 100)</li>
     *   <li>Historically notable dates (1582, 1945)</li>
     * </ul>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 — epoch and nearby dates
            { InternationalFixedDate.of(1,  1,  1),  LocalDate.of(1,  1,  1)  },
            { InternationalFixedDate.of(1,  1,  2),  LocalDate.of(1,  1,  2)  },
            { InternationalFixedDate.of(1,  6, 27),  LocalDate.of(1,  6, 16)  },
            { InternationalFixedDate.of(1,  6, 28),  LocalDate.of(1,  6, 17)  },
            { InternationalFixedDate.of(1,  7,  1),  LocalDate.of(1,  6, 18)  },
            { InternationalFixedDate.of(1,  7,  2),  LocalDate.of(1,  6, 19)  },
            { InternationalFixedDate.of(1, 13, 27),  LocalDate.of(1, 12, 29)  },
            { InternationalFixedDate.of(1, 13, 28),  LocalDate.of(1, 12, 30)  },
            { InternationalFixedDate.of(1, 13, 29),  LocalDate.of(1, 12, 31)  }, // Year Day (non-leap)

            // Year 2 — first day of the second year
            { InternationalFixedDate.of(2,  1,  1),  LocalDate.of(2,  1,  1)  },

            // Year 4 — first Gregorian/IFC leap year; Leap Day present (month 6, day 29)
            { InternationalFixedDate.of(4,  6, 27),  LocalDate.of(4,  6, 15)  },
            { InternationalFixedDate.of(4,  6, 28),  LocalDate.of(4,  6, 16)  },
            { InternationalFixedDate.of(4,  6, 29),  LocalDate.of(4,  6, 17)  }, // Leap Day
            { InternationalFixedDate.of(4,  7,  1),  LocalDate.of(4,  6, 18)  },
            { InternationalFixedDate.of(4,  7,  2),  LocalDate.of(4,  6, 19)  },
            { InternationalFixedDate.of(4, 13, 27),  LocalDate.of(4, 12, 29)  },
            { InternationalFixedDate.of(4, 13, 28),  LocalDate.of(4, 12, 30)  },
            { InternationalFixedDate.of(4, 13, 29),  LocalDate.of(4, 12, 31)  }, // Year Day (leap)

            // Year 5 — first day after first leap year
            { InternationalFixedDate.of(5,  1,  1),  LocalDate.of(5,  1,  1)  },

            // Year 100 — century year: NOT a leap year (skips Leap Day)
            { InternationalFixedDate.of(100,  6, 27), LocalDate.of(100,  6, 16) },
            { InternationalFixedDate.of(100,  6, 28), LocalDate.of(100,  6, 17) },
            { InternationalFixedDate.of(100,  7,  1), LocalDate.of(100,  6, 18) },
            { InternationalFixedDate.of(100,  7,  2), LocalDate.of(100,  6, 19) },

            // Year 400 — century year divisible by 400: IS a leap year (Leap Day present)
            { InternationalFixedDate.of(400,  6, 27), LocalDate.of(400,  6, 15) },
            { InternationalFixedDate.of(400,  6, 28), LocalDate.of(400,  6, 16) },
            { InternationalFixedDate.of(400,  6, 29), LocalDate.of(400,  6, 17) }, // Leap Day
            { InternationalFixedDate.of(400,  7,  1), LocalDate.of(400,  6, 18) },
            { InternationalFixedDate.of(400,  7,  2), LocalDate.of(400,  6, 19) },

            // Historical dates
            { InternationalFixedDate.of(1582,  9, 28), LocalDate.of(1582,  9,  9) },
            { InternationalFixedDate.of(1582, 10,  1), LocalDate.of(1582,  9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10,  6) },

            // Recent dates (2012 is a leap year)
            { InternationalFixedDate.of(2012,  6, 15), LocalDate.of(2012,  6,  3) },
            { InternationalFixedDate.of(2012,  6, 16), LocalDate.of(2012,  6,  4) },
        };
    }

    /**
     * Verifies that converting an ISO {@link LocalDate} via
     * {@link InternationalFixedChronology#date(java.time.temporal.TemporalAccessor)}
     * produces the expected {@link InternationalFixedDate}.
     *
     * @param expectedFixed the expected International Fixed date
     * @param isoDate       the ISO date to convert
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(InternationalFixedDate expectedFixed, LocalDate isoDate) {
        assertEquals(expectedFixed, InternationalFixedChronology.INSTANCE.date(isoDate));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_InternationalFixedDate_toEpochDay {

    /**
     * Each row pairs an InternationalFixedDate with the ISO LocalDate that falls on
     * the same day, so that both should return the same epoch-day value.
     *
     * Cases are grouped by the calendar rule they exercise:
     *   - year 1 basics (month 1 and around the Sol/mid-year boundary)
     *   - Year Day (month 13, day 29) in non-leap years
     *   - year boundary (year 2)
     *   - leap year (year 4): Leap Day (month 6, day 29) and Year Day
     *   - century (year 100, not a leap year): Sol boundary
     *   - 400-year cycle (year 400, leap year): Leap Day and Sol boundary
     *   - historical dates: 1582 calendar reform and modern dates
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 – ordinary days near start of year
            { InternationalFixedDate.of(1, 1, 1),   LocalDate.of(1, 1, 1)  },
            { InternationalFixedDate.of(1, 1, 2),   LocalDate.of(1, 1, 2)  },

            // Year 1 – around the Sol (month 6/7) boundary (no Leap Day in year 1)
            { InternationalFixedDate.of(1, 6, 27),  LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28),  LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1),   LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2),   LocalDate.of(1, 6, 19) },

            // Year 1 – Year Day (month 13, day 29) and adjacent days
            { InternationalFixedDate.of(1, 13, 27), LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 28), LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 29), LocalDate.of(1, 12, 31) },

            // Year boundary: first day of year 2
            { InternationalFixedDate.of(2, 1, 1),   LocalDate.of(2, 1, 1)  },

            // Year 4 (leap year) – Leap Day (month 6, day 29) and Sol boundary
            { InternationalFixedDate.of(4, 6, 27),  LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28),  LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29),  LocalDate.of(4, 6, 17) },
            { InternationalFixedDate.of(4, 7, 1),   LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2),   LocalDate.of(4, 6, 19) },

            // Year 4 – Year Day and adjacent days
            { InternationalFixedDate.of(4, 13, 27), LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 28), LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 29), LocalDate.of(4, 12, 31) },

            // Year 5 – first day after first leap year
            { InternationalFixedDate.of(5, 1, 1),   LocalDate.of(5, 1, 1)  },

            // Year 100 (century, NOT a leap year) – Sol boundary
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1),  LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2),  LocalDate.of(100, 6, 19) },

            // Year 400 (400-year cycle, IS a leap year) – Leap Day and Sol boundary
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) },
            { InternationalFixedDate.of(400, 7, 1),  LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2),  LocalDate.of(400, 6, 19) },

            // Historical: around the Gregorian calendar reform in 1582
            { InternationalFixedDate.of(1582, 9, 28), LocalDate.of(1582, 9, 9)  },
            { InternationalFixedDate.of(1582, 10, 1), LocalDate.of(1582, 9, 10) },

            // Historical: mid-20th century and early-21st century spot checks
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10, 6) },
            { InternationalFixedDate.of(2012, 6, 15),  LocalDate.of(2012, 6, 3)  },
            { InternationalFixedDate.of(2012, 6, 16),  LocalDate.of(2012, 6, 4)  },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_InternationalFixedDate_toEpochDay(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(iso.toEpochDay(), fixed.toEpochDay());
    }
}

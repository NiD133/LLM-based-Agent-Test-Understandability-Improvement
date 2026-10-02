package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_InternationalFixedDate_from_LocalDate {

    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 - basic early dates
            { InternationalFixedDate.of(1, 1, 1),    LocalDate.of(1, 1, 1) },
            { InternationalFixedDate.of(1, 1, 2),    LocalDate.of(1, 1, 2) },

            // Year 1 - around the Sol/June boundary (IFC months 6 and 7 straddle ISO June)
            { InternationalFixedDate.of(1, 6, 27),   LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28),   LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1),    LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2),    LocalDate.of(1, 6, 19) },

            // Year 1 - Year Day (month 13 day 29) maps to the last three days of ISO December
            { InternationalFixedDate.of(1, 13, 27),  LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 28),  LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 29),  LocalDate.of(1, 12, 31) },

            // Year 2 - start of a non-leap year
            { InternationalFixedDate.of(2, 1, 1),    LocalDate.of(2, 1, 1) },

            // Year 4 (leap year) - Leap Day is month 6 day 29; verify the day before, on, and after
            { InternationalFixedDate.of(4, 6, 27),   LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28),   LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29),   LocalDate.of(4, 6, 17) },
            { InternationalFixedDate.of(4, 7, 1),    LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2),    LocalDate.of(4, 6, 19) },

            // Year 4 - Year Day
            { InternationalFixedDate.of(4, 13, 27),  LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 28),  LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 29),  LocalDate.of(4, 12, 31) },

            // Year 5 - first day after a leap year
            { InternationalFixedDate.of(5, 1, 1),    LocalDate.of(5, 1, 1) },

            // Year 100 (century year, NOT a leap year) - no Leap Day; month 6 has only 28 days
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1),  LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2),  LocalDate.of(100, 6, 19) },

            // Year 400 (leap century) - IS a leap year; verify Leap Day present
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) },
            { InternationalFixedDate.of(400, 7, 1),  LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2),  LocalDate.of(400, 6, 19) },

            // Historical spot-checks
            { InternationalFixedDate.of(1582,  9, 28), LocalDate.of(1582,  9,  9) },
            { InternationalFixedDate.of(1582, 10,  1), LocalDate.of(1582,  9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10,  6) },

            // Year 2012 (modern leap year) - two ordinary Sol dates
            { InternationalFixedDate.of(2012, 6, 15), LocalDate.of(2012, 6,  3) },
            { InternationalFixedDate.of(2012, 6, 16), LocalDate.of(2012, 6,  4) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_InternationalFixedDate_from_LocalDate(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(fixed, InternationalFixedDate.from(iso));
    }
}

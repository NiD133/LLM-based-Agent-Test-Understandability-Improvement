package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plusDays {

    // Each row maps an InternationalFixedDate to its ISO LocalDate equivalent.
    // The IFC calendar has 13 months of 28 days plus special intercalary days
    // (Leap Day in month 6, Year Day in month 13), which creates the offsets visible here.
    public static Object[][] data_samples() {
        return new Object[][] {
            // year 1, ordinary months
            { InternationalFixedDate.of(1, 1, 1),   LocalDate.of(1, 1, 1)  },
            { InternationalFixedDate.of(1, 1, 2),   LocalDate.of(1, 1, 2)  },
            // year 1, around Sol (month 7) boundary — no Leap Day in year 1
            { InternationalFixedDate.of(1, 6, 27),  LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28),  LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1),   LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2),   LocalDate.of(1, 6, 19) },
            // year 1, end-of-year (Year Day = month 13 day 29)
            { InternationalFixedDate.of(1, 13, 27), LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 28), LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 29), LocalDate.of(1, 12, 31) },
            // year 2, start
            { InternationalFixedDate.of(2, 1, 1),   LocalDate.of(2, 1, 1)  },
            // year 4 (leap year), around Leap Day (month 6 day 29)
            { InternationalFixedDate.of(4, 6, 27),  LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28),  LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29),  LocalDate.of(4, 6, 17) }, // Leap Day
            { InternationalFixedDate.of(4, 7, 1),   LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2),   LocalDate.of(4, 6, 19) },
            // year 4, end-of-year
            { InternationalFixedDate.of(4, 13, 27), LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 28), LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 29), LocalDate.of(4, 12, 31) },
            // year 5, start
            { InternationalFixedDate.of(5, 1, 1),   LocalDate.of(5, 1, 1)  },
            // year 100 (century non-leap), around Sol boundary
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1),  LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2),  LocalDate.of(100, 6, 19) },
            // year 400 (century leap), around Leap Day
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) }, // Leap Day
            { InternationalFixedDate.of(400, 7, 1),  LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2),  LocalDate.of(400, 6, 19) },
            // historical dates
            { InternationalFixedDate.of(1582, 9, 28), LocalDate.of(1582, 9, 9)  },
            { InternationalFixedDate.of(1582, 10, 1), LocalDate.of(1582, 9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10, 6) },
            // modern dates
            { InternationalFixedDate.of(2012, 6, 15), LocalDate.of(2012, 6, 3)  },
            { InternationalFixedDate.of(2012, 6, 16), LocalDate.of(2012, 6, 4)  },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(iso,              LocalDate.from(fixed.plus(0,   DAYS)));
        assertEquals(iso.plusDays(1),  LocalDate.from(fixed.plus(1,   DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(fixed.plus(35,  DAYS)));

        // Avoid underflowing before the proleptic epoch (year 1, day 1):
        // only test backward arithmetic when the ISO date is past day 60 of year 1.
        boolean safeToSubtract = LocalDate.ofYearDay(1, 60).isBefore(iso);
        if (safeToSubtract) {
            assertEquals(iso.plusDays(-1),  LocalDate.from(fixed.plus(-1,  DAYS)));
            assertEquals(iso.plusDays(-60), LocalDate.from(fixed.plus(-60, DAYS)));
        }
    }
}

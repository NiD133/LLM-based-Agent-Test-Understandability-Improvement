package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_plusDays {

    /**
     * Pairs of (JulianDate, equivalent ISO LocalDate) used to verify that arithmetic
     * on a Julian date maps correctly to the same arithmetic on the ISO calendar.
     *
     * Each entry covers a distinct boundary: start of the calendar epoch, month
     * transitions around February in non-leap / Julian-leap / Gregorian-non-leap
     * years (AD 4, AD 100), negative proleptic years, the Gregorian reform date
     * (1582-10-04/15), and a modern date.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Julian epoch start
            { JulianDate.of(1, 1, 1),   LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),   LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),   LocalDate.of(1,  1,  1) },

            // Year 1 – February/March boundary (Julian leap, ISO non-leap)
            { JulianDate.of(1, 2, 28),  LocalDate.of(1,  2, 26) },
            { JulianDate.of(1, 3,  1),  LocalDate.of(1,  2, 27) },
            { JulianDate.of(1, 3,  2),  LocalDate.of(1,  2, 28) },
            { JulianDate.of(1, 3,  3),  LocalDate.of(1,  3,  1) },

            // Year 4 – leap in both Julian and ISO
            { JulianDate.of(4, 2, 28),  LocalDate.of(4,  2, 26) },
            { JulianDate.of(4, 2, 29),  LocalDate.of(4,  2, 27) },
            { JulianDate.of(4, 3,  1),  LocalDate.of(4,  2, 28) },
            { JulianDate.of(4, 3,  2),  LocalDate.of(4,  2, 29) },
            { JulianDate.of(4, 3,  3),  LocalDate.of(4,  3,  1) },

            // Year 100 – Julian leap, ISO non-leap (first divergence)
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // Negative (BC) proleptic year
            { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // Gregorian calendar reform boundary (1582)
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // Modern dates
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that adding days to a {@link JulianDate} produces the same ISO date
     * as adding the same number of days to the equivalent {@link LocalDate}.
     *
     * Tested offsets: 0, +1, +35, -1, -60 days.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(JulianDate julian, LocalDate iso) {
        assertEquals(iso,              LocalDate.from(julian.plus(  0, DAYS)));
        assertEquals(iso.plusDays( 1), LocalDate.from(julian.plus(  1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(julian.plus( 35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(julian.plus( -1, DAYS)));
        assertEquals(iso.plusDays(-60),LocalDate.from(julian.plus(-60, DAYS)));
    }
}

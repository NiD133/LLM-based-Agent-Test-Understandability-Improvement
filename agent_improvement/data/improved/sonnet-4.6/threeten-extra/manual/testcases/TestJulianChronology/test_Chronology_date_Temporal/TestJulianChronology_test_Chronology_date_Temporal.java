package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_Chronology_date_Temporal {

    /**
     * Pairs of (Julian date, equivalent ISO/Gregorian date) used to verify
     * that {@link JulianChronology#date(java.time.temporal.TemporalAccessor)}
     * converts an ISO {@link LocalDate} back to the correct {@link JulianDate}.
     *
     * The Julian calendar differs from the Gregorian calendar by 2 days in year 1
     * and gradually shifts to 13 days by the 20th century, because the Julian
     * calendar adds a leap day every 4 years without exception while the Gregorian
     * calendar skips century years that are not divisible by 400.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 AD – Julian epoch starts 2 days before ISO epoch
            { JulianDate.of(1,  1,  1),  LocalDate.of(   0, 12, 30) },
            { JulianDate.of(1,  1,  2),  LocalDate.of(   0, 12, 31) },
            { JulianDate.of(1,  1,  3),  LocalDate.of(   1,  1,  1) },
            { JulianDate.of(1,  2, 28),  LocalDate.of(   1,  2, 26) },
            { JulianDate.of(1,  3,  1),  LocalDate.of(   1,  2, 27) },
            { JulianDate.of(1,  3,  2),  LocalDate.of(   1,  2, 28) },
            { JulianDate.of(1,  3,  3),  LocalDate.of(   1,  3,  1) },

            // Year 4 AD – first Julian leap year; Gregorian also leaps (gap stays 2)
            { JulianDate.of(4,  2, 28),  LocalDate.of(   4,  2, 26) },
            { JulianDate.of(4,  2, 29),  LocalDate.of(   4,  2, 27) },
            { JulianDate.of(4,  3,  1),  LocalDate.of(   4,  2, 28) },
            { JulianDate.of(4,  3,  2),  LocalDate.of(   4,  2, 29) },
            { JulianDate.of(4,  3,  3),  LocalDate.of(   4,  3,  1) },

            // Year 100 AD – Julian leaps but Gregorian does not (gap grows to 3)
            { JulianDate.of(100, 2, 28), LocalDate.of( 100,  2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of( 100,  2, 27) },
            { JulianDate.of(100, 3,  1), LocalDate.of( 100,  2, 28) },
            { JulianDate.of(100, 3,  2), LocalDate.of( 100,  3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of( 100,  3,  2) },

            // Year 0 / 1 BC boundary
            { JulianDate.of(0,  12, 31), LocalDate.of(   0, 12, 29) },
            { JulianDate.of(0,  12, 30), LocalDate.of(   0, 12, 28) },

            // Calendar reform date (1582-10-04 Julian = 1582-10-14 Gregorian / ISO)
            { JulianDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // 20th-century date – gap has grown to 13 days
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },

            // Modern dates – gap is still 13 days
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(JulianDate julian, LocalDate iso) {
        assertEquals(julian, JulianChronology.INSTANCE.date(iso));
    }
}

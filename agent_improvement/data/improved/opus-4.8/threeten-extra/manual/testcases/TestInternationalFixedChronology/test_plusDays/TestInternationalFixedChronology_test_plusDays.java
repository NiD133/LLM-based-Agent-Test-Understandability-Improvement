package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plusDays {

    /**
     * Pairs of equivalent dates: an International Fixed date and the ISO (proleptic
     * Gregorian) date that falls on the same day. Each pair is used to confirm that
     * adding days to the International Fixed date stays in step with the ISO calendar.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { InternationalFixedDate.of(1, 1, 1),      LocalDate.of(1, 1, 1) },
            { InternationalFixedDate.of(1, 1, 2),      LocalDate.of(1, 1, 2) },
            { InternationalFixedDate.of(1, 6, 27),     LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28),     LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1),      LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2),      LocalDate.of(1, 6, 19) },
            { InternationalFixedDate.of(1, 13, 28),    LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 27),    LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 29),    LocalDate.of(1, 12, 31) },
            { InternationalFixedDate.of(2, 1, 1),      LocalDate.of(2, 1, 1) },
            { InternationalFixedDate.of(4, 6, 27),     LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28),     LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29),     LocalDate.of(4, 6, 17) },
            { InternationalFixedDate.of(4, 7, 1),      LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2),      LocalDate.of(4, 6, 19) },
            { InternationalFixedDate.of(4, 13, 28),    LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 27),    LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 29),    LocalDate.of(4, 12, 31) },
            { InternationalFixedDate.of(5, 1, 1),      LocalDate.of(5, 1, 1) },
            { InternationalFixedDate.of(100, 6, 27),   LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28),   LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1),    LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2),    LocalDate.of(100, 6, 19) },
            { InternationalFixedDate.of(400, 6, 27),   LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28),   LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29),   LocalDate.of(400, 6, 17) },
            { InternationalFixedDate.of(400, 7, 1),    LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2),    LocalDate.of(400, 6, 19) },
            { InternationalFixedDate.of(1582, 9, 28),  LocalDate.of(1582, 9, 9) },
            { InternationalFixedDate.of(1582, 10, 1),  LocalDate.of(1582, 9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10, 6) },
            { InternationalFixedDate.of(2012, 6, 15),  LocalDate.of(2012, 6, 3) },
            { InternationalFixedDate.of(2012, 6, 16),  LocalDate.of(2012, 6, 4) },
        };
    }

    /**
     * Adding a number of days to an International Fixed date must yield the same day
     * as adding the same number of days to the equivalent ISO date.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(iso, LocalDate.from(fixed.plus(0, DAYS)));
        assertEquals(iso.plusDays(1), LocalDate.from(fixed.plus(1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(fixed.plus(35, DAYS)));

        // Only subtract days when the date is late enough that going back 60 days
        // stays within the supported range (day 60 of year 1).
        if (LocalDate.ofYearDay(1, 60).isBefore(iso)) {
            assertEquals(iso.plusDays(-1), LocalDate.from(fixed.plus(-1, DAYS)));
            assertEquals(iso.plusDays(-60), LocalDate.from(fixed.plus(-60, DAYS)));
        }
    }
}

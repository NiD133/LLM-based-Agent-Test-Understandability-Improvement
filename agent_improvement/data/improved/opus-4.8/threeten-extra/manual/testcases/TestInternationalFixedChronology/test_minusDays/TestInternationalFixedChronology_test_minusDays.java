package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link InternationalFixedDate#minus(long, java.time.temporal.TemporalUnit)}
 * subtracts days correctly by checking that the result, converted to an ISO
 * {@link LocalDate}, matches the equivalent {@code LocalDate.minusDays(...)} call.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minusDays {

    /**
     * Each row pairs an International Fixed date with the ISO {@link LocalDate}
     * that represents the same point in time.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { InternationalFixedDate.of(1, 1, 1), LocalDate.of(1, 1, 1) },
            { InternationalFixedDate.of(1, 1, 2), LocalDate.of(1, 1, 2) },
            { InternationalFixedDate.of(1, 6, 27), LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28), LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1), LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2), LocalDate.of(1, 6, 19) },
            { InternationalFixedDate.of(1, 13, 28), LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 27), LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 29), LocalDate.of(1, 12, 31) },
            { InternationalFixedDate.of(2, 1, 1), LocalDate.of(2, 1, 1) },
            { InternationalFixedDate.of(4, 6, 27), LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28), LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29), LocalDate.of(4, 6, 17) },
            { InternationalFixedDate.of(4, 7, 1), LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2), LocalDate.of(4, 6, 19) },
            { InternationalFixedDate.of(4, 13, 28), LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 27), LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 29), LocalDate.of(4, 12, 31) },
            { InternationalFixedDate.of(5, 1, 1), LocalDate.of(5, 1, 1) },
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1), LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2), LocalDate.of(100, 6, 19) },
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) },
            { InternationalFixedDate.of(400, 7, 1), LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2), LocalDate.of(400, 6, 19) },
            { InternationalFixedDate.of(1582, 9, 28), LocalDate.of(1582, 9, 9) },
            { InternationalFixedDate.of(1582, 10, 1), LocalDate.of(1582, 9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10, 6) },
            { InternationalFixedDate.of(2012, 6, 15), LocalDate.of(2012, 6, 3) },
            { InternationalFixedDate.of(2012, 6, 16), LocalDate.of(2012, 6, 4) }
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(InternationalFixedDate fixed, LocalDate iso) {
        // Subtracting zero days leaves the date unchanged.
        assertEquals(iso, LocalDate.from(fixed.minus(0, DAYS)));

        // Only subtract real days when the date is far enough into year 1 that
        // doing so cannot underflow before the start of the supported range.
        if (LocalDate.ofYearDay(1, 35).isBefore(iso)) {
            assertEquals(iso.minusDays(1), LocalDate.from(fixed.minus(1, DAYS)));
            assertEquals(iso.minusDays(35), LocalDate.from(fixed.minus(35, DAYS)));
        }

        // Subtracting a negative amount adds days, so it is always safe.
        assertEquals(iso.minusDays(-1), LocalDate.from(fixed.minus(-1, DAYS)));
        assertEquals(iso.minusDays(-60), LocalDate.from(fixed.minus(-60, DAYS)));
    }
}

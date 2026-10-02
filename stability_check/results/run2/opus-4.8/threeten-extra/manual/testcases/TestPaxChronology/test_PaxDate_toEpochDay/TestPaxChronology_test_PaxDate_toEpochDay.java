package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link PaxDate#toEpochDay()} produces the same epoch-day value
 * as the equivalent ISO {@link LocalDate}.
 *
 * <p>Each sample pairs a Pax date with the ISO date that falls on the exact same
 * day. Since two dates that denote the same day must share the same epoch day,
 * their {@code toEpochDay()} results are expected to be equal.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_PaxDate_toEpochDay {

    /**
     * Sample pairs of {@code {paxDate, equivalentIsoDate}} spanning positive years,
     * leap and non-leap Pax years (which have the extra 13th month "Pax"), the
     * year zero, and negative (BCE) years.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early days of Pax year 1.
            { PaxDate.of(1, 1, 1), LocalDate.of(0, 12, 31) },
            { PaxDate.of(1, 1, 2), LocalDate.of(1, 1, 1) },
            { PaxDate.of(1, 1, 3), LocalDate.of(1, 1, 2) },
            { PaxDate.of(1, 1, 28), LocalDate.of(1, 1, 27) },
            { PaxDate.of(1, 2, 1), LocalDate.of(1, 1, 28) },
            { PaxDate.of(1, 2, 2), LocalDate.of(1, 1, 29) },
            { PaxDate.of(1, 2, 3), LocalDate.of(1, 1, 30) },

            // Around the leap month of year 6 and the roll-over into year 7.
            { PaxDate.of(6, 13, 6), LocalDate.of(6, 12, 1) },
            { PaxDate.of(6, 13, 7), LocalDate.of(6, 12, 2) },
            { PaxDate.of(6, 14, 1), LocalDate.of(6, 12, 3) },
            { PaxDate.of(6, 14, 2), LocalDate.of(6, 12, 4) },
            { PaxDate.of(6, 14, 3), LocalDate.of(6, 12, 5) },
            { PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29) },
            { PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30) },
            { PaxDate.of(7, 1, 1), LocalDate.of(6, 12, 31) },
            { PaxDate.of(7, 1, 2), LocalDate.of(7, 1, 1) },

            // Century boundary (year 400 is a leap year) around years 399-401.
            { PaxDate.of(399, 13, 6), LocalDate.of(399, 12, 3) },
            { PaxDate.of(399, 13, 7), LocalDate.of(399, 12, 4) },
            { PaxDate.of(399, 14, 1), LocalDate.of(399, 12, 5) },
            { PaxDate.of(399, 14, 2), LocalDate.of(399, 12, 6) },
            { PaxDate.of(399, 14, 3), LocalDate.of(399, 12, 7) },
            { PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29) },
            { PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30) },
            { PaxDate.of(401, 1, 1), LocalDate.of(400, 12, 31) },
            { PaxDate.of(401, 1, 2), LocalDate.of(401, 1, 1) },
            { PaxDate.of(401, 1, 3), LocalDate.of(401, 1, 2) },

            // Year zero.
            { PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30) },
            { PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29) },

            // Historically notable dates.
            { PaxDate.of(1582, 10, 5), LocalDate.of(1582, 9, 9) },
            { PaxDate.of(1582, 10, 6), LocalDate.of(1582, 9, 10) },
            { PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10, 6) },
            { PaxDate.of(2012, 6, 23), LocalDate.of(2012, 6, 4) },
            { PaxDate.of(2012, 6, 24), LocalDate.of(2012, 6, 5) },

            // Negative (BCE) years, including the leap month of year -6.
            { PaxDate.of(-6, 1, 1), LocalDate.of(-6, 1, 2) },
            { PaxDate.of(-6, 13, 6), LocalDate.of(-6, 12, 9) },
            { PaxDate.of(-6, 13, 7), LocalDate.of(-6, 12, 10) },
            { PaxDate.of(-6, 14, 1), LocalDate.of(-6, 12, 11) },
            { PaxDate.of(-6, 14, 2), LocalDate.of(-6, 12, 12) },
            { PaxDate.of(-6, 14, 27), LocalDate.of(-5, 1, 6) },
            { PaxDate.of(-6, 14, 28), LocalDate.of(-5, 1, 7) },
            { PaxDate.of(-5, 1, 1), LocalDate.of(-5, 1, 8) },
            { PaxDate.of(-5, 1, 2), LocalDate.of(-5, 1, 9) },
            { PaxDate.of(-99, 1, 1), LocalDate.of(-99, 1, 6) },
            { PaxDate.of(-99, 13, 6), LocalDate.of(-99, 12, 13) },
            { PaxDate.of(-99, 13, 7), LocalDate.of(-99, 12, 14) },
            { PaxDate.of(-99, 14, 1), LocalDate.of(-99, 12, 15) },
            { PaxDate.of(-99, 14, 2), LocalDate.of(-99, 12, 16) },
            { PaxDate.of(-100, 1, 1), LocalDate.of(-101, 12, 31) },
            { PaxDate.of(-100, 13, 6), LocalDate.of(-100, 12, 7) },
            { PaxDate.of(-100, 13, 7), LocalDate.of(-100, 12, 8) },
            { PaxDate.of(-100, 14, 1), LocalDate.of(-100, 12, 9) },
            { PaxDate.of(-100, 14, 2), LocalDate.of(-100, 12, 10) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_PaxDate_toEpochDay(PaxDate pax, LocalDate iso) {
        assertEquals(iso.toEpochDay(), pax.toEpochDay());
    }
}

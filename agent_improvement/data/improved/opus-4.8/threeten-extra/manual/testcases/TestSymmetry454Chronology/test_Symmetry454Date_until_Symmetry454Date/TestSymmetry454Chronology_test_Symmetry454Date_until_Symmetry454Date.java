package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Symmetry454Date_until_Symmetry454Date {

    /**
     * A spread of Symmetry454 dates used to verify the behaviour for a variety
     * of years, months and days (including 35-day months and year boundaries).
     */
    public static Symmetry454Date[] sampleDates() {
        return new Symmetry454Date[] {
            Symmetry454Date.of(1, 1, 1),
            Symmetry454Date.of(272, 2, 30),
            Symmetry454Date.of(272, 2, 27),
            Symmetry454Date.of(742, 3, 25),
            Symmetry454Date.of(742, 4, 2),
            Symmetry454Date.of(1066, 10, 14),
            Symmetry454Date.of(1304, 7, 21),
            Symmetry454Date.of(1304, 7, 20),
            Symmetry454Date.of(1433, 11, 14),
            Symmetry454Date.of(1433, 11, 10),
            Symmetry454Date.of(1452, 4, 11),
            Symmetry454Date.of(1452, 4, 15),
            Symmetry454Date.of(1492, 10, 10),
            Symmetry454Date.of(1492, 10, 12),
            Symmetry454Date.of(1564, 2, 20),
            Symmetry454Date.of(1564, 2, 15),
            Symmetry454Date.of(1564, 4, 28),
            Symmetry454Date.of(1564, 4, 26),
            Symmetry454Date.of(1643, 1, 7),
            Symmetry454Date.of(1643, 1, 4),
            Symmetry454Date.of(1707, 4, 12),
            Symmetry454Date.of(1707, 4, 15),
            Symmetry454Date.of(1789, 7, 16),
            Symmetry454Date.of(1789, 7, 14),
            Symmetry454Date.of(1879, 3, 12),
            Symmetry454Date.of(1879, 3, 14),
            Symmetry454Date.of(1941, 9, 9),
            Symmetry454Date.of(1970, 1, 4),
            Symmetry454Date.of(1970, 1, 1),
            Symmetry454Date.of(1999, 12, 27),
            Symmetry454Date.of(2000, 1, 1)
        };
    }

    /**
     * The period between a date and itself must always be zero,
     * regardless of which date is chosen.
     */
    @ParameterizedTest
    @MethodSource("sampleDates")
    public void until_sameDate_returnsZeroPeriod(Symmetry454Date date) {
        assertEquals(Symmetry454Chronology.INSTANCE.period(0, 0, 0), date.until(date));
    }
}

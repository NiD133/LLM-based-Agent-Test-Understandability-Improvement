package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the Discordian calendar reports leap years in sync with the ISO calendar.
 * <p>
 * The Discordian year is the ISO year offset by {@link #ISO_OFFSET}. A Discordian
 * proleptic-year is therefore a leap year exactly when the corresponding ISO year is:
 * every 4th year, except century years that are not divisible by 400.
 */
public class TestDiscordianChronology_test_isLeapYear_loop {

    /** Offset between a Discordian proleptic-year and the underlying ISO year (YOLD 1 == ISO BCE 1166). */
    private static final int ISO_OFFSET = 1166;

    /** Reference implementation of the ISO leap-year rule, applied to the offset (ISO) year. */
    private static final IntPredicate EXPECTED_IS_LEAP_YEAR = year -> {
        int isoYear = year - ISO_OFFSET;
        return isoYear % 4 == 0 && (isoYear % 400 == 0 || isoYear % 100 != 0);
    };

    @Test
    public void test_isLeapYear_loop() {
        // Sweep a range of years that includes a century non-leap year (year 1266 -> ISO 100)
        // and a 400-divisible leap year, so all branches of the leap-year rule are exercised.
        for (int year = 1066; year < 1567; year++) {
            boolean expected = EXPECTED_IS_LEAP_YEAR.test(year);

            // The leap-year status must agree both on the date instance and on the chronology.
            DiscordianDate dateInYear = DiscordianDate.of(year, 1, 1);
            assertEquals(expected, dateInYear.isLeapYear());
            assertEquals(expected, DiscordianChronology.INSTANCE.isLeapYear(year));
        }
    }
}

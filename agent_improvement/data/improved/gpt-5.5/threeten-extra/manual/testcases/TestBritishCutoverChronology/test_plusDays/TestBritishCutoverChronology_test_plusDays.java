package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_plusDays {

    private static final int[] TESTED_DAY_OFFSETS = {0, 1, 35, -1, -60};

    public static Object[][] data_samples() {
        return new Object[][] {
            // Proleptic Julian dates around the AD 1 start.
            sample(1, 1, 1, 0, 12, 30),
            sample(1, 1, 2, 0, 12, 31),
            sample(1, 1, 3, 1, 1, 1),
            sample(1, 2, 28, 1, 2, 26),
            sample(1, 3, 1, 1, 2, 27),
            sample(1, 3, 2, 1, 2, 28),
            sample(1, 3, 3, 1, 3, 1),

            // Julian leap-year behavior before the Gregorian cutover.
            sample(4, 2, 28, 4, 2, 26),
            sample(4, 2, 29, 4, 2, 27),
            sample(4, 3, 1, 4, 2, 28),
            sample(4, 3, 2, 4, 2, 29),
            sample(4, 3, 3, 4, 3, 1),
            sample(100, 2, 28, 100, 2, 26),
            sample(100, 2, 29, 100, 2, 27),
            sample(100, 3, 1, 100, 2, 28),
            sample(100, 3, 2, 100, 3, 1),
            sample(100, 3, 3, 100, 3, 2),
            sample(0, 12, 31, 0, 12, 29),
            sample(0, 12, 30, 0, 12, 28),

            // Gregorian reform dates before Britain adopted the cutover.
            sample(1582, 10, 4, 1582, 10, 14),
            sample(1582, 10, 5, 1582, 10, 15),
            sample(1751, 12, 20, 1751, 12, 31),
            sample(1751, 12, 31, 1752, 1, 11),
            sample(1752, 1, 1, 1752, 1, 12),

            // British September 1752 cutover, including lenient gap dates.
            sample(1752, 9, 1, 1752, 9, 12),
            sample(1752, 9, 2, 1752, 9, 13),
            sample(1752, 9, 3, 1752, 9, 14),
            sample(1752, 9, 13, 1752, 9, 24),
            sample(1752, 9, 14, 1752, 9, 14),

            // Ordinary Gregorian dates after the cutover.
            sample(1945, 11, 12, 1945, 11, 12),
            sample(2012, 7, 5, 2012, 7, 5),
            sample(2012, 7, 6, 2012, 7, 6)
        };
    }

    private static Object[] sample(
            int cutoverYear,
            int cutoverMonth,
            int cutoverDay,
            int isoYear,
            int isoMonth,
            int isoDay) {

        return new Object[] {
            BritishCutoverDate.of(cutoverYear, cutoverMonth, cutoverDay),
            LocalDate.of(isoYear, isoMonth, isoDay)
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(BritishCutoverDate cutover, LocalDate iso) {
        for (int daysToAdd : TESTED_DAY_OFFSETS) {
            assertPlusDaysMatchesIso(cutover, iso, daysToAdd);
        }
    }

    private static void assertPlusDaysMatchesIso(BritishCutoverDate cutover, LocalDate iso, int daysToAdd) {
        assertEquals(iso.plusDays(daysToAdd), LocalDate.from(cutover.plus(daysToAdd, DAYS)));
    }
}

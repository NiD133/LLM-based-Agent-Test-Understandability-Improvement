package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link AccountingChronology#date(int, int, int)} rejects
 * year/month/day combinations that do not denote a valid date.
 *
 * <p>The chronology under test uses the "13 even months of 4 weeks" division,
 * so a regular month always has 28 days, while the 13th (leap-week) month has
 * 28 days in a common year and 35 days in a leap year (here, 2012).
 */
public class TestAccountingChronology_test_badDates {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Each row is an invalid {year, month, dayOfMonth} triple, grouped by the
     * reason it is out of range.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // Month outside the valid range 1..13.
            { 2012, 0, 0 },
            { 2012, -1, 1 },
            { 2012, 0, 1 },
            { 2012, 14, 1 },
            { 2012, 15, 1 },

            // Regular month (28 days): day must be 1..28.
            { 2012, 1, -1 },
            { 2012, 1, 0 },
            { 2012, 1, 29 },

            // Leap year (2012), month 13 has 35 days: day must be 1..35.
            { 2012, 13, -1 },
            { 2012, 13, 0 },
            { 2012, 13, 36 },
            { 2012, 13, 37 },
            { 2012, 13, 38 },

            // Common year (2011), month 13 has only 28 days: day must be 1..28.
            { 2011, 13, -1 },
            { 2011, 13, 0 },
            { 2011, 13, 29 },
            { 2011, 13, 30 },
            { 2011, 13, 31 },
            { 2011, 13, 32 },
            { 2011, 13, 33 },
            { 2011, 13, 34 },
            { 2011, 13, 35 },

            // Day 29 is never valid in any of the 28-day regular months.
            { 2012, 2, 29 },
            { 2012, 3, 29 },
            { 2012, 4, 29 },
            { 2012, 5, 29 },
            { 2012, 6, 29 },
            { 2012, 7, 29 },
            { 2012, 8, 29 },
            { 2012, 9, 29 },
            { 2012, 10, 29 },
            { 2012, 11, 29 },
            { 2012, 12, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dayOfMonth) {
        assertThrows(DateTimeException.class, () -> INSTANCE.date(year, month, dayOfMonth));
    }
}

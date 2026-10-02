package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link AccountingDate#until} correctly counts the number of days
 * between an AccountingDate and an ISO LocalDate, using data_samples() which
 * pairs each AccountingDate with its equivalent ISO date.
 */
public class TestAccountingChronology_test_until_DAYS {

    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    /**
     * Pairs of (AccountingDate, equivalent ISO LocalDate) used to verify day arithmetic.
     * Each row maps an Accounting calendar date to the ISO date it falls on.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { INSTANCE.date(1,    1,  1),  LocalDate.of(   0,  9,  4) },
            { INSTANCE.date(1,    1,  2),  LocalDate.of(   0,  9,  5) },
            { INSTANCE.date(1,    1,  3),  LocalDate.of(   0,  9,  6) },
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011,  8, 28) },
            { INSTANCE.date(2012,  1,  1), LocalDate.of(2011,  8, 29) },
            { INSTANCE.date(2012,  1,  2), LocalDate.of(2011,  8, 30) },
            { INSTANCE.date(2012,  1,  3), LocalDate.of(2011,  8, 31) },
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012,  8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012,  8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012,  8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012,  8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012,  8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012,  8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012,  9,  1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012,  9,  2) },
            { INSTANCE.date(2013,  1,  1), LocalDate.of(2012,  9,  3) },
            { INSTANCE.date(2013,  1,  2), LocalDate.of(2012,  9,  4) },
            { INSTANCE.date(2013,  1,  3), LocalDate.of(2012,  9,  5) },
            { INSTANCE.date(0,   13, 35),  LocalDate.of(   0,  9,  3) },
            { INSTANCE.date(0,   13, 34),  LocalDate.of(   0,  9,  2) },
            { INSTANCE.date(1583,  2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583,  2, 19), LocalDate.of(1582, 10, 15) },
            { INSTANCE.date(1946,  3, 15), LocalDate.of(1945, 11, 12) },
            { INSTANCE.date(2012, 12,  4), LocalDate.of(2012,  7,  5) },
            { INSTANCE.date(2012, 12,  5), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that {@code accounting.until(target, DAYS)} returns the expected day count
     * for targets that are 0, +1, +35, and -40 days from the ISO equivalent of
     * the given AccountingDate.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(AccountingDate accounting, LocalDate isoEquivalent) {
        assertEquals( 0,  accounting.until(isoEquivalent.plusDays(0),  DAYS));
        assertEquals( 1,  accounting.until(isoEquivalent.plusDays(1),  DAYS));
        assertEquals(35,  accounting.until(isoEquivalent.plusDays(35), DAYS));
        assertEquals(-40, accounting.until(isoEquivalent.minusDays(40), DAYS));
    }
}

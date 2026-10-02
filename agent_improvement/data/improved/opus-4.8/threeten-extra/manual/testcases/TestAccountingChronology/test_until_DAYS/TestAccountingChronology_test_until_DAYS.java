package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link AccountingDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * with the {@link java.time.temporal.ChronoUnit#DAYS DAYS} unit.
 *
 * <p>Each accounting date in {@code data_samples} is paired with the ISO date that falls on the
 * same day. The test then asks how many days separate the accounting date from several ISO dates
 * offset from that equivalent, confirming the day count matches the offset.
 */
public class TestAccountingChronology_test_until_DAYS {

    /** Accounting calendar ending on the Sunday nearest the end of August, with the leap week in month 13. */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Pairs of equivalent dates: an accounting date and the ISO date for the same day.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { INSTANCE.date(1, 1, 1), LocalDate.of(0, 9, 4) },
            { INSTANCE.date(1, 1, 2), LocalDate.of(0, 9, 5) },
            { INSTANCE.date(1, 1, 3), LocalDate.of(0, 9, 6) },
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            { INSTANCE.date(2012, 1, 1), LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012, 1, 2), LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012, 1, 3), LocalDate.of(2011, 8, 31) },
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2) },
            { INSTANCE.date(2013, 1, 1), LocalDate.of(2012, 9, 3) },
            { INSTANCE.date(2013, 1, 2), LocalDate.of(2012, 9, 4) },
            { INSTANCE.date(2013, 1, 3), LocalDate.of(2012, 9, 5) },
            { INSTANCE.date(0, 13, 35), LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34), LocalDate.of(0, 9, 2) },
            { INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },
            { INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },
            { INSTANCE.date(2012, 12, 4), LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5), LocalDate.of(2012, 7, 6) }
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(AccountingDate accounting, LocalDate iso) {
        // The accounting date equals the ISO date, so the day count to an offset ISO date is that offset.
        assertEquals(0, accounting.until(iso.plusDays(0), DAYS));
        assertEquals(1, accounting.until(iso.plusDays(1), DAYS));
        assertEquals(35, accounting.until(iso.plusDays(35), DAYS));
        assertEquals(-40, accounting.until(iso.minusDays(40), DAYS));
    }
}

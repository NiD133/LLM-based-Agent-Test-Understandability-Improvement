package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingDate#with(java.time.temporal.TemporalAdjuster)} correctly
 * applies a standard {@link TemporalAdjusters} adjuster to an Accounting calendar date.
 *
 * <p>The chronology used throughout: ends on SUNDAY nearest the end of AUGUST,
 * divided into 13 equal 4-week periods, with the leap week appended to period 13.
 */
public class TestAccountingChronology_test_adjust1 {

    /**
     * A 13-period accounting calendar that ends on the Sunday nearest the end of August,
     * with each regular period lasting 4 weeks (28 days) and the leap week in period 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Verifies that applying {@code lastDayOfMonth()} to a date in a standard 28-day period
     * moves the date to day 28 of that same period.
     *
     * <p>Period 6 of accounting year 2012 is a regular (non-leap) period with 28 days,
     * so the last day of the period is day 28.
     */
    @Test
    public void test_adjust1() {
        AccountingDate base = INSTANCE.date(2012, 6, 23);
        AccountingDate adjusted = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(INSTANCE.date(2012, 6, 28), adjusted);
    }
}

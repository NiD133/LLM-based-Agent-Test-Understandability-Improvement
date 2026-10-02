package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting a {@link java.time.chrono.ChronoPeriod} from an
 * {@link AccountingDate} correctly rolls the date back by whole years, months
 * and days.
 */
public class TestAccountingChronology_test_minus_Period {

    /**
     * A 13-month accounting calendar whose year ends on the Sunday nearest to
     * the end of August, with the leap week placed in month 13.
     */
    private static final AccountingChronology CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_minus_Period() {
        // Subtracting 0 years, 2 months and 3 days from 2014-05-26 yields 2014-03-23.
        AccountingDate start = CHRONOLOGY.date(2014, 5, 26);
        AccountingDate expected = CHRONOLOGY.date(2014, 3, 23);

        assertEquals(expected, start.minus(CHRONOLOGY.period(0, 2, 3)));
    }
}

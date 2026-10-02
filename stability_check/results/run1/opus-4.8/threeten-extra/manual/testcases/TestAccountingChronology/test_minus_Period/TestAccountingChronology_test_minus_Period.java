package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests subtracting a {@link org.threeten.extra.chrono.AccountingChronology} period
 * from an accounting date via {@code AccountingDate.minus(Period)}.
 */
public class TestAccountingChronology_test_minus_Period {

    /**
     * Accounting calendar whose year ends on the Sunday nearest the end of August
     * and is divided into thirteen 4-week months, with the leap week in month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_minus_Period() {
        // Subtracting 2 months and 3 days from 2014-05-26 yields 2014-03-23.
        AccountingDate startDate = INSTANCE.date(2014, 5, 26);
        AccountingDate expectedDate = INSTANCE.date(2014, 3, 23);

        AccountingDate actualDate = startDate.minus(INSTANCE.period(0, 2, 3));

        assertEquals(expectedDate, actualDate);
    }
}

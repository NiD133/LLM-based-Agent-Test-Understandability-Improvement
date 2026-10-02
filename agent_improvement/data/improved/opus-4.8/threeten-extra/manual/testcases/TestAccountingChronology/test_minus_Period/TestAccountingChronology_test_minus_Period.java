package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests subtracting a period from an {@link AccountingDate} via {@code minus(Period)}.
 */
public class TestAccountingChronology_test_minus_Period {

    /**
     * A 13-month accounting calendar whose year ends on the Sunday nearest the end of August,
     * with the leap week placed in month 13.
     */
    private static final AccountingChronology CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_minus_Period() {
        // Subtracting 2 months and 3 days from 2014-05-26 yields 2014-03-23.
        assertEquals(
                CHRONOLOGY.date(2014, 3, 23),
                CHRONOLOGY.date(2014, 5, 26).minus(CHRONOLOGY.period(0, 2, 3)));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that the Accounting calendar system has no registered calendar type
 * (unlike, for example, the Japanese or Hijrah calendars, the accounting
 * calendar is configurable and therefore exposes no single CLDR identifier).
 */
public class TestAccountingChronology_test_chronology_of_name_id {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_chronology_of_name_id() {
        assertNull(INSTANCE.getCalendarType());
    }
}

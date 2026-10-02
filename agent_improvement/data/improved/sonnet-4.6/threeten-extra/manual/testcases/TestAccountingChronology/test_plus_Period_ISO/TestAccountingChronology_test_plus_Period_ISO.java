package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_plus_Period_ISO {

    // Calendar ending on Sunday nearest end of August, split into 13 equal 4-week months,
    // with the leap week appended to month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // ISO Period carries ISO-specific month semantics and is rejected by non-ISO chronologies.
    @Test
    public void test_plus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> INSTANCE.date(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}

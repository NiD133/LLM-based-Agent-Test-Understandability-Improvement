package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_minus_Period_ISO {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_minus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> INSTANCE.date(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}

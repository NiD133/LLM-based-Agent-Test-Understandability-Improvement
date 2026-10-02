package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_adjust_toMonth {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_adjust_toMonth() {
        AccountingDate accounting = INSTANCE.date(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> accounting.with(Month.APRIL));
    }
}

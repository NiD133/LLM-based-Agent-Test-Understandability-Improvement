package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that an {@link AccountingDate} cannot be adjusted with a plain
 * {@link Month}, because the accounting calendar has 13 months and therefore
 * does not understand the ISO 12-month {@code Month} enum as an adjuster.
 */
public class TestAccountingChronology_test_adjust_toMonth {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_adjust_toMonth() {
        AccountingDate accountingDate = ACCOUNTING_CHRONOLOGY.date(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> accountingDate.with(Month.APRIL));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting an ISO {@link Period} from an accounting date is rejected.
 */
public class TestAccountingChronology_test_minus_Period_ISO {

    /**
     * An accounting calendar whose year ends on the Sunday nearest the end of August,
     * is divided into thirteen 4-week months, and places the leap week in month 13.
     */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_minus_Period_ISO() {
        // An ISO Period (months) is incompatible with the accounting calendar, so subtracting it must fail.
        assertThrows(DateTimeException.class,
                () -> ACCOUNTING_CHRONOLOGY.date(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}

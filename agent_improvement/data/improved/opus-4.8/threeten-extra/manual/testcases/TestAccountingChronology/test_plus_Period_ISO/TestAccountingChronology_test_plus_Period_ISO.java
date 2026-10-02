package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding an ISO {@link Period} to an accounting-calendar date is rejected.
 *
 * <p>An accounting year is divided into months that do not line up with ISO calendar
 * months, so mixing an ISO {@code Period} (expressed in ISO years/months/days) into
 * accounting-date arithmetic is unsupported and must fail fast.
 */
public class TestAccountingChronology_test_plus_Period_ISO {

    /**
     * Accounting calendar that ends on the Sunday nearest the end of August, divides the
     * year into thirteen 4-week months, and places the leap week in month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_plus_Period_ISO() {
        // Adding an ISO Period (here, 2 ISO months) to an accounting date is unsupported.
        assertThrows(DateTimeException.class,
                () -> INSTANCE.date(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}

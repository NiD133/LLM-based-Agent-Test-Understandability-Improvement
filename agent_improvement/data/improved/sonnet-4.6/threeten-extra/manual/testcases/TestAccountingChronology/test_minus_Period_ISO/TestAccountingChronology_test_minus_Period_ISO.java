package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting an ISO {@link Period} from an {@link AccountingDate} throws
 * {@link DateTimeException}, because ISO month lengths are incompatible with the
 * Accounting calendar's fixed 4-week (28-day) months.
 */
public class TestAccountingChronology_test_minus_Period_ISO {

    /**
     * Accounting chronology: year ends on Sunday nearest end of August,
     * divided into 13 even months of 4 weeks, with the leap week in month 13.
     */
    private static final AccountingChronology CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_minus_Period_ISO() {
        // ISO Period.ofMonths() cannot be applied to an AccountingDate because ISO months
        // do not correspond to Accounting months; the chronology rejects the operation.
        assertThrows(
                DateTimeException.class,
                () -> CHRONOLOGY.date(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingChronology#eraOf(int)} rejects an out-of-range era value.
 */
public class TestAccountingChronology_test_Chronology_eraOf_invalid {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_Chronology_eraOf_invalid() {
        // Only era values 0 (BCE) and 1 (CE) are valid; 2 is out of range.
        assertThrows(DateTimeException.class, () -> INSTANCE.eraOf(2));
    }
}

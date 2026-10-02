package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingChronology#eraOf(int)} rejects era values
 * outside the valid range [0, 1] (BCE=0, CE=1).
 */
public class TestAccountingChronology_test_Chronology_eraOf_invalid {

    // A fully configured chronology instance used to invoke eraOf().
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_Chronology_eraOf_invalid() {
        // Only 0 (BCE) and 1 (CE) are valid; 2 must throw DateTimeException.
        assertThrows(DateTimeException.class, () -> INSTANCE.eraOf(2));
    }
}

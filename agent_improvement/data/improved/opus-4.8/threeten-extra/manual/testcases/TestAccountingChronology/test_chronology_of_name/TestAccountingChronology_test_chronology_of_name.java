package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the {@link AccountingChronology} reports the expected chronology id.
 */
public class TestAccountingChronology_test_chronology_of_name {

    /** A representative AccountingChronology instance used by the test below. */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_chronology_of_name() {
        assertEquals("Accounting", ACCOUNTING_CHRONOLOGY.getId());
    }
}

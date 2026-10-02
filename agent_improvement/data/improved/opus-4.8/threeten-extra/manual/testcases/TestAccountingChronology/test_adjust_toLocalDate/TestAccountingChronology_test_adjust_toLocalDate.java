package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that an {@link AccountingDate} can be adjusted to match a given ISO
 * {@link LocalDate} via {@code with(TemporalAdjuster)}.
 */
public class TestAccountingChronology_test_adjust_toLocalDate {

    /**
     * Accounting calendar that ends on the Sunday nearest the end of August,
     * splits the year into thirteen 4-week months, and places the leap week in
     * month 13.
     */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_adjust_toLocalDate() {
        // Start from an arbitrary accounting date.
        AccountingDate startDate = ACCOUNTING_CHRONOLOGY.date(2000, 1, 4);

        // Adjusting it to an ISO date should yield the equivalent accounting date.
        AccountingDate adjusted = startDate.with(LocalDate.of(2012, 7, 6));

        AccountingDate expected = ACCOUNTING_CHRONOLOGY.date(2012, 12, 5);
        assertEquals(expected, adjusted);
    }
}

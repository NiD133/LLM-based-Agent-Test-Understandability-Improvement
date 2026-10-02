package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_prolepticYear_specific {

    // A 13-month accounting calendar ending on the nearest Sunday to end of August,
    // with the leap week placed in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // CE years map directly to positive proleptic years (CE 1 → 1, CE 2 → 2, ...).
    // BCE years map to non-positive proleptic years (BCE 1 → 0, BCE 2 → -1, ...).
    @Test
    public void test_prolepticYear_specific() {
        assertEquals(4, INSTANCE.prolepticYear(AccountingEra.CE, 4));
        assertEquals(3, INSTANCE.prolepticYear(AccountingEra.CE, 3));
        assertEquals(2, INSTANCE.prolepticYear(AccountingEra.CE, 2));
        assertEquals(1, INSTANCE.prolepticYear(AccountingEra.CE, 1));
        assertEquals(0, INSTANCE.prolepticYear(AccountingEra.BCE, 1));
        assertEquals(-1, INSTANCE.prolepticYear(AccountingEra.BCE, 2));
        assertEquals(-2, INSTANCE.prolepticYear(AccountingEra.BCE, 3));
        assertEquals(-3, INSTANCE.prolepticYear(AccountingEra.BCE, 4));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_prolepticYear_specific {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

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

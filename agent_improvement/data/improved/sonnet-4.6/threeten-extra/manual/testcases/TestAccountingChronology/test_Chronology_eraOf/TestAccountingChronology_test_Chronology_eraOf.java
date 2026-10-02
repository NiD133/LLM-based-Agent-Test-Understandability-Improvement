package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_Chronology_eraOf {

    // A standard 13-month accounting calendar ending on the Sunday nearest the end of August,
    // with the leap week placed in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_Chronology_eraOf() {
        // Era value 1 maps to the Current Era (CE)
        assertEquals(AccountingEra.CE, INSTANCE.eraOf(1));
        // Era value 0 maps to Before Current Era (BCE)
        assertEquals(AccountingEra.BCE, INSTANCE.eraOf(0));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.Era;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_Chronology_eras {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = INSTANCE.eras();

        assertEquals(2, eras.size());
        assertTrue(eras.contains(AccountingEra.BCE));
        assertTrue(eras.contains(AccountingEra.CE));
    }
}

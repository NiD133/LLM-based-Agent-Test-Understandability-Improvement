package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_prolepticYear_badEra {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_prolepticYear_badEra() {
        assertThrows(
                ClassCastException.class,
                () -> ACCOUNTING_CHRONOLOGY.prolepticYear(IsoEra.CE, 4));
    }
}

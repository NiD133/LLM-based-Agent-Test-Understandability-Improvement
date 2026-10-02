package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingChronology#prolepticYear(java.time.chrono.Era, int)}
 * rejects an era that is not an {@link AccountingEra}.
 */
public class TestAccountingChronology_test_prolepticYear_badEra {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_prolepticYear_badEra() {
        // IsoEra is not an AccountingEra, so prolepticYear must reject it with a ClassCastException.
        assertThrows(ClassCastException.class, () -> INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}

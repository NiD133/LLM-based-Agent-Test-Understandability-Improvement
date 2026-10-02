package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link AccountingDate#toEpochDay()} returns the same epoch day
 * as the equivalent ISO {@link LocalDate} it maps to.
 */
public class TestAccountingChronology_test_AccountingDate_toEpochDay {

    /**
     * Accounting calendar whose years end on the SUNDAY nearest the end of August,
     * are split into thirteen even 4-week months, and carry the leap week in month 13.
     */
    private static final AccountingChronology CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Pairs of (accounting date, equivalent ISO date) that must share an epoch day.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { CHRONOLOGY.date(1, 1, 1), LocalDate.of(0, 9, 4) },
            { CHRONOLOGY.date(1, 1, 2), LocalDate.of(0, 9, 5) },
            { CHRONOLOGY.date(1, 1, 3), LocalDate.of(0, 9, 6) },
            { CHRONOLOGY.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            { CHRONOLOGY.date(2012, 1, 1), LocalDate.of(2011, 8, 29) },
            { CHRONOLOGY.date(2012, 1, 2), LocalDate.of(2011, 8, 30) },
            { CHRONOLOGY.date(2012, 1, 3), LocalDate.of(2011, 8, 31) },
            { CHRONOLOGY.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { CHRONOLOGY.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { CHRONOLOGY.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { CHRONOLOGY.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { CHRONOLOGY.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { CHRONOLOGY.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { CHRONOLOGY.date(2012, 13, 34), LocalDate.of(2012, 9, 1) },
            { CHRONOLOGY.date(2012, 13, 35), LocalDate.of(2012, 9, 2) },
            { CHRONOLOGY.date(2013, 1, 1), LocalDate.of(2012, 9, 3) },
            { CHRONOLOGY.date(2013, 1, 2), LocalDate.of(2012, 9, 4) },
            { CHRONOLOGY.date(2013, 1, 3), LocalDate.of(2012, 9, 5) },
            { CHRONOLOGY.date(0, 13, 35), LocalDate.of(0, 9, 3) },
            { CHRONOLOGY.date(0, 13, 34), LocalDate.of(0, 9, 2) },
            { CHRONOLOGY.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { CHRONOLOGY.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },
            { CHRONOLOGY.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },
            { CHRONOLOGY.date(2012, 12, 4), LocalDate.of(2012, 7, 5) },
            { CHRONOLOGY.date(2012, 12, 5), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_AccountingDate_toEpochDay(AccountingDate accounting, LocalDate iso) {
        assertEquals(iso.toEpochDay(), accounting.toEpochDay());
    }
}

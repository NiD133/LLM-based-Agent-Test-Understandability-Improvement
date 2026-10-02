package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link AccountingDate#from(AccountingChronology, java.time.temporal.TemporalAccessor)}
 * maps an ISO {@link LocalDate} to the matching {@link AccountingDate}.
 */
public class TestAccountingChronology_test_AccountingDate_from_LocalDate {

    /**
     * Accounting calendar whose year ends on the SUNDAY nearest the end of August,
     * is split into thirteen even 4-week months, and carries its leap week in month 13.
     */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Pairs of {@code {expectedAccountingDate, isoDate}} that fall on the same calendar day.
     * Each accounting date is built as {@code (year, month, dayOfMonth)} in the chronology above.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { ACCOUNTING_CHRONOLOGY.date(1, 1, 1),       LocalDate.of(0, 9, 4) },
            { ACCOUNTING_CHRONOLOGY.date(1, 1, 2),       LocalDate.of(0, 9, 5) },
            { ACCOUNTING_CHRONOLOGY.date(1, 1, 3),       LocalDate.of(0, 9, 6) },
            { ACCOUNTING_CHRONOLOGY.date(2011, 13, 28),  LocalDate.of(2011, 8, 28) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 1, 1),    LocalDate.of(2011, 8, 29) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 1, 2),    LocalDate.of(2011, 8, 30) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 1, 3),    LocalDate.of(2011, 8, 31) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 28),  LocalDate.of(2012, 8, 26) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 29),  LocalDate.of(2012, 8, 27) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 30),  LocalDate.of(2012, 8, 28) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 31),  LocalDate.of(2012, 8, 29) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 32),  LocalDate.of(2012, 8, 30) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 33),  LocalDate.of(2012, 8, 31) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 34),  LocalDate.of(2012, 9, 1) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 35),  LocalDate.of(2012, 9, 2) },
            { ACCOUNTING_CHRONOLOGY.date(2013, 1, 1),    LocalDate.of(2012, 9, 3) },
            { ACCOUNTING_CHRONOLOGY.date(2013, 1, 2),    LocalDate.of(2012, 9, 4) },
            { ACCOUNTING_CHRONOLOGY.date(2013, 1, 3),    LocalDate.of(2012, 9, 5) },
            { ACCOUNTING_CHRONOLOGY.date(0, 13, 35),     LocalDate.of(0, 9, 3) },
            { ACCOUNTING_CHRONOLOGY.date(0, 13, 34),     LocalDate.of(0, 9, 2) },
            { ACCOUNTING_CHRONOLOGY.date(1583, 2, 18),   LocalDate.of(1582, 10, 14) },
            { ACCOUNTING_CHRONOLOGY.date(1583, 2, 19),   LocalDate.of(1582, 10, 15) },
            { ACCOUNTING_CHRONOLOGY.date(1946, 3, 15),   LocalDate.of(1945, 11, 12) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 12, 4),   LocalDate.of(2012, 7, 5) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 12, 5),   LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_AccountingDate_from_LocalDate(AccountingDate expectedAccountingDate, LocalDate isoDate) {
        assertEquals(expectedAccountingDate, AccountingDate.from(ACCOUNTING_CHRONOLOGY, isoDate));
    }
}

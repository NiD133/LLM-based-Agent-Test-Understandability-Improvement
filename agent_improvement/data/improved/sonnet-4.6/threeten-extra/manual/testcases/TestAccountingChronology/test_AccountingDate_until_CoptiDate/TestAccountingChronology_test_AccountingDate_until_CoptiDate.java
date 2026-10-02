package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_AccountingDate_until_CoptiDate {

    // A 52/53-week accounting calendar: ends on Sunday nearest end of August,
    // divided into 13 equal 4-week months, with the leap week appended to month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // A representative sample of AccountingDate values paired with their ISO equivalents.
    public static Object[][] data_samples() {
        return new Object[][] {
            { INSTANCE.date(1, 1, 1),    java.time.LocalDate.of(0,    9,  4) },
            { INSTANCE.date(1, 1, 2),    java.time.LocalDate.of(0,    9,  5) },
            { INSTANCE.date(1, 1, 3),    java.time.LocalDate.of(0,    9,  6) },
            { INSTANCE.date(2011, 13, 28), java.time.LocalDate.of(2011, 8, 28) },
            { INSTANCE.date(2012, 1, 1),  java.time.LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012, 1, 2),  java.time.LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012, 1, 3),  java.time.LocalDate.of(2011, 8, 31) },
            { INSTANCE.date(2012, 13, 28), java.time.LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), java.time.LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), java.time.LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), java.time.LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), java.time.LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), java.time.LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), java.time.LocalDate.of(2012, 9,  1) },
            { INSTANCE.date(2012, 13, 35), java.time.LocalDate.of(2012, 9,  2) },
            { INSTANCE.date(2013, 1, 1),  java.time.LocalDate.of(2012, 9,  3) },
            { INSTANCE.date(2013, 1, 2),  java.time.LocalDate.of(2012, 9,  4) },
            { INSTANCE.date(2013, 1, 3),  java.time.LocalDate.of(2012, 9,  5) },
            { INSTANCE.date(0, 13, 35),  java.time.LocalDate.of(0,    9,  3) },
            { INSTANCE.date(0, 13, 34),  java.time.LocalDate.of(0,    9,  2) },
            { INSTANCE.date(1583, 2, 18), java.time.LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19), java.time.LocalDate.of(1582, 10, 15) },
            { INSTANCE.date(1946, 3, 15), java.time.LocalDate.of(1945, 11, 12) },
            { INSTANCE.date(2012, 12, 4), java.time.LocalDate.of(2012, 7,  5) },
            { INSTANCE.date(2012, 12, 5), java.time.LocalDate.of(2012, 7,  6) },
        };
    }

    /**
     * Verifies that the period from any AccountingDate to itself is zero
     * (i.e., {@code date.until(date)} returns a zero-length period).
     * The {@code iso} parameter carries the corresponding ISO date for each sample
     * but is not needed by this assertion.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_AccountingDate_until_CoptiDate(AccountingDate accounting, java.time.LocalDate iso) {
        assertEquals(INSTANCE.period(0, 0, 0), accounting.until(accounting));
    }
}

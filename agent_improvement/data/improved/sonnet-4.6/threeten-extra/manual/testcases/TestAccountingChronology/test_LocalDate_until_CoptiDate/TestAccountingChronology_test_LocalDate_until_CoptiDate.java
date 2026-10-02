package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link LocalDate#until(java.time.temporal.TemporalAmount)} returns {@link Period#ZERO}
 * when the target is an {@link AccountingDate} representing the same calendar day.
 *
 * <p>The chronology under test ends on SUNDAY nearest the end of AUGUST,
 * divides the year into 13 even months of 4 weeks, with the leap-week in month 13.
 */
public class TestAccountingChronology_test_LocalDate_until_CoptiDate {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    //-----------------------------------------------------------------------
    /**
     * Pairs of (AccountingDate, ISO LocalDate) that represent the same point in time.
     * Each pair is used to verify that {@code localDate.until(accountingDate)} is zero.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { INSTANCE.date(1, 1, 1),      LocalDate.of(0, 9, 4) },
            { INSTANCE.date(1, 1, 2),      LocalDate.of(0, 9, 5) },
            { INSTANCE.date(1, 1, 3),      LocalDate.of(0, 9, 6) },
            // Last days of accounting year 2011
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            // First days of accounting year 2012
            { INSTANCE.date(2012, 1, 1),   LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012, 1, 2),   LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012, 1, 3),   LocalDate.of(2011, 8, 31) },
            // Leap-week days in month 13 of accounting year 2012 (leap year with 35-day month 13)
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2) },
            // First days of accounting year 2013
            { INSTANCE.date(2013, 1, 1),   LocalDate.of(2012, 9, 3) },
            { INSTANCE.date(2013, 1, 2),   LocalDate.of(2012, 9, 4) },
            { INSTANCE.date(2013, 1, 3),   LocalDate.of(2012, 9, 5) },
            // Dates in BCE (proleptic year 0)
            { INSTANCE.date(0, 13, 35),    LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34),    LocalDate.of(0, 9, 2) },
            // Around the Gregorian calendar reform boundary (Oct 1582)
            { INSTANCE.date(1583, 2, 18),  LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19),  LocalDate.of(1582, 10, 15) },
            // A mid-year date in 1946
            { INSTANCE.date(1946, 3, 15),  LocalDate.of(1945, 11, 12) },
            // End of month 12 in 2012
            { INSTANCE.date(2012, 12, 4),  LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5),  LocalDate.of(2012, 7, 6) },
        };
    }

    //-----------------------------------------------------------------------
    /**
     * Verifies that computing the period from an ISO {@link LocalDate} to the
     * equivalent {@link AccountingDate} yields {@link Period#ZERO}.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_CoptiDate(AccountingDate accounting, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(accounting));
    }
}

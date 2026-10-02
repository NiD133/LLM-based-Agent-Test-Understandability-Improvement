package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link AccountingDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero-length period when an accounting date is measured against itself.
 *
 * <p>The accounting calendar under test ends on the Sunday nearest the end of August and
 * splits each year into thirteen even four-week months, with the leap week placed in month 13.
 */
public class TestAccountingChronology_test_AccountingDate_until_CoptiDate {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Sample accounting dates paired with their equivalent ISO date. Only the accounting date is
     * exercised by this test; the ISO date documents the correspondence and keeps the sample set
     * identical to the shared chronology test data.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { INSTANCE.date(1, 1, 1), LocalDate.of(0, 9, 4) },
            { INSTANCE.date(1, 1, 2), LocalDate.of(0, 9, 5) },
            { INSTANCE.date(1, 1, 3), LocalDate.of(0, 9, 6) },
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            { INSTANCE.date(2012, 1, 1), LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012, 1, 2), LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012, 1, 3), LocalDate.of(2011, 8, 31) },
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2) },
            { INSTANCE.date(2013, 1, 1), LocalDate.of(2012, 9, 3) },
            { INSTANCE.date(2013, 1, 2), LocalDate.of(2012, 9, 4) },
            { INSTANCE.date(2013, 1, 3), LocalDate.of(2012, 9, 5) },
            { INSTANCE.date(0, 13, 35), LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34), LocalDate.of(0, 9, 2) },
            { INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },
            { INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },
            { INSTANCE.date(2012, 12, 4), LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_AccountingDate_until_CoptiDate(AccountingDate accounting, LocalDate iso) {
        assertEquals(INSTANCE.period(0, 0, 0), accounting.until(accounting));
    }
}

package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that AccountingDate.toEpochDay() returns the same epoch-day as the
 * equivalent ISO LocalDate for a 13-month (4-week) accounting chronology that
 * ends on the Sunday nearest the end of August.
 */
public class TestAccountingChronology_test_AccountingDate_toEpochDay {

    // Chronology: ends on Sunday nearest end-of-August, 13 x 4-week months, leap-week in month 13
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    /**
     * Pairs of (AccountingDate, equivalent ISO LocalDate) used to verify toEpochDay().
     * The accounting year ends on the Sunday nearest the end of August, so year
     * boundaries do not align with the ISO calendar.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Start of accounting year 1 (CE) ---
            { INSTANCE.date(1, 1, 1),   LocalDate.of(0,    9,  4) },
            { INSTANCE.date(1, 1, 2),   LocalDate.of(0,    9,  5) },
            { INSTANCE.date(1, 1, 3),   LocalDate.of(0,    9,  6) },

            // --- End of non-leap year 2011 and start of leap year 2012 ---
            // 2011 is a non-leap year (month 13 has only 28 days)
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            // 2012 is a leap year (month 13 has 35 days); accounting year starts in ISO 2011
            { INSTANCE.date(2012,  1,  1), LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012,  1,  2), LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012,  1,  3), LocalDate.of(2011, 8, 31) },

            // --- Leap-week days in month 13 of accounting year 2012 ---
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012,  9,  1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012,  9,  2) },

            // --- Start of accounting year 2013 (immediately after leap year 2012) ---
            { INSTANCE.date(2013, 1, 1), LocalDate.of(2012, 9,  3) },
            { INSTANCE.date(2013, 1, 2), LocalDate.of(2012, 9,  4) },
            { INSTANCE.date(2013, 1, 3), LocalDate.of(2012, 9,  5) },

            // --- BCE years (proleptic year 0 = 1 BCE) ---
            { INSTANCE.date(0, 13, 35), LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34), LocalDate.of(0, 9, 2) },

            // --- Gregorian calendar reform boundary (Oct 1582) ---
            { INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },

            // --- Mid-20th-century date ---
            { INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },

            // --- Mid-year dates within month 12 of 2012 ---
            { INSTANCE.date(2012, 12, 4), LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_AccountingDate_toEpochDay(AccountingDate accounting, LocalDate iso) {
        assertEquals(iso.toEpochDay(), accounting.toEpochDay());
    }
}

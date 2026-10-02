package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that converting an AccountingDate to a LocalDate (ISO) produces the correct result.
 *
 * The chronology under test ends on SUNDAY nearest the end of AUGUST, divides the year into
 * THIRTEEN_EVEN_MONTHS_OF_4_WEEKS, and places the leap week in month 13.
 */
public class TestAccountingChronology_test_LocalDate_from_AccountingDate {

    // Accounting chronology: ends on Sunday nearest end of August,
    // 13 equal months of 4 weeks each, leap week appended to month 13.
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    /**
     * Pairs of (AccountingDate, expected ISO LocalDate) used to verify the conversion.
     *
     * Each row represents one scenario:
     * - Accounting year 1 starts just after the end of ISO year 0 (= proleptic year 0), so the
     *   first accounting day maps to ISO 0000-09-04.
     * - Leap years (e.g. 2012) produce a 35-day month 13 instead of the usual 28.
     * - Non-leap years (e.g. 2011) only have 28 days in month 13.
     * - Historical boundary around the Gregorian calendar switch (1582) is included.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Accounting year 1: first few days map into ISO year 0 ---
            { INSTANCE.date(1, 1, 1),   LocalDate.of(0, 9, 4) },
            { INSTANCE.date(1, 1, 2),   LocalDate.of(0, 9, 5) },
            { INSTANCE.date(1, 1, 3),   LocalDate.of(0, 9, 6) },

            // --- End of non-leap accounting year 2011 (month 13 has 28 days) ---
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },

            // --- Start of accounting year 2012 (year ends in ISO 2011-08) ---
            { INSTANCE.date(2012, 1, 1),  LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012, 1, 2),  LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012, 1, 3),  LocalDate.of(2011, 8, 31) },

            // --- End of leap accounting year 2012 (month 13 has 35 days) ---
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2) },

            // --- Start of accounting year 2013 ---
            { INSTANCE.date(2013, 1, 1),  LocalDate.of(2012, 9, 3) },
            { INSTANCE.date(2013, 1, 2),  LocalDate.of(2012, 9, 4) },
            { INSTANCE.date(2013, 1, 3),  LocalDate.of(2012, 9, 5) },

            // --- Proleptic year 0 (BCE): last two days of year 0 ---
            { INSTANCE.date(0, 13, 35),  LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34),  LocalDate.of(0, 9, 2) },

            // --- Gregorian calendar switchover boundary (Oct 1582) ---
            { INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },

            // --- Spot-check mid-century date ---
            { INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },

            // --- Two adjacent days mid-year in 2012 ---
            { INSTANCE.date(2012, 12, 4),  LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5),  LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_AccountingDate(AccountingDate accounting, LocalDate iso) {
        assertEquals(iso, LocalDate.from(accounting));
    }
}

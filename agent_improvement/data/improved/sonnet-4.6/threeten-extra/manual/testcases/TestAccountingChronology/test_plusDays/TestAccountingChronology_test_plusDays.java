package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_plusDays {

    // A 13-month accounting calendar ending on the Sunday nearest the end of August,
    // with 13 even months of 4 weeks and the leap week placed in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // Offsets used in test_plusDays to exercise various boundary conditions.
    // 0 verifies the base date itself; +1 and -1 test single-day movement;
    // +35 crosses a full 4-week accounting month; -60 reaches back roughly two months.
    private static final int OFFSET_SAME_DAY    =  0;
    private static final int OFFSET_NEXT_DAY    =  1;
    private static final int OFFSET_FULL_MONTH  = 35;
    private static final int OFFSET_PREV_DAY    = -1;
    private static final int OFFSET_TWO_MONTHS  = -60;

    // -----------------------------------------------------------------------
    // Each row: (AccountingDate, equivalent ISO LocalDate)
    // -----------------------------------------------------------------------
    public static Object[][] data_samples() {
        return new Object[][] {
            // Start of proleptic year 1
            { INSTANCE.date(1,    1,  1),  LocalDate.of(   0,  9,  4) },
            { INSTANCE.date(1,    1,  2),  LocalDate.of(   0,  9,  5) },
            { INSTANCE.date(1,    1,  3),  LocalDate.of(   0,  9,  6) },

            // Transition from accounting year 2011 to 2012
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011,  8, 28) },
            { INSTANCE.date(2012,  1,  1), LocalDate.of(2011,  8, 29) },
            { INSTANCE.date(2012,  1,  2), LocalDate.of(2011,  8, 30) },
            { INSTANCE.date(2012,  1,  3), LocalDate.of(2011,  8, 31) },

            // Leap month 13 of 2012 (35 days because 2012 is a leap year)
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012,  8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012,  8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012,  8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012,  8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012,  8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012,  8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012,  9,  1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012,  9,  2) },

            // Transition from accounting year 2012 to 2013
            { INSTANCE.date(2013,  1,  1), LocalDate.of(2012,  9,  3) },
            { INSTANCE.date(2013,  1,  2), LocalDate.of(2012,  9,  4) },
            { INSTANCE.date(2013,  1,  3), LocalDate.of(2012,  9,  5) },

            // Proleptic year 0 (BCE era): end-of-year days counting backwards
            { INSTANCE.date(0,   13, 35), LocalDate.of(   0,  9,  3) },
            { INSTANCE.date(0,   13, 34), LocalDate.of(   0,  9,  2) },

            // Around the Gregorian calendar reform (Oct 1582)
            { INSTANCE.date(1583,  2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583,  2, 19), LocalDate.of(1582, 10, 15) },

            // Mid-year spot check
            { INSTANCE.date(1946,  3, 15), LocalDate.of(1945, 11, 12) },

            // Month 12 of a leap year
            { INSTANCE.date(2012, 12,  4), LocalDate.of(2012,  7,  5) },
            { INSTANCE.date(2012, 12,  5), LocalDate.of(2012,  7,  6) },
        };
    }

    // -----------------------------------------------------------------------
    // For each (accountingDate, isoDate) pair, verify that adding days via
    // AccountingDate.plus(n, DAYS) produces the same ISO date as
    // LocalDate.plusDays(n). This exercises both forward and backward
    // arithmetic, including multi-month jumps.
    // -----------------------------------------------------------------------
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(AccountingDate accounting, LocalDate iso) {
        assertEquals(iso.plusDays(OFFSET_SAME_DAY),   LocalDate.from(accounting.plus(OFFSET_SAME_DAY,   DAYS)));
        assertEquals(iso.plusDays(OFFSET_NEXT_DAY),   LocalDate.from(accounting.plus(OFFSET_NEXT_DAY,   DAYS)));
        assertEquals(iso.plusDays(OFFSET_FULL_MONTH),  LocalDate.from(accounting.plus(OFFSET_FULL_MONTH,  DAYS)));
        assertEquals(iso.plusDays(OFFSET_PREV_DAY),   LocalDate.from(accounting.plus(OFFSET_PREV_DAY,   DAYS)));
        assertEquals(iso.plusDays(OFFSET_TWO_MONTHS), LocalDate.from(accounting.plus(OFFSET_TWO_MONTHS, DAYS)));
    }
}

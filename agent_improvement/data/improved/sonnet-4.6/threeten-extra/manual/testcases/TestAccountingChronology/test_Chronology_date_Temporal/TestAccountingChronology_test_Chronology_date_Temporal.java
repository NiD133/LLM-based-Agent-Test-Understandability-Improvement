package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link AccountingChronology#date(java.time.temporal.TemporalAccessor)} correctly
 * converts an ISO {@link LocalDate} to its equivalent {@link AccountingDate} for a specific
 * chronology configuration: ends on SUNDAY nearest end of AUGUST, divided into 13 even months
 * of 4 weeks each, with the leap week placed in month 13.
 */
public class TestAccountingChronology_test_Chronology_date_Temporal {

    // Chronology under test: ends on SUNDAY nearest end of AUGUST,
    // 13 even months of 4 weeks, leap week in month 13, ending in given ISO year.
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    // Pairs of (AccountingDate, equivalent ISO LocalDate) used to verify round-trip conversion.
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early dates around year 1
            { INSTANCE.date(1, 1, 1),   LocalDate.of(0, 9, 4) },
            { INSTANCE.date(1, 1, 2),   LocalDate.of(0, 9, 5) },
            { INSTANCE.date(1, 1, 3),   LocalDate.of(0, 9, 6) },
            // Standard (non-leap) year boundary: 2011 ends, 2012 begins
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            { INSTANCE.date(2012, 1, 1),   LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012, 1, 2),   LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012, 1, 3),   LocalDate.of(2011, 8, 31) },
            // Leap year 2012: month 13 has 35 days instead of 28
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1)  },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2)  },
            // Start of accounting year 2013
            { INSTANCE.date(2013, 1, 1),   LocalDate.of(2012, 9, 3) },
            { INSTANCE.date(2013, 1, 2),   LocalDate.of(2012, 9, 4) },
            { INSTANCE.date(2013, 1, 3),   LocalDate.of(2012, 9, 5) },
            // Dates in year 0 (proleptic BCE)
            { INSTANCE.date(0, 13, 35),    LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34),    LocalDate.of(0, 9, 2) },
            // Dates spanning the Gregorian calendar reform (1582)
            { INSTANCE.date(1583, 2, 18),  LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19),  LocalDate.of(1582, 10, 15) },
            // A historical mid-century date
            { INSTANCE.date(1946, 3, 15),  LocalDate.of(1945, 11, 12) },
            // Two consecutive days in month 12 of leap year 2012
            { INSTANCE.date(2012, 12, 4),  LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5),  LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(AccountingDate accounting, LocalDate iso) {
        assertEquals(accounting, INSTANCE.date(iso));
    }
}

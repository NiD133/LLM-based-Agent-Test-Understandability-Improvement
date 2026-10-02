package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link AccountingDate#from(AccountingChronology, java.time.temporal.TemporalAccessor)}
 * correctly converts ISO {@link LocalDate} values to their equivalent {@link AccountingDate}
 * in an accounting calendar that ends on the nearest Sunday to the end of August,
 * divided into 13 equal months of 4 weeks, with the leap week placed in month 13.
 */
public class TestAccountingChronology_test_AccountingDate_from_LocalDate {

    /** Accounting chronology under test: ends on nearest Sunday to end of August,
     *  13 months of 4 weeks each, leap week in month 13, year ending in the given ISO year. */
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    /**
     * Pairs of (AccountingDate, ISO LocalDate) that should be equivalent.
     * Covers: year 1 (epoch start), year/month boundaries around 2011–2013,
     * the 2012 leap year (month 13 has 35 days), year 0, the 1582 calendar
     * reform boundary, and a mid-year check for 1946 and 2012.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 – first three days map to early September ISO year 0
            { INSTANCE.date(1, 1, 1),  LocalDate.of(0, 9, 4) },
            { INSTANCE.date(1, 1, 2),  LocalDate.of(0, 9, 5) },
            { INSTANCE.date(1, 1, 3),  LocalDate.of(0, 9, 6) },

            // End of accounting year 2011 / start of 2012
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            { INSTANCE.date(2012,  1,  1), LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012,  1,  2), LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012,  1,  3), LocalDate.of(2011, 8, 31) },

            // 2012 is a leap year – month 13 has 35 days (normal 28 + 7 leap)
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012,  9,  1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012,  9,  2) },

            // Start of accounting year 2013
            { INSTANCE.date(2013, 1, 1), LocalDate.of(2012, 9, 3) },
            { INSTANCE.date(2013, 1, 2), LocalDate.of(2012, 9, 4) },
            { INSTANCE.date(2013, 1, 3), LocalDate.of(2012, 9, 5) },

            // Year 0 (proleptic BCE) – last two days of month 13
            { INSTANCE.date(0, 13, 35), LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34), LocalDate.of(0, 9, 2) },

            // Gregorian calendar reform boundary (Oct 1582)
            { INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },

            // Mid-year spot check: accounting year 1946, month 3
            { INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },

            // Mid-year spot check: accounting year 2012, month 12
            { INSTANCE.date(2012, 12, 4), LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_AccountingDate_from_LocalDate(AccountingDate accounting, LocalDate iso) {
        assertEquals(accounting, AccountingDate.from(INSTANCE, iso));
    }
}

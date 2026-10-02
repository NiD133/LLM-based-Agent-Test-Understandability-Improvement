package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link AccountingDate#getLong(TemporalField)} for various ChronoFields
 * using a 13-month accounting calendar that ends on Sunday nearest end of August.
 */
public class TestAccountingChronology_test_getLong {

    // 13-month calendar: each month has 4 weeks (28 days), leap week appended to month 13.
    // Year ends on the Sunday nearest end of August.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // Accounting year 2014, month 5, day 26:
    //   DAY_OF_YEAR  = 4 full months * 28 days + 26 days into month 5 = 138
    //   PROLEPTIC_MONTH = prolepticYear * 13 + (monthOfYear - 1)
    private static final int DAYS_PER_STANDARD_MONTH = 28;
    private static final int MONTHS_PER_YEAR = 13;

    public static Object[][] data_getLong() {
        int year = 2014, month = 5, day = 26;
        int dayOfYear = (month - 1) * DAYS_PER_STANDARD_MONTH + day; // 4*28 + 26 = 138
        long prolepticMonth = (long) year * MONTHS_PER_YEAR + (month - 1);

        return new Object[][] {
            // { year, month, day, field, expectedValue }

            // Day-of-week: day 26 in month 5 of 2014 falls on a Friday (ISO day 5)
            { year, month, day, DAY_OF_WEEK,                   5L },

            // Day-of-month: straightforward positional value within the month
            { year, month, day, DAY_OF_MONTH,                  26L },

            // Day-of-year: months 1–4 each have 28 days, then 26 days into month 5
            { year, month, day, DAY_OF_YEAR,                   (long) dayOfYear },

            // Aligned day of week within the current 7-day week block inside the month
            { year, month, day, ALIGNED_DAY_OF_WEEK_IN_MONTH,  5L },

            // Aligned week number within the month (week 4 of 4 in a 28-day month)
            { year, month, day, ALIGNED_WEEK_OF_MONTH,         4L },

            // Aligned day of week within the current 7-day week block across the year
            { year, month, day, ALIGNED_DAY_OF_WEEK_IN_YEAR,   5L },

            // Aligned week of year: 19 complete weeks before this week + current = week 20
            { year, month, day, ALIGNED_WEEK_OF_YEAR,          20L },

            // Month of year: month index within the accounting year
            { year, month, day, MONTH_OF_YEAR,                 5L },

            // Proleptic month: absolute month count from the epoch
            { year, month, day, PROLEPTIC_MONTH,               prolepticMonth },

            // Year: the proleptic year value
            { year, month, day, YEAR,                          2014L },

            // ERA: CE years (year > 0) map to era value 1
            { year, month, day, ERA,                           1L },
            { 1,    6,     8,   ERA,                           1L },

            // ERA: year 0 (BCE) maps to era value 0
            { 0,    6,     8,   ERA,                           0L },

            // Non-ChronoField temporal field: ISO WeekFields day-of-week (Friday = 5)
            { year, month, day, WeekFields.ISO.dayOfWeek(),    5L },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, INSTANCE.date(year, month, dom).getLong(field));
    }
}

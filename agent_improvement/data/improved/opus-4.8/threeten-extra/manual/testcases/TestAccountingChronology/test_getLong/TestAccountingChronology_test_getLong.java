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
 * Tests {@link AccountingChronology#date(int, int, int)}'s {@code getLong(TemporalField)}.
 *
 * <p>The chronology under test is an accounting calendar whose year:
 * <ul>
 *   <li>ends on the SUNDAY nearest the end of AUGUST,</li>
 *   <li>is divided into 13 even months of 4 weeks each, and</li>
 *   <li>places the leap week (when present) in month 13.</li>
 * </ul>
 */
public class TestAccountingChronology_test_getLong {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Each case is: accounting (year, month, dayOfMonth), the field to query, and the
     * expected {@code getLong} result for that field on that date.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // Date 2014-05-26 queried across every supported field.
            { 2014, 5, 26, DAY_OF_WEEK,                 5L },
            { 2014, 5, 26, DAY_OF_MONTH,                26L },
            // Four full 28-day months precede month 5, plus 26 days into month 5.
            { 2014, 5, 26, DAY_OF_YEAR,                 (long) (28 + 28 + 28 + 28 + 26) },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5L },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,        4L },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  5L },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,         20L },
            { 2014, 5, 26, MONTH_OF_YEAR,                5L },
            // Proleptic month: 13 months per year, zero-based month index.
            { 2014, 5, 26, PROLEPTIC_MONTH,             (long) (2014 * 13 + 5 - 1) },
            { 2014, 5, 26, YEAR,                         2014L },
            { 2014, 5, 26, ERA,                          1L },

            // ERA depends on the year: positive proleptic year -> CE (1), year 0 -> BCE (0).
            { 1, 6, 8, ERA, 1L },
            { 0, 6, 8, ERA, 0L },

            // A custom (non-ChronoField) field is also supported.
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(),  5L },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, INSTANCE.date(year, month, dom).getLong(field));
    }
}

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
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_with_TemporalField {

    /**
     * Test data for {@link #test_with_TemporalField}.
     * Columns: startYear, startMonth, startDay, field, newValue, expectedYear, expectedMonth, expectedDay
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // DAY_OF_WEEK: adjusts day within the current week
            { 2014, 5, 26, DAY_OF_WEEK,                    3, 2014,  5, 22 },
            { 2014, 5, 26, DAY_OF_WEEK,                    7, 2014,  5, 26 },  // no-op: already Sunday

            // DAY_OF_MONTH: sets the day within the current month
            { 2014, 5, 26, DAY_OF_MONTH,                  31, 2014,  5, 31 },
            { 2014, 5, 26, DAY_OF_MONTH,                  26, 2014,  5, 26 },  // no-op

            // DAY_OF_YEAR: sets the day within the current year
            { 2014, 5, 26, DAY_OF_YEAR,                  365, 2014, 12, 31 },
            { 2014, 5, 26, DAY_OF_YEAR,                  146, 2014,  5, 26 },  // no-op

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: day position within the aligned week of the month
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,   3, 2014,  5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,   5, 2014,  5, 26 },  // no-op

            // ALIGNED_WEEK_OF_MONTH: which aligned week within the month
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,          1, 2014,  5,  5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,          4, 2014,  5, 26 },  // no-op

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: day position within the aligned week of the year
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,    2, 2014,  5, 22 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,    6, 2014,  5, 26 },  // no-op

            // ALIGNED_WEEK_OF_YEAR: which aligned week within the year
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,          23, 2014,  6,  9 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,          21, 2014,  5, 26 },  // no-op

            // MONTH_OF_YEAR: changes the month, clamping day if needed
            { 2014, 5, 26, MONTH_OF_YEAR,                  7, 2014,  7, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR,                  5, 2014,  5, 26 },  // no-op
            { 2011, 3, 31, MONTH_OF_YEAR,                  2, 2011,  2, 28 },  // clamp to Feb 28 (non-leap)
            { 2012, 3, 31, MONTH_OF_YEAR,                  2, 2012,  2, 29 },  // clamp to Feb 29 (leap)
            { 2012, 3, 31, MONTH_OF_YEAR,                  6, 2012,  6, 30 },  // clamp to Jun 30

            // PROLEPTIC_MONTH: sets month as absolute month index from epoch
            { 2014, 5, 26, PROLEPTIC_MONTH,   2013 * 12 + 3 - 1, 2013,  3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH,   2014 * 12 + 5 - 1, 2014,  5, 26 },  // no-op

            // YEAR: changes the proleptic year, clamping day if Feb 29 in non-leap target
            { 2014, 5, 26, YEAR,                        2012, 2012,  5, 26 },
            { 2014, 5, 26, YEAR,                        2014, 2014,  5, 26 },  // no-op
            { 2012, 2, 29, YEAR,                        2011, 2011,  2, 28 },  // clamp leap day

            // YEAR_OF_ERA: changes the year within the current era
            { 2014, 5, 26, YEAR_OF_ERA,                 2012, 2012,  5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA,                 2014, 2014,  5, 26 },  // no-op
            { -2013, 6,  8, YEAR_OF_ERA,                2012, -2011,  6,  8 },  // BC era

            // ERA: flip between AD (1) and BC (0)
            { 2014, 5, 26, ERA,                            0, -2013,  5, 26 },  // AD -> BC
            { 2014, 5, 26, ERA,                            1,  2014,  5, 26 },  // no-op (already AD)

            // WeekFields.ISO.dayOfWeek(): ISO day-of-week (1=Mon .. 7=Sun)
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(),    3, 2014,  5, 22 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                JulianDate.of(expectedYear, expectedMonth, expectedDom),
                JulianDate.of(year, month, dom).with(field, value));
    }
}

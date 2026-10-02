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

import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_getLong {

    private static final int SAMPLE_YEAR = 2014;
    private static final int SAMPLE_MONTH = 5;
    private static final int SAMPLE_DAY = 26;
    private static final int DAYS_BEFORE_SAMPLE_MONTH = 28 + 28 + 28 + 28;
    private static final long SAMPLE_PROLEPTIC_MONTH =
            SAMPLE_YEAR * 13 + 20 * 18 - 5 + 2 + SAMPLE_MONTH - 1;

    public static Object[][] data_getLong() {
        return new Object[][] {
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, DAY_OF_WEEK, 4 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, DAY_OF_MONTH, 26 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, DAY_OF_YEAR, DAYS_BEFORE_SAMPLE_MONTH + SAMPLE_DAY },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, ALIGNED_WEEK_OF_MONTH, 4 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, ALIGNED_WEEK_OF_YEAR, 20 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, MONTH_OF_YEAR, 5 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, PROLEPTIC_MONTH, SAMPLE_PROLEPTIC_MONTH },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, YEAR, 2014 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, ERA, 1 },
                { 1, 6, 8, ERA, 1 },
                { 0, 6, 8, ERA, 0 },
                { SAMPLE_YEAR, SAMPLE_MONTH, SAMPLE_DAY, WeekFields.ISO.dayOfWeek(), 4 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dayOfMonth, TemporalField field, long expected) {
        assertEquals(expected, PaxDate.of(year, month, dayOfMonth).getLong(field));
    }
}

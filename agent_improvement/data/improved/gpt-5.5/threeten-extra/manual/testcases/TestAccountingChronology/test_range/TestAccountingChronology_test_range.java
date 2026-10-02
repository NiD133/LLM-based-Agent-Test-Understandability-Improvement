package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_range {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    public static Object[][] data_ranges() {
        return new Object[][] {
                {2012, 1, 23, DAY_OF_MONTH, 1, 28},
                {2012, 2, 23, DAY_OF_MONTH, 1, 28},
                {2012, 3, 23, DAY_OF_MONTH, 1, 28},
                {2012, 4, 23, DAY_OF_MONTH, 1, 28},
                {2012, 5, 23, DAY_OF_MONTH, 1, 28},
                {2012, 6, 23, DAY_OF_MONTH, 1, 28},
                {2012, 7, 23, DAY_OF_MONTH, 1, 28},
                {2012, 8, 23, DAY_OF_MONTH, 1, 28},
                {2012, 9, 23, DAY_OF_MONTH, 1, 28},
                {2012, 10, 23, DAY_OF_MONTH, 1, 28},
                {2012, 11, 23, DAY_OF_MONTH, 1, 28},
                {2012, 12, 23, DAY_OF_MONTH, 1, 28},
                {2012, 13, 23, DAY_OF_MONTH, 1, 35},
                {2012, 1, 23, DAY_OF_YEAR, 1, 371},
                {2012, 12, 23, ALIGNED_WEEK_OF_MONTH, 1, 4},
                {2012, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
                {2013, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 4},
                {2011, 13, 23, DAY_OF_MONTH, 1, 28},
                {2011, 13, 23, DAY_OF_YEAR, 1, 364},
                {2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 4},
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dayOfMonth, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), INSTANCE.date(year, month, dayOfMonth).range(field));
    }
}

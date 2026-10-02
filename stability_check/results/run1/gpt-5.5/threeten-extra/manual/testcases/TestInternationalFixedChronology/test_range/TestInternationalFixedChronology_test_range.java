package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_range {

    private static final ValueRange EMPTY_SPECIAL_DAY_RANGE = ValueRange.of(0, 0);
    private static final ValueRange STANDARD_WEEK_BASED_RANGE = ValueRange.of(1, 7);
    private static final ValueRange STANDARD_WEEK_OF_MONTH_RANGE = ValueRange.of(1, 4);
    private static final ValueRange STANDARD_WEEK_OF_YEAR_RANGE = ValueRange.of(1, 52);

    public static Object[][] data_ranges() {
        List<Object[]> ranges = new ArrayList<>();
        addDayOfMonthRanges(ranges);
        addDayOfYearAndMonthRanges(ranges);
        addAlignedDayOfWeekInMonthRanges(ranges);
        addAlignedWeekOfMonthRanges(ranges);
        addAlignedDayOfWeekInYearRanges(ranges);
        addAlignedWeekOfYearRanges(ranges);
        addDayOfWeekRanges(ranges);
        addCommonYearRanges(ranges);
        return ranges.toArray(new Object[0][]);
    }

    private static void addDayOfMonthRanges(List<Object[]> ranges) {
        add(ranges, 2012, 6, 29, DAY_OF_MONTH, ValueRange.of(1, 29));
        add(ranges, 2012, 13, 29, DAY_OF_MONTH, ValueRange.of(1, 29));
        add(ranges, 2012, 1, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 2, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 3, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 4, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 5, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 29));
        add(ranges, 2012, 7, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 8, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 9, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2012, 13, 23, DAY_OF_MONTH, ValueRange.of(1, 29));
    }

    private static void addDayOfYearAndMonthRanges(List<Object[]> ranges) {
        add(ranges, 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 366));
        add(ranges, 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 13));
    }

    private static void addAlignedDayOfWeekInMonthRanges(List<Object[]> ranges) {
        add(ranges, 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, STANDARD_WEEK_BASED_RANGE);
        add(ranges, 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, STANDARD_WEEK_BASED_RANGE);
        add(ranges, 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, STANDARD_WEEK_BASED_RANGE);
    }

    private static void addAlignedWeekOfMonthRanges(List<Object[]> ranges) {
        add(ranges, 2012, 6, 29, ALIGNED_WEEK_OF_MONTH, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 13, 29, ALIGNED_WEEK_OF_MONTH, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, STANDARD_WEEK_OF_MONTH_RANGE);
        add(ranges, 2012, 6, 23, ALIGNED_WEEK_OF_MONTH, STANDARD_WEEK_OF_MONTH_RANGE);
        add(ranges, 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, STANDARD_WEEK_OF_MONTH_RANGE);
    }

    private static void addAlignedDayOfWeekInYearRanges(List<Object[]> ranges) {
        add(ranges, 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, STANDARD_WEEK_BASED_RANGE);
        add(ranges, 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, STANDARD_WEEK_BASED_RANGE);
        add(ranges, 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, STANDARD_WEEK_BASED_RANGE);
    }

    private static void addAlignedWeekOfYearRanges(List<Object[]> ranges) {
        add(ranges, 2012, 6, 29, ALIGNED_WEEK_OF_YEAR, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 13, 29, ALIGNED_WEEK_OF_YEAR, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, STANDARD_WEEK_OF_YEAR_RANGE);
        add(ranges, 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, STANDARD_WEEK_OF_YEAR_RANGE);
        add(ranges, 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, STANDARD_WEEK_OF_YEAR_RANGE);
    }

    private static void addDayOfWeekRanges(List<Object[]> ranges) {
        add(ranges, 2012, 6, 29, DAY_OF_WEEK, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 13, 29, DAY_OF_WEEK, EMPTY_SPECIAL_DAY_RANGE);
        add(ranges, 2012, 1, 23, DAY_OF_WEEK, STANDARD_WEEK_BASED_RANGE);
        add(ranges, 2012, 6, 23, DAY_OF_WEEK, STANDARD_WEEK_BASED_RANGE);
        add(ranges, 2012, 12, 23, DAY_OF_WEEK, STANDARD_WEEK_BASED_RANGE);
    }

    private static void addCommonYearRanges(List<Object[]> ranges) {
        add(ranges, 2011, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 28));
        add(ranges, 2011, 13, 23, DAY_OF_YEAR, ValueRange.of(1, 365));
        add(ranges, 2011, 13, 23, MONTH_OF_YEAR, ValueRange.of(1, 13));
    }

    private static void add(
            List<Object[]> ranges,
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            ValueRange expectedRange) {

        ranges.add(new Object[] { year, month, dayOfMonth, field, expectedRange });
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange range) {
        assertEquals(range, InternationalFixedDate.of(year, month, dom).range(field));
    }
}

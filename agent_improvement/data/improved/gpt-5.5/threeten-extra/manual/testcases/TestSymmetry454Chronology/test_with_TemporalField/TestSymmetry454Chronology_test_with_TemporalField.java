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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_with_TemporalField {

    public static Object[][] data_with() {
        return new Object[][] {
                { 2014, 5, 26, DAY_OF_WEEK, 1, 2014, 5, 22 },
                { 2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 26 },
                { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 },
                { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },
                { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 12, 28 },
                { 2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 19 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 23 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26 },
                { 2014, 5, 26, MONTH_OF_YEAR, 4, 2014, 4, 26 },
                { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 },
                { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26 },
                { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26 },
                { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
                { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },
                { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
                { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },
                { 2014, 5, 26, ERA, 1, 2014, 5, 26 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2015, 12, 22 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2015, 12, 23 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2015, 12, 24 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2015, 12, 25 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2015, 12, 26 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 6, 2015, 12, 27 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7, 2015, 12, 28 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 2015, 12, 22 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2015, 12, 23 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2015, 12, 24 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, 2015, 12, 25 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2015, 12, 26 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2015, 12, 27 },
                { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7, 2015, 12, 28 },
                { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 0, 2015, 12, 29 },
                { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 3, 2015, 12, 15 },
                { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR, 0, 2015, 12, 29 },
                { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR, 3, 2015, 1, 15 },
                { 2015, 12, 29, DAY_OF_WEEK, 0, 2015, 12, 29 },
                { 2015, 12, 28, DAY_OF_WEEK, 1, 2015, 12, 22 },
                { 2015, 12, 28, DAY_OF_WEEK, 2, 2015, 12, 23 },
                { 2015, 12, 28, DAY_OF_WEEK, 3, 2015, 12, 24 },
                { 2015, 12, 28, DAY_OF_WEEK, 4, 2015, 12, 25 },
                { 2015, 12, 28, DAY_OF_WEEK, 5, 2015, 12, 26 },
                { 2015, 12, 28, DAY_OF_WEEK, 6, 2015, 12, 27 },
                { 2015, 12, 28, DAY_OF_WEEK, 7, 2015, 12, 28 },
                { 2015, 12, 29, DAY_OF_MONTH, 1, 2015, 12, 1 },
                { 2015, 12, 29, DAY_OF_MONTH, 3, 2015, 12, 3 },
                { 2015, 12, 29, MONTH_OF_YEAR, 1, 2015, 1, 28 },
                { 2015, 12, 29, MONTH_OF_YEAR, 12, 2015, 12, 29 },
                { 2015, 12, 29, MONTH_OF_YEAR, 2, 2015, 2, 29 },
                { 2015, 12, 29, YEAR, 2014, 2014, 12, 28 },
                { 2015, 12, 29, YEAR, 2013, 2013, 12, 28 },
                { 2014, 3, 28, DAY_OF_MONTH, 1, 2014, 3, 1 },
                { 2014, 1, 28, DAY_OF_MONTH, 1, 2014, 1, 1 },
                { 2014, 3, 28, MONTH_OF_YEAR, 1, 2014, 1, 28 },
                { 2015, 3, 28, DAY_OF_YEAR, 371, 2015, 12, 35 },
                { 2012, 3, 28, DAY_OF_YEAR, 364, 2012, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year,
            int month,
            int dom,
            TemporalField field,
            long value,
            int expectedYear,
            int expectedMonth,
            int expectedDom) {

        assertEquals(
                Symmetry454Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry454Date.of(year, month, dom).with(field, value));
    }
}

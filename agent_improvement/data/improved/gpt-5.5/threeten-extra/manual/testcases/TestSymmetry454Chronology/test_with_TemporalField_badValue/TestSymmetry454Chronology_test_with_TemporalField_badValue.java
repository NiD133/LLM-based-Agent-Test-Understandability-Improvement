package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_with_TemporalField_badValue {

    public static Object[][] data_with_bad() {
        return new Object[][] {
                { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, -1 },
                { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 8 },
                { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, -1 },
                { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 8 },
                { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, -1 },
                { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, 5 },
                { 2013, 2, 1, ALIGNED_WEEK_OF_MONTH, 6 },
                { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, -1 },
                { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 53 },
                { 2015, 1, 1, ALIGNED_WEEK_OF_YEAR, 54 },
                { 2013, 1, 1, DAY_OF_WEEK, -1 },
                { 2013, 1, 1, DAY_OF_WEEK, 8 },
                { 2013, 1, 1, DAY_OF_MONTH, -1 },
                { 2013, 1, 1, DAY_OF_MONTH, 29 },
                { 2013, 6, 1, DAY_OF_MONTH, 29 },
                { 2013, 12, 1, DAY_OF_MONTH, 30 },
                { 2015, 12, 1, DAY_OF_MONTH, 36 },
                { 2013, 1, 1, DAY_OF_YEAR, -1 },
                { 2013, 1, 1, DAY_OF_YEAR, 365 },
                { 2015, 1, 1, DAY_OF_YEAR, 372 },
                { 2013, 1, 1, MONTH_OF_YEAR, -1 },
                { 2013, 1, 1, MONTH_OF_YEAR, 14 },
                { 2013, 1, 1, MONTH_OF_YEAR, -2 },
                { 2013, 1, 1, MONTH_OF_YEAR, 14 },
                { 2013, 1, 1, EPOCH_DAY, -365_961_481 },
                { 2013, 1, 1, EPOCH_DAY, 364_523_156 },
                { 2013, 1, 1, YEAR, -1_000_001 },
                { 2013, 1, 1, YEAR, 1_000_001 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(year, month, dom).with(field, value));
    }
}

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

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_with_TemporalField {

    private static final int YEAR_2014 = 2014;
    private static final int MONTH_MAY = 5;
    private static final int DAY_26 = 26;

    public static Object[][] data_with() {
        return new Object[][] {
                sameDateWith(DAY_OF_WEEK, 3, 2014, 5, 25),
                sameDateWith(DAY_OF_WEEK, 4, 2014, 5, 26),
                sameDateWith(DAY_OF_MONTH, 28, 2014, 5, 28),
                sameDateWith(DAY_OF_MONTH, 26, 2014, 5, 26),
                sameDateWith(DAY_OF_YEAR, 364, 2014, 13, 28),
                sameDateWith(DAY_OF_YEAR, 138, 2014, 5, 26),
                sameDateWith(ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24),
                sameDateWith(ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26),
                sameDateWith(ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5),
                sameDateWith(ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26),
                sameDateWith(ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 24),
                sameDateWith(ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26),
                sameDateWith(ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19),
                sameDateWith(ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26),
                sameDateWith(MONTH_OF_YEAR, 7, 2014, 7, 26),
                sameDateWith(MONTH_OF_YEAR, 5, 2014, 5, 26),
                sameDateWith(PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 3 - 1, 2013, 3, 26),
                sameDateWith(PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 5 - 1, 2013, 5, 26),
                sameDateWith(YEAR, 2012, 2012, 5, 26),
                sameDateWith(YEAR, 2014, 2014, 5, 26),
                sameDateWith(YEAR_OF_ERA, 2012, 2012, 5, 26),
                sameDateWith(YEAR_OF_ERA, 2014, 2014, 5, 26),
                sameDateWith(ERA, 0, -2013, 5, 26),
                sameDateWith(ERA, 1, 2014, 5, 26),
                withField(2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28),
                withField(2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 7),
                withField(2012, 3, 28, MONTH_OF_YEAR, 6, 2012, 6, 28),
                withField(2012, 13, 7, YEAR, 2011, 2011, 13, 7),
                withField(-2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8),
                sameDateWith(WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 25),
        };
    }

    private static Object[] sameDateWith(
            TemporalField field,
            long value,
            int expectedYear,
            int expectedMonth,
            int expectedDom) {

        return withField(YEAR_2014, MONTH_MAY, DAY_26, field, value, expectedYear, expectedMonth, expectedDom);
    }

    private static Object[] withField(
            int year,
            int month,
            int dom,
            TemporalField field,
            long value,
            int expectedYear,
            int expectedMonth,
            int expectedDom) {

        return new Object[] { year, month, dom, field, value, expectedYear, expectedMonth, expectedDom };
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
                PaxDate.of(expectedYear, expectedMonth, expectedDom),
                PaxDate.of(year, month, dom).with(field, value));
    }
}

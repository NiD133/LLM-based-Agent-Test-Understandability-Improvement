package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link PaxDate#range(TemporalField)}.
 * <p>
 * For a given Pax date, {@code range(field)} reports the valid value range of a
 * temporal field <em>at that date</em>. The range depends on where the date sits
 * in the Pax calendar, e.g. the short leap month ('Pax', month 13 of a leap year)
 * only has 7 days, while ordinary months have 28.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_range {

    /**
     * Each case describes a Pax date and the value range expected for one field.
     *
     * @return rows of {@code (year, month, dayOfMonth, field, expectedMin, expectedMax)}
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: ordinary months span days 1..28 ...
            { 2012, 1, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 2, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 3, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 4, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 5, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 6, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 7, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 8, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 9, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 10, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 11, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 12, 23, DAY_OF_MONTH, 1, 28 },
            // ... but the inserted leap month (month 13 of leap year 2012) only spans days 1..7
            { 2012, 13, 3, DAY_OF_MONTH, 1, 7 },
            { 2012, 14, 23, DAY_OF_MONTH, 1, 28 },

            // MONTH_OF_YEAR: 14 months in a leap year, 13 otherwise
            { 2012, 1, 23, MONTH_OF_YEAR, 1, 14 },
            { 2011, 1, 23, MONTH_OF_YEAR, 1, 13 },

            // DAY_OF_YEAR: 371 days in a leap year, 364 otherwise
            { 2012, 1, 23, DAY_OF_YEAR, 1, 371 },
            { 2011, 13, 23, DAY_OF_YEAR, 1, 364 },

            // ALIGNED_WEEK_OF_MONTH: 4 weeks in ordinary months, 1 in the short leap month
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
            { 2012, 13, 3, ALIGNED_WEEK_OF_MONTH, 1, 1 },
            { 2012, 14, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },

            // a non-leap year (2011): month 13 is an ordinary 28-day month
            { 2011, 13, 23, DAY_OF_MONTH, 1, 28 },
            { 2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        ValueRange actualRange = PaxDate.of(year, month, dom).range(field);

        assertEquals(ValueRange.of(expectedMin, expectedMax), actualRange);
    }
}

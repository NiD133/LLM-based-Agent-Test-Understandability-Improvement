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

/**
 * Tests {@link PaxDate#getLong(TemporalField)}, i.e. reading the value of a
 * single temporal field from a Pax date.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_getLong {

    /**
     * Each case is: Pax date (year, month, day-of-month), the field to read,
     * and the value {@code getLong} is expected to return for that field.
     * <p>
     * The reference date {@code 2014-05-26 (Pax)} is reused for most fields so
     * the expected values can be compared against a single, known date.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // day-related fields on the reference date 2014-05-26 (Pax)
            { 2014, 5, 26, DAY_OF_WEEK, 4 },
            { 2014, 5, 26, DAY_OF_MONTH, 26 },
            // day-of-year: four full 28-day months precede month 5, plus day 26
            { 2014, 5, 26, DAY_OF_YEAR, 28 + 28 + 28 + 28 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20 },

            // month / year / era fields on the reference date
            { 2014, 5, 26, MONTH_OF_YEAR, 5 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 20 * 18 - 5 + 2 + 5 - 1 },
            { 2014, 5, 26, YEAR, 2014 },
            { 2014, 5, 26, ERA, 1 },

            // ERA distinguishes the Current Era (1) from the Before-Current Era (0)
            { 1, 6, 8, ERA, 1 },
            { 0, 6, 8, ERA, 0 },

            // a non-ChronoField field still resolves through the Pax date
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, PaxDate.of(year, month, dom).getLong(field));
    }
}

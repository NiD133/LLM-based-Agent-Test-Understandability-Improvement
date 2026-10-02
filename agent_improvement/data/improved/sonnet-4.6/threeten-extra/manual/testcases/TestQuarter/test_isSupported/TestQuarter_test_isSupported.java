package org.threeten.extra;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_DAY;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.MICRO_OF_DAY;
import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.MILLI_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.SECOND_OF_DAY;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_isSupported {

    private final Quarter quarter = Quarter.Q1;

    @Test
    public void test_isSupported_nullReturnsFalse() {
        assertFalse(quarter.isSupported(null));
    }

    @Test
    public void test_isSupported_timeChronoFieldsReturnFalse() {
        assertFalse(quarter.isSupported(NANO_OF_SECOND));
        assertFalse(quarter.isSupported(NANO_OF_DAY));
        assertFalse(quarter.isSupported(MICRO_OF_SECOND));
        assertFalse(quarter.isSupported(MICRO_OF_DAY));
        assertFalse(quarter.isSupported(MILLI_OF_SECOND));
        assertFalse(quarter.isSupported(MILLI_OF_DAY));
        assertFalse(quarter.isSupported(SECOND_OF_MINUTE));
        assertFalse(quarter.isSupported(SECOND_OF_DAY));
        assertFalse(quarter.isSupported(MINUTE_OF_HOUR));
        assertFalse(quarter.isSupported(MINUTE_OF_DAY));
        assertFalse(quarter.isSupported(HOUR_OF_AMPM));
        assertFalse(quarter.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(quarter.isSupported(HOUR_OF_DAY));
        assertFalse(quarter.isSupported(CLOCK_HOUR_OF_DAY));
        assertFalse(quarter.isSupported(AMPM_OF_DAY));
    }

    @Test
    public void test_isSupported_dateChronoFieldsReturnFalse() {
        assertFalse(quarter.isSupported(DAY_OF_WEEK));
        assertFalse(quarter.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(quarter.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertFalse(quarter.isSupported(DAY_OF_MONTH));
        assertFalse(quarter.isSupported(DAY_OF_YEAR));
        assertFalse(quarter.isSupported(EPOCH_DAY));
        assertFalse(quarter.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(quarter.isSupported(ALIGNED_WEEK_OF_YEAR));
        assertFalse(quarter.isSupported(MONTH_OF_YEAR));
        assertFalse(quarter.isSupported(PROLEPTIC_MONTH));
        assertFalse(quarter.isSupported(YEAR_OF_ERA));
        assertFalse(quarter.isSupported(YEAR));
        assertFalse(quarter.isSupported(ERA));
        assertFalse(quarter.isSupported(INSTANT_SECONDS));
        assertFalse(quarter.isSupported(OFFSET_SECONDS));
    }

    @Test
    public void test_isSupported_quarterOfYearReturnsTrue() {
        assertTrue(quarter.isSupported(QUARTER_OF_YEAR));
    }
}

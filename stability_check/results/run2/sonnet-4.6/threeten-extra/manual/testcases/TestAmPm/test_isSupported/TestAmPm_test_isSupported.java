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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_isSupported {

    // AmPm represents only a half-day (AM or PM); it supports exactly one ChronoField: AMPM_OF_DAY.
    // Every finer-grained or coarser-grained field should return false.

    @Test
    public void test_isSupported_nullReturnsFalse() {
        assertFalse(AmPm.AM.isSupported(null));
    }

    @Test
    public void test_isSupported_onlyAmpmOfDayIsSupported() {
        assertTrue(AmPm.AM.isSupported(AMPM_OF_DAY));
    }

    @Test
    public void test_isSupported_subSecondFieldsNotSupported() {
        AmPm amPm = AmPm.AM;
        assertFalse(amPm.isSupported(NANO_OF_SECOND));
        assertFalse(amPm.isSupported(NANO_OF_DAY));
        assertFalse(amPm.isSupported(MICRO_OF_SECOND));
        assertFalse(amPm.isSupported(MICRO_OF_DAY));
        assertFalse(amPm.isSupported(MILLI_OF_SECOND));
        assertFalse(amPm.isSupported(MILLI_OF_DAY));
    }

    @Test
    public void test_isSupported_secondAndMinuteFieldsNotSupported() {
        AmPm amPm = AmPm.AM;
        assertFalse(amPm.isSupported(SECOND_OF_MINUTE));
        assertFalse(amPm.isSupported(SECOND_OF_DAY));
        assertFalse(amPm.isSupported(MINUTE_OF_HOUR));
        assertFalse(amPm.isSupported(MINUTE_OF_DAY));
    }

    @Test
    public void test_isSupported_hourFieldsNotSupported() {
        AmPm amPm = AmPm.AM;
        assertFalse(amPm.isSupported(HOUR_OF_AMPM));
        assertFalse(amPm.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(amPm.isSupported(HOUR_OF_DAY));
        assertFalse(amPm.isSupported(CLOCK_HOUR_OF_DAY));
    }

    @Test
    public void test_isSupported_dayFieldsNotSupported() {
        AmPm amPm = AmPm.AM;
        assertFalse(amPm.isSupported(DAY_OF_WEEK));
        assertFalse(amPm.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(amPm.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertFalse(amPm.isSupported(DAY_OF_MONTH));
        assertFalse(amPm.isSupported(DAY_OF_YEAR));
        assertFalse(amPm.isSupported(EPOCH_DAY));
    }

    @Test
    public void test_isSupported_weekMonthYearFieldsNotSupported() {
        AmPm amPm = AmPm.AM;
        assertFalse(amPm.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(amPm.isSupported(ALIGNED_WEEK_OF_YEAR));
        assertFalse(amPm.isSupported(MONTH_OF_YEAR));
        assertFalse(amPm.isSupported(PROLEPTIC_MONTH));
        assertFalse(amPm.isSupported(YEAR_OF_ERA));
        assertFalse(amPm.isSupported(YEAR));
        assertFalse(amPm.isSupported(ERA));
    }

    @Test
    public void test_isSupported_instantAndOffsetFieldsNotSupported() {
        AmPm amPm = AmPm.AM;
        assertFalse(amPm.isSupported(INSTANT_SECONDS));
        assertFalse(amPm.isSupported(OFFSET_SECONDS));
    }
}

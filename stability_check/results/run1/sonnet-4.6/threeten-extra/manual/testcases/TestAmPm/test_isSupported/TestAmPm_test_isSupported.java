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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_isSupported {

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("isSupported returns true only for AMPM_OF_DAY; false for null and all other ChronoFields")
    public void test_isSupported() {
        AmPm test = AmPm.AM;

        // null always returns false
        assertFalse(test.isSupported(null));

        // AMPM_OF_DAY is the only supported field
        assertTrue(test.isSupported(AMPM_OF_DAY));

        // sub-second precision fields are not supported
        assertFalse(test.isSupported(NANO_OF_SECOND));
        assertFalse(test.isSupported(NANO_OF_DAY));
        assertFalse(test.isSupported(MICRO_OF_SECOND));
        assertFalse(test.isSupported(MICRO_OF_DAY));
        assertFalse(test.isSupported(MILLI_OF_SECOND));
        assertFalse(test.isSupported(MILLI_OF_DAY));

        // second and minute fields are not supported
        assertFalse(test.isSupported(SECOND_OF_MINUTE));
        assertFalse(test.isSupported(SECOND_OF_DAY));
        assertFalse(test.isSupported(MINUTE_OF_HOUR));
        assertFalse(test.isSupported(MINUTE_OF_DAY));

        // hour-level fields (other than AMPM_OF_DAY itself) are not supported
        assertFalse(test.isSupported(HOUR_OF_AMPM));
        assertFalse(test.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(test.isSupported(HOUR_OF_DAY));
        assertFalse(test.isSupported(CLOCK_HOUR_OF_DAY));

        // day-level fields are not supported
        assertFalse(test.isSupported(DAY_OF_WEEK));
        assertFalse(test.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(test.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertFalse(test.isSupported(DAY_OF_MONTH));
        assertFalse(test.isSupported(DAY_OF_YEAR));
        assertFalse(test.isSupported(EPOCH_DAY));

        // week-level fields are not supported
        assertFalse(test.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(test.isSupported(ALIGNED_WEEK_OF_YEAR));

        // month and year fields are not supported
        assertFalse(test.isSupported(MONTH_OF_YEAR));
        assertFalse(test.isSupported(PROLEPTIC_MONTH));
        assertFalse(test.isSupported(YEAR_OF_ERA));
        assertFalse(test.isSupported(YEAR));
        assertFalse(test.isSupported(ERA));

        // epoch/offset fields are not supported
        assertFalse(test.isSupported(INSTANT_SECONDS));
        assertFalse(test.isSupported(OFFSET_SECONDS));
    }
}

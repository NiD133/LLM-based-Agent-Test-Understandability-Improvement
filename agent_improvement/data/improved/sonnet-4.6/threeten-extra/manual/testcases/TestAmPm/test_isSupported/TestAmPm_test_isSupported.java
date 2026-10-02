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

    // AmPm only carries half-day (AM/PM) information, so only AMPM_OF_DAY is
    // supported. Every other ChronoField requires finer (time) or coarser
    // (date/calendar) granularity that AmPm simply does not hold.

    @Test
    public void test_isSupported() {
        AmPm test = AmPm.AM;

        // null is never a valid field
        assertFalse(test.isSupported(null));

        // AMPM_OF_DAY is the sole field that AmPm can supply
        assertTrue(test.isSupported(AMPM_OF_DAY));

        // Sub-second fields — finer granularity than AmPm
        assertFalse(test.isSupported(NANO_OF_SECOND));
        assertFalse(test.isSupported(NANO_OF_DAY));
        assertFalse(test.isSupported(MICRO_OF_SECOND));
        assertFalse(test.isSupported(MICRO_OF_DAY));
        assertFalse(test.isSupported(MILLI_OF_SECOND));
        assertFalse(test.isSupported(MILLI_OF_DAY));

        // Second and minute fields — finer granularity than AmPm
        assertFalse(test.isSupported(SECOND_OF_MINUTE));
        assertFalse(test.isSupported(SECOND_OF_DAY));
        assertFalse(test.isSupported(MINUTE_OF_HOUR));
        assertFalse(test.isSupported(MINUTE_OF_DAY));

        // Hour fields — finer granularity than AmPm
        assertFalse(test.isSupported(HOUR_OF_AMPM));
        assertFalse(test.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(test.isSupported(HOUR_OF_DAY));
        assertFalse(test.isSupported(CLOCK_HOUR_OF_DAY));

        // Day-of-week and day-position fields — date granularity, not carried by AmPm
        assertFalse(test.isSupported(DAY_OF_WEEK));
        assertFalse(test.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(test.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertFalse(test.isSupported(DAY_OF_MONTH));
        assertFalse(test.isSupported(DAY_OF_YEAR));
        assertFalse(test.isSupported(EPOCH_DAY));

        // Week fields — date granularity, not carried by AmPm
        assertFalse(test.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(test.isSupported(ALIGNED_WEEK_OF_YEAR));

        // Month and year fields — date granularity, not carried by AmPm
        assertFalse(test.isSupported(MONTH_OF_YEAR));
        assertFalse(test.isSupported(PROLEPTIC_MONTH));
        assertFalse(test.isSupported(YEAR_OF_ERA));
        assertFalse(test.isSupported(YEAR));
        assertFalse(test.isSupported(ERA));

        // Instant and offset fields — require timezone context that AmPm lacks
        assertFalse(test.isSupported(INSTANT_SECONDS));
        assertFalse(test.isSupported(OFFSET_SECONDS));
    }
}

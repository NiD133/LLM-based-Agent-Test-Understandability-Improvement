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

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link AmPm#isSupported(TemporalField)}.
 * <p>
 * An {@code AmPm} supports exactly one temporal field, {@code AMPM_OF_DAY};
 * every other {@code ChronoField} (and a {@code null} field) is unsupported.
 */
public class TestAmPm_test_isSupported {

    /** All {@code ChronoField} values that an am-pm cannot be queried for. */
    private static final TemporalField[] UNSUPPORTED_FIELDS = {
        NANO_OF_SECOND,
        NANO_OF_DAY,
        MICRO_OF_SECOND,
        MICRO_OF_DAY,
        MILLI_OF_SECOND,
        MILLI_OF_DAY,
        SECOND_OF_MINUTE,
        SECOND_OF_DAY,
        MINUTE_OF_HOUR,
        MINUTE_OF_DAY,
        HOUR_OF_AMPM,
        CLOCK_HOUR_OF_AMPM,
        HOUR_OF_DAY,
        CLOCK_HOUR_OF_DAY,
        DAY_OF_WEEK,
        ALIGNED_DAY_OF_WEEK_IN_MONTH,
        ALIGNED_DAY_OF_WEEK_IN_YEAR,
        DAY_OF_MONTH,
        DAY_OF_YEAR,
        EPOCH_DAY,
        ALIGNED_WEEK_OF_MONTH,
        ALIGNED_WEEK_OF_YEAR,
        MONTH_OF_YEAR,
        PROLEPTIC_MONTH,
        YEAR_OF_ERA,
        YEAR,
        ERA,
        INSTANT_SECONDS,
        OFFSET_SECONDS,
    };

    @Test
    public void test_isSupported() {
        AmPm test = AmPm.AM;

        // A null field is never supported.
        assertFalse(test.isSupported(null));

        // AMPM_OF_DAY is the only supported field.
        assertTrue(test.isSupported(AMPM_OF_DAY));

        // Any other ChronoField is unsupported.
        for (TemporalField field : UNSUPPORTED_FIELDS) {
            assertFalse(test.isSupported(field), "expected unsupported: " + field);
        }
    }
}

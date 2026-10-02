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
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_isSupported {

    @Test
    public void test_isSupported() {
        Quarter test = Quarter.Q1;

        assertUnsupported(test,
                null,
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
                AMPM_OF_DAY,
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
                OFFSET_SECONDS);

        assertEquals(true, test.isSupported(QUARTER_OF_YEAR));
    }

    private static void assertUnsupported(Quarter quarter, TemporalField... fields) {
        for (TemporalField field : fields) {
            assertEquals(false, quarter.isSupported(field));
        }
    }
}

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isSupported {

    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_isSupported() {
        // null → never supported
        assertFalse(TEST.isSupported((TemporalField) null));

        // Time-of-day ChronoFields → not supported (DayOfMonth carries no time information)
        assertFalse(TEST.isSupported(NANO_OF_SECOND));
        assertFalse(TEST.isSupported(NANO_OF_DAY));
        assertFalse(TEST.isSupported(MICRO_OF_SECOND));
        assertFalse(TEST.isSupported(MICRO_OF_DAY));
        assertFalse(TEST.isSupported(MILLI_OF_SECOND));
        assertFalse(TEST.isSupported(MILLI_OF_DAY));
        assertFalse(TEST.isSupported(SECOND_OF_MINUTE));
        assertFalse(TEST.isSupported(SECOND_OF_DAY));
        assertFalse(TEST.isSupported(MINUTE_OF_HOUR));
        assertFalse(TEST.isSupported(MINUTE_OF_DAY));
        assertFalse(TEST.isSupported(HOUR_OF_AMPM));
        assertFalse(TEST.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(TEST.isSupported(HOUR_OF_DAY));
        assertFalse(TEST.isSupported(CLOCK_HOUR_OF_DAY));
        assertFalse(TEST.isSupported(AMPM_OF_DAY));

        // Week-based date ChronoFields → not supported
        assertFalse(TEST.isSupported(DAY_OF_WEEK));
        assertFalse(TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));

        // DAY_OF_MONTH → the only supported ChronoField
        assertTrue(TEST.isSupported(DAY_OF_MONTH));

        // Remaining date-based and epoch/offset ChronoFields → not supported
        assertFalse(TEST.isSupported(DAY_OF_YEAR));
        assertFalse(TEST.isSupported(EPOCH_DAY));
        assertFalse(TEST.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(TEST.isSupported(ALIGNED_WEEK_OF_YEAR));
        assertFalse(TEST.isSupported(MONTH_OF_YEAR));
        assertFalse(TEST.isSupported(PROLEPTIC_MONTH));
        assertFalse(TEST.isSupported(YEAR_OF_ERA));
        assertFalse(TEST.isSupported(YEAR));
        assertFalse(TEST.isSupported(ERA));
        assertFalse(TEST.isSupported(INSTANT_SECONDS));
        assertFalse(TEST.isSupported(OFFSET_SECONDS));

        // Non-ChronoField fields → delegates to TemporalField.isSupportedBy(this)
        assertFalse(TEST.isSupported(IsoFields.DAY_OF_QUARTER));
        assertTrue(TEST.isSupported(TestingField.INSTANCE));
    }
}

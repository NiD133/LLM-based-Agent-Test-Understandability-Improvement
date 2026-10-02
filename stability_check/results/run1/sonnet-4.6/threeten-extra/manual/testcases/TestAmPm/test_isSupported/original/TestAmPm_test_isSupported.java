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
import static java.time.temporal.ChronoUnit.HALF_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_isSupported {

    //-----------------------------------------------------------------------
    @Test
    public void test_isSupported() {
        AmPm test = AmPm.AM;
        assertEquals(false, test.isSupported(null));
        assertEquals(false, test.isSupported(NANO_OF_SECOND));
        assertEquals(false, test.isSupported(NANO_OF_DAY));
        assertEquals(false, test.isSupported(MICRO_OF_SECOND));
        assertEquals(false, test.isSupported(MICRO_OF_DAY));
        assertEquals(false, test.isSupported(MILLI_OF_SECOND));
        assertEquals(false, test.isSupported(MILLI_OF_DAY));
        assertEquals(false, test.isSupported(SECOND_OF_MINUTE));
        assertEquals(false, test.isSupported(SECOND_OF_DAY));
        assertEquals(false, test.isSupported(MINUTE_OF_HOUR));
        assertEquals(false, test.isSupported(MINUTE_OF_DAY));
        assertEquals(false, test.isSupported(HOUR_OF_AMPM));
        assertEquals(false, test.isSupported(CLOCK_HOUR_OF_AMPM));
        assertEquals(false, test.isSupported(HOUR_OF_DAY));
        assertEquals(false, test.isSupported(CLOCK_HOUR_OF_DAY));
        assertEquals(true, test.isSupported(AMPM_OF_DAY));
        assertEquals(false, test.isSupported(DAY_OF_WEEK));
        assertEquals(false, test.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(false, test.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(false, test.isSupported(DAY_OF_MONTH));
        assertEquals(false, test.isSupported(DAY_OF_YEAR));
        assertEquals(false, test.isSupported(EPOCH_DAY));
        assertEquals(false, test.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertEquals(false, test.isSupported(ALIGNED_WEEK_OF_YEAR));
        assertEquals(false, test.isSupported(MONTH_OF_YEAR));
        assertEquals(false, test.isSupported(PROLEPTIC_MONTH));
        assertEquals(false, test.isSupported(YEAR_OF_ERA));
        assertEquals(false, test.isSupported(YEAR));
        assertEquals(false, test.isSupported(ERA));
        assertEquals(false, test.isSupported(INSTANT_SECONDS));
        assertEquals(false, test.isSupported(OFFSET_SECONDS));
    }
}

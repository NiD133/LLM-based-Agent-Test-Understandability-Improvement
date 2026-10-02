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
import static java.time.temporal.IsoFields.QUARTER_YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.threeten.extra.Quarter.Q3;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestQuarter_test_from_TemporalAccessor_Month {

    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
        return new Object[][] { { 1, -5, 4 }, { 1, -4, 1 }, { 1, -3, 2 }, { 1, -2, 3 }, { 1, -1, 4 }, { 1, 0, 1 }, { 1, 1, 2 }, { 1, 2, 3 }, { 1, 3, 4 }, { 1, 4, 1 }, { 1, 5, 2 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_minus() {
        return new Object[][] { { 1, -5, 2 }, { 1, -4, 1 }, { 1, -3, 4 }, { 1, -2, 3 }, { 1, -1, 2 }, { 1, 0, 1 }, { 1, 1, 4 }, { 1, 2, 3 }, { 1, 3, 2 }, { 1, 4, 1 }, { 1, 5, 4 } };
    }

    @Test
    public void test_from_TemporalAccessor_Month() {
        assertEquals(Quarter.Q1, Quarter.from(Month.JANUARY));
        assertEquals(Quarter.Q1, Quarter.from(Month.FEBRUARY));
        assertEquals(Quarter.Q1, Quarter.from(Month.MARCH));
        assertEquals(Quarter.Q2, Quarter.from(Month.APRIL));
        assertEquals(Quarter.Q2, Quarter.from(Month.MAY));
        assertEquals(Quarter.Q2, Quarter.from(Month.JUNE));
        assertEquals(Quarter.Q3, Quarter.from(Month.JULY));
        assertEquals(Quarter.Q3, Quarter.from(Month.AUGUST));
        assertEquals(Quarter.Q3, Quarter.from(Month.SEPTEMBER));
        assertEquals(Quarter.Q4, Quarter.from(Month.OCTOBER));
        assertEquals(Quarter.Q4, Quarter.from(Month.NOVEMBER));
        assertEquals(Quarter.Q4, Quarter.from(Month.DECEMBER));
    }
}

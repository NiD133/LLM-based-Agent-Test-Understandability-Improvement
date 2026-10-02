/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test Quarter.
 */
public class TestQuarter {

    private static final TemporalField[] UNSUPPORTED_CHRONO_FIELDS = {
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
        OFFSET_SECONDS
    };

    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(Quarter.class));
        assertTrue(Serializable.class.isAssignableFrom(Quarter.class));
        assertTrue(Comparable.class.isAssignableFrom(Quarter.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(Quarter.class));
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_of_int_singleton() {
        assertQuarterValue(1);
        assertQuarterValue(2);
        assertQuarterValue(3);
        assertQuarterValue(4);
    }

    @Test
    public void test_of_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> Quarter.of(0));
    }

    @Test
    public void test_of_int_valueTooHigh() {
        assertThrows(DateTimeException.class, () -> Quarter.of(5));
    }

    //-----------------------------------------------------------------------
    // ofMonth(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_ofMonth_int_singleton() {
        assertMonthsMapToQuarter(Quarter.Q1, 1, 2, 3);
        assertMonthsMapToQuarter(Quarter.Q2, 4, 5, 6);
        assertMonthsMapToQuarter(Quarter.Q3, 7, 8, 9);
        assertMonthsMapToQuarter(Quarter.Q4, 10, 11, 12);
    }

    @Test
    public void test_ofMonth_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(0));
    }

    @Test
    public void test_ofMonth_int_valueTooHigh() {
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(13));
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor)
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor() {
        assertEquals(Quarter.Q2, Quarter.from(LocalDate.of(2011, 6, 6)));
        assertEquals(Quarter.Q1, Quarter.from(LocalDateTime.of(2012, 2, 3, 12, 30)));
    }

    @Test
    public void test_from_TemporalAccessor_Month() {
        assertTemporalAccessorsMapToQuarter(Quarter.Q1, Month.JANUARY, Month.FEBRUARY, Month.MARCH);
        assertTemporalAccessorsMapToQuarter(Quarter.Q2, Month.APRIL, Month.MAY, Month.JUNE);
        assertTemporalAccessorsMapToQuarter(Quarter.Q3, Month.JULY, Month.AUGUST, Month.SEPTEMBER);
        assertTemporalAccessorsMapToQuarter(Quarter.Q4, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
    }

    @Test
    public void test_from_TemporalAccessorl_invalid_noDerive() {
        assertThrows(DateTimeException.class, () -> Quarter.from(LocalTime.of(12, 30)));
    }

    @Test
    public void test_from_TemporalAccessor_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Quarter.from((TemporalAccessor) null));
    }

    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Q'Q");
        assertEquals(Q3, formatter.parse("Q3", Quarter::from));
    }

    //-----------------------------------------------------------------------
    // getDisplayName()
    //-----------------------------------------------------------------------
    @Test
    public void test_getDisplayName() {
        assertEquals("Q1", Quarter.Q1.getDisplayName(TextStyle.SHORT, Locale.US));
    }

    @Test
    public void test_getDisplayName_nullStyle() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Quarter.Q1.getDisplayName(null, Locale.US));
    }

    @Test
    public void test_getDisplayName_nullLocale() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Quarter.Q1.getDisplayName(TextStyle.FULL, null));
    }

    //-----------------------------------------------------------------------
    // isSupported()
    //-----------------------------------------------------------------------
    @Test
    public void test_isSupported() {
        Quarter test = Quarter.Q1;

        assertEquals(false, test.isSupported(null));
        for (TemporalField field : UNSUPPORTED_CHRONO_FIELDS) {
            assertEquals(false, test.isSupported(field));
        }
        assertEquals(true, test.isSupported(QUARTER_OF_YEAR));
    }

    //-----------------------------------------------------------------------
    // range()
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        assertEquals(QUARTER_OF_YEAR.range(), Quarter.Q1.range(QUARTER_OF_YEAR));
    }

    @Test
    public void test_range_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q1.range(MONTH_OF_YEAR));
    }

    @Test
    public void test_range_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Quarter.Q1.range(null));
    }

    //-----------------------------------------------------------------------
    // get()
    //-----------------------------------------------------------------------
    @Test
    public void test_get() {
        assertQuarterFieldValue(Quarter.Q1, 1);
        assertQuarterFieldValue(Quarter.Q2, 2);
        assertQuarterFieldValue(Quarter.Q3, 3);
        assertQuarterFieldValue(Quarter.Q4, 4);
    }

    @Test
    public void test_get_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q2.get(MONTH_OF_YEAR));
    }

    @Test
    public void test_get_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Quarter.Q2.get(null));
    }

    //-----------------------------------------------------------------------
    // getLong()
    //-----------------------------------------------------------------------
    @Test
    public void test_getLong() {
        assertQuarterLongFieldValue(Quarter.Q1, 1);
        assertQuarterLongFieldValue(Quarter.Q2, 2);
        assertQuarterLongFieldValue(Quarter.Q3, 3);
        assertQuarterLongFieldValue(Quarter.Q4, 4);
    }

    @Test
    public void test_getLong_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q2.getLong(MONTH_OF_YEAR));
    }

    @Test
    public void test_getLong_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Quarter.Q2.getLong(null));
    }

    //-----------------------------------------------------------------------
    // plus(long), plus(long,unit)
    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
        return new Object[][] {
            {1, -5, 4},
            {1, -4, 1},
            {1, -3, 2},
            {1, -2, 3},
            {1, -1, 4},
            {1, 0, 1},
            {1, 1, 2},
            {1, 2, 3},
            {1, 3, 4},
            {1, 4, 1},
            {1, 5, 2},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_long(int base, long amount, int expected) {
        assertEquals(Quarter.of(expected), Quarter.of(base).plus(amount));
    }

    //-----------------------------------------------------------------------
    // minus(long), minus(long,unit)
    //-----------------------------------------------------------------------
    public static Object[][] data_minus() {
        return new Object[][] {
            {1, -5, 2},
            {1, -4, 1},
            {1, -3, 4},
            {1, -2, 3},
            {1, -1, 2},
            {1, 0, 1},
            {1, 1, 4},
            {1, 2, 3},
            {1, 3, 2},
            {1, 4, 1},
            {1, 5, 4},
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_long(int base, long amount, int expected) {
        assertEquals(Quarter.of(expected), Quarter.of(base).minus(amount));
    }

    //-----------------------------------------------------------------------
    // length(boolean)
    //-----------------------------------------------------------------------
    @Test
    public void test_length_boolean() {
        assertQuarterLength(Quarter.Q1, 91, 90);
        assertQuarterLength(Quarter.Q2, 91, 91);
        assertQuarterLength(Quarter.Q3, 92, 92);
        assertQuarterLength(Quarter.Q4, 92, 92);
    }

    //-----------------------------------------------------------------------
    // firstMonth()
    //-----------------------------------------------------------------------
    @Test
    public void test_firstMonth() {
        assertFirstMonth(Quarter.Q1, Month.JANUARY);
        assertFirstMonth(Quarter.Q2, Month.APRIL);
        assertFirstMonth(Quarter.Q3, Month.JULY);
        assertFirstMonth(Quarter.Q4, Month.OCTOBER);
    }

    //-----------------------------------------------------------------------
    // query()
    //-----------------------------------------------------------------------
    @Test
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, Quarter.Q1.query(TemporalQueries.chronology()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.localDate()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.localTime()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.offset()));
        assertEquals(QUARTER_YEARS, Quarter.Q1.query(TemporalQueries.precision()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.zone()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.zoneId()));
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        assertQuarterToString(Quarter.Q1, "Q1");
        assertQuarterToString(Quarter.Q2, "Q2");
        assertQuarterToString(Quarter.Q3, "Q3");
        assertQuarterToString(Quarter.Q4, "Q4");
    }

    //-----------------------------------------------------------------------
    // generated methods
    //-----------------------------------------------------------------------
    @Test
    public void test_enum() {
        assertEquals(Quarter.Q4, Quarter.valueOf("Q4"));
        assertEquals(Quarter.Q1, Quarter.values()[0]);
    }

    private static void assertQuarterValue(int value) {
        Quarter test = Quarter.of(value);
        assertEquals(value, test.getValue());
    }

    private static void assertMonthsMapToQuarter(Quarter expected, int... months) {
        for (int month : months) {
            assertSame(expected, Quarter.ofMonth(month));
        }
    }

    private static void assertTemporalAccessorsMapToQuarter(Quarter expected, TemporalAccessor... temporals) {
        for (TemporalAccessor temporal : temporals) {
            assertEquals(expected, Quarter.from(temporal));
        }
    }

    private static void assertQuarterFieldValue(Quarter quarter, int value) {
        assertEquals(value, quarter.get(QUARTER_OF_YEAR));
    }

    private static void assertQuarterLongFieldValue(Quarter quarter, long value) {
        assertEquals(value, quarter.getLong(QUARTER_OF_YEAR));
    }

    private static void assertQuarterLength(Quarter quarter, int leapYearLength, int standardYearLength) {
        assertEquals(leapYearLength, quarter.length(true));
        assertEquals(standardYearLength, quarter.length(false));
    }

    private static void assertFirstMonth(Quarter quarter, Month month) {
        assertEquals(month, quarter.firstMonth());
    }

    private static void assertQuarterToString(Quarter quarter, String text) {
        assertEquals(text, quarter.toString());
    }

}

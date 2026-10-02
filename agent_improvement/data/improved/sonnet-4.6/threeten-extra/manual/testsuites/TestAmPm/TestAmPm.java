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
import static java.time.temporal.ChronoUnit.HALF_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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

/**
 * Test AmPm.
 */
public class TestAmPm {

    //-----------------------------------------------------------------------
    // interfaces
    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(AmPm.class));
        assertTrue(Serializable.class.isAssignableFrom(AmPm.class));
        assertTrue(Comparable.class.isAssignableFrom(AmPm.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(AmPm.class));
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_of_int_singleton_equals() {
        // AM has value 0, PM has value 1 (matching Calendar.AM / Calendar.PM)
        assertEquals(0, AmPm.of(0).getValue());
        assertEquals(1, AmPm.of(1).getValue());
    }

    @Test
    public void test_of_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> AmPm.of(-1));
    }

    @Test
    public void test_of_int_valueTooHigh() {
        assertThrows(DateTimeException.class, () -> AmPm.of(2));
    }

    //-----------------------------------------------------------------------
    // ofHour(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_ofHour_int_singleton() {
        // Hours 0-11 are AM
        for (int i = 0; i < 12; i++) {
            assertSame(AmPm.AM, AmPm.ofHour(i));
        }
        // Hours 12-23 are PM
        for (int i = 12; i < 24; i++) {
            assertSame(AmPm.PM, AmPm.ofHour(i));
        }
    }

    @Test
    public void test_ofHour_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> AmPm.ofHour(-1));
    }

    @Test
    public void test_ofHour_int_valueTooHigh() {
        assertThrows(DateTimeException.class, () -> AmPm.ofHour(24));
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor)
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor() {
        assertEquals(AmPm.AM, AmPm.from(LocalTime.of(8, 30)));
        assertEquals(AmPm.PM, AmPm.from(LocalTime.of(17, 30)));
    }

    @Test
    public void test_from_TemporalAccessor_invalid_noDerive() {
        assertThrows(DateTimeException.class, () -> AmPm.from(LocalDate.of(2007, 7, 30)));
    }

    @Test
    public void test_from_TemporalAccessor_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AmPm.from((TemporalAccessor) null));
    }

    //-----------------------------------------------------------------------
    // getDisplayName()
    //-----------------------------------------------------------------------
    @Test
    public void test_getDisplayName() {
        assertEquals("AM", AmPm.AM.getDisplayName(TextStyle.SHORT, Locale.US));
    }

    @Test
    public void test_getDisplayName_nullStyle() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AmPm.AM.getDisplayName(null, Locale.US));
    }

    @Test
    public void test_getDisplayName_nullLocale() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AmPm.AM.getDisplayName(TextStyle.FULL, null));
    }

    //-----------------------------------------------------------------------
    // isSupported()
    //-----------------------------------------------------------------------
    @Test
    public void test_isSupported() {
        AmPm test = AmPm.AM;

        assertFalse(test.isSupported(null));

        // Sub-second time fields - not supported by AmPm
        assertFalse(test.isSupported(NANO_OF_SECOND));
        assertFalse(test.isSupported(NANO_OF_DAY));
        assertFalse(test.isSupported(MICRO_OF_SECOND));
        assertFalse(test.isSupported(MICRO_OF_DAY));
        assertFalse(test.isSupported(MILLI_OF_SECOND));
        assertFalse(test.isSupported(MILLI_OF_DAY));

        // Second and minute fields - not supported by AmPm
        assertFalse(test.isSupported(SECOND_OF_MINUTE));
        assertFalse(test.isSupported(SECOND_OF_DAY));
        assertFalse(test.isSupported(MINUTE_OF_HOUR));
        assertFalse(test.isSupported(MINUTE_OF_DAY));

        // Hour fields - not supported (AmPm only knows half-day, not specific hour)
        assertFalse(test.isSupported(HOUR_OF_AMPM));
        assertFalse(test.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(test.isSupported(HOUR_OF_DAY));
        assertFalse(test.isSupported(CLOCK_HOUR_OF_DAY));

        // AMPM_OF_DAY is the only supported field
        assertTrue(test.isSupported(AMPM_OF_DAY));

        // Day fields - not supported by AmPm
        assertFalse(test.isSupported(DAY_OF_WEEK));
        assertFalse(test.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(test.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertFalse(test.isSupported(DAY_OF_MONTH));
        assertFalse(test.isSupported(DAY_OF_YEAR));
        assertFalse(test.isSupported(EPOCH_DAY));

        // Week fields - not supported by AmPm
        assertFalse(test.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(test.isSupported(ALIGNED_WEEK_OF_YEAR));

        // Month and year fields - not supported by AmPm
        assertFalse(test.isSupported(MONTH_OF_YEAR));
        assertFalse(test.isSupported(PROLEPTIC_MONTH));
        assertFalse(test.isSupported(YEAR_OF_ERA));
        assertFalse(test.isSupported(YEAR));
        assertFalse(test.isSupported(ERA));

        // Offset/zone fields - not supported by AmPm
        assertFalse(test.isSupported(INSTANT_SECONDS));
        assertFalse(test.isSupported(OFFSET_SECONDS));
    }

    //-----------------------------------------------------------------------
    // range()
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        assertEquals(AMPM_OF_DAY.range(), AmPm.AM.range(AMPM_OF_DAY));
    }

    @Test
    public void test_range_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.AM.range(MONTH_OF_YEAR));
    }

    @Test
    public void test_range_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AmPm.AM.range(null));
    }

    //-----------------------------------------------------------------------
    // get()
    //-----------------------------------------------------------------------
    @Test
    public void test_get() {
        assertEquals(0, AmPm.AM.get(AMPM_OF_DAY));
        assertEquals(1, AmPm.PM.get(AMPM_OF_DAY));
    }

    @Test
    public void test_get_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.PM.get(MONTH_OF_YEAR));
    }

    @Test
    public void test_get_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AmPm.PM.get(null));
    }

    //-----------------------------------------------------------------------
    // getLong()
    //-----------------------------------------------------------------------
    @Test
    public void test_getLong() {
        assertEquals(0, AmPm.AM.getLong(AMPM_OF_DAY));
        assertEquals(1, AmPm.PM.getLong(AMPM_OF_DAY));
    }

    @Test
    public void test_getLong_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.PM.getLong(MONTH_OF_YEAR));
    }

    @Test
    public void test_getLong_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AmPm.PM.getLong(null));
    }

    //-----------------------------------------------------------------------
    // query()
    //-----------------------------------------------------------------------
    @Test
    public void test_query() {
        // AmPm only provides HALF_DAYS precision; all other standard queries return null
        assertNull(AmPm.AM.query(TemporalQueries.chronology()));
        assertNull(AmPm.AM.query(TemporalQueries.localDate()));
        assertNull(AmPm.AM.query(TemporalQueries.localTime()));
        assertNull(AmPm.AM.query(TemporalQueries.offset()));
        assertEquals(HALF_DAYS, AmPm.AM.query(TemporalQueries.precision()));
        assertNull(AmPm.AM.query(TemporalQueries.zone()));
        assertNull(AmPm.AM.query(TemporalQueries.zoneId()));
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        assertEquals("AM", AmPm.AM.toString());
        assertEquals("PM", AmPm.PM.toString());
    }

    //-----------------------------------------------------------------------
    // generated methods
    //-----------------------------------------------------------------------
    @Test
    public void test_enum() {
        assertEquals(AmPm.AM, AmPm.valueOf("AM"));
        assertEquals(AmPm.AM, AmPm.values()[0]);
    }

}

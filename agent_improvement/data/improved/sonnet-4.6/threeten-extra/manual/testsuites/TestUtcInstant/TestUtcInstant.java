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
package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Test UtcInstant.
 */
public class TestUtcInstant {

    // Known Modified Julian Days for well-known calendar dates
    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;  // positive leap second at end of this day
    private static final long MJD_1973_01_01 = 41683;
    private static final long MJD_1973_12_31_LEAP = MJD_1972_12_31_LEAP + 365;  // another leap-second day
    private static final long MJD_TAI_EPOCH = 36204;    // 1958-01-01: start of the TAI time scale
    private static final long MJD_UNIX_EPOCH = 40587;   // 1970-01-01: Unix and Java Instant epoch
    private static final long MJD_1980_01_01 = 44239;   // reference date used in toInstant round-trip tests

    // Duration constants
    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1000000000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    // TAI was initialised 10 seconds ahead of UTC when UTC was defined in 1958
    private static final int TAI_UTC_OFFSET_SECS = 10;
    // Unix epoch seconds corresponding to 1980-01-01T00:00:00Z (MJD_1980_01_01)
    private static final long UNIX_EPOCH_SECS_AT_1980_01_01 = 315532800L;

    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(UtcInstant.class));
        assertTrue(Comparable.class.isAssignableFrom(UtcInstant.class));
    }

    //-----------------------------------------------------------------------
    // serialization
    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        UtcInstant test = UtcInstant.ofModifiedJulianDay(2, 3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertEquals(test, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // ofModififiedJulianDay(long,long)
    //-----------------------------------------------------------------------
    @Test
    public void factory_ofModifiedJulianDay_long_long() {
        for (long i = -2; i <= 2; i++) {
            for (int j = 0; j < 10; j++) {
                UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(i, j);
                assertEquals(i, utcInstant.getModifiedJulianDay());
                assertEquals(j, utcInstant.getNanoOfDay());
                assertFalse(utcInstant.isLeapSecond());
            }
        }
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_endNormal() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1);
        assertEquals(MJD_1972_12_31_LEAP, utcInstant.getModifiedJulianDay());
        assertEquals(NANOS_PER_DAY - 1, utcInstant.getNanoOfDay());
        assertFalse(utcInstant.isLeapSecond());
        assertEquals("1972-12-31T23:59:59.999999999Z", utcInstant.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_startLeap() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY);
        assertEquals(MJD_1972_12_31_LEAP, utcInstant.getModifiedJulianDay());
        assertEquals(NANOS_PER_DAY, utcInstant.getNanoOfDay());
        assertTrue(utcInstant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60Z", utcInstant.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1);
        assertEquals(MJD_1972_12_31_LEAP, utcInstant.getModifiedJulianDay());
        assertEquals(NANOS_PER_LEAP_DAY - 1, utcInstant.getNanoOfDay());
        assertTrue(utcInstant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", utcInstant.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosNegative() {
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, -1));
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap() {
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, NANOS_PER_DAY));
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_leap() {
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY));
    }

    //-----------------------------------------------------------------------
    // of(Instant)
    //-----------------------------------------------------------------------
    @Test
    public void factory_of_Instant() {
        UtcInstant utcInstant = UtcInstant.of(Instant.ofEpochSecond(0, 2));  // 1970-01-01
        assertEquals(MJD_UNIX_EPOCH, utcInstant.getModifiedJulianDay());
        assertEquals(2, utcInstant.getNanoOfDay());
    }

    @Test
    public void factory_of_Instant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> UtcInstant.of((Instant) null));
    }

    //-----------------------------------------------------------------------
    // of(TaiInstant)
    //-----------------------------------------------------------------------
    @Test
    public void factory_of_TaiInstant() {
        // TAI seconds are counted from 1958-01-01 (MJD_TAI_EPOCH) with a 10-second UTC offset
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(MJD_TAI_EPOCH + i, j * NANOS_PER_SEC + 2L);
                TaiInstant tai = TaiInstant.ofTaiSeconds(i * SECS_PER_DAY + j + TAI_UTC_OFFSET_SECS, 2);
                assertEquals(expected, UtcInstant.of(tai));
            }
        }
    }

    @Test
    public void factory_of_TaiInstant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> UtcInstant.of((TaiInstant) null));
    }

    //-----------------------------------------------------------------------
    // parse(CharSequence)
    //-----------------------------------------------------------------------
    @Test
    public void factory_parse_CharSequence() {
        assertEquals(UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC), UtcInstant.parse("1972-12-31T23:59:59Z"));
        assertEquals(UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY), UtcInstant.parse("1972-12-31T23:59:60Z"));
    }

    public static Object[][] data_badParse() {
        return new Object[][] {
            {""},
            {"A"},
            {"2012-13-01T00:00:00Z"},  // bad month
        };
    }

    @ParameterizedTest
    @MethodSource("data_badParse")
    public void factory_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeException.class, () -> UtcInstant.parse(str));
    }

    @Test
    public void factory_parse_CharSequence_invalidLeapSecond() {
        assertThrows(DateTimeException.class, () -> UtcInstant.parse("1972-11-11T23:59:60Z")); // leap second but not leap day
    }

    @Test
    public void factory_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> UtcInstant.parse((String) null));
    }

    //-----------------------------------------------------------------------
    // withModifiedJulianDay()
    //-----------------------------------------------------------------------
    public static @Nullable Object[][] data_withModifiedJulianDay() {
        // Columns: mjd, nanoOfDay, newMjd, expectedMjd (null = expect DateTimeException), expectedNanoOfDay
        return new @Nullable Object[][] {
            {0L, 12345L, 1L, 1L, 12345L},
            {0L, 12345L, -1L, -1L, 12345L},
            {7L, 12345L, 2L, 2L, 12345L},
            {7L, 12345L, -2L, -2L, 12345L},
            {-99L, 12345L, 3L, 3L, 12345L},
            {-99L, 12345L, -3L, -3L, 12345L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_30, null, 0L},           // leap nanoOfDay invalid on non-leap day
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_31_LEAP, MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_01_01, null, 0L},           // leap nanoOfDay invalid on non-leap day
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_12_31_LEAP, MJD_1973_12_31_LEAP, NANOS_PER_DAY},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withModifiedJulianDay")
    public void test_withModifiedJulianDay(long mjd, long nanos, long newMjd, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant i = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant withModifiedJulianDay = i.withModifiedJulianDay(newMjd);
            assertEquals(expectedMjd.longValue(), withModifiedJulianDay.getModifiedJulianDay());
            assertEquals(expectedNanos, withModifiedJulianDay.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> i.withModifiedJulianDay(newMjd));
        }
    }

    //-----------------------------------------------------------------------
    // withNanoOfDay()
    //-----------------------------------------------------------------------
    public static @Nullable Object[][] data_withNanoOfDay() {
        // Columns: mjd, nanoOfDay, newNanoOfDay, expectedMjd (null = expect DateTimeException), expectedNanoOfDay
        return new @Nullable Object[][] {
            {0L, 12345L, 1L, 0L, 1L},
            {0L, 12345L, -1L, null, 0L},
            {7L, 12345L, 2L, 7L, 2L},
            {-99L, 12345L, 3L, -99L, 3L},
            {MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_30,      NANOS_PER_DAY - 1},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1},
            {MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1973_01_01,      NANOS_PER_DAY - 1},
            {MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_DAY,     null,                0L},  // NANOS_PER_DAY invalid on non-leap day
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY,     MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_DAY,     null,                0L},  // NANOS_PER_DAY invalid on non-leap day
            {MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null,                0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1},
            {MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null,                0L},
            {MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY,     null,                0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY,     null,                0L},
            {MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY,     null,                0L},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNanoOfDay")
    public void test_withNanoOfDay(long mjd, long nanos, long newNanoOfDay, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant i = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant withNanoOfDay = i.withNanoOfDay(newNanoOfDay);
            assertEquals(expectedMjd.longValue(), withNanoOfDay.getModifiedJulianDay());
            assertEquals(expectedNanos, withNanoOfDay.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> i.withNanoOfDay(newNanoOfDay));
        }
    }

    //-----------------------------------------------------------------------
    // plus(Duration)
    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
        // Columns: startMjd, startNanoOfDay, durationSecs, durationNanos, expectedMjd, expectedNanoOfDay
        return new Object[][] {
            {0, 0,  -2 * SECS_PER_DAY, 5, -2, 5},
            {0, 0,  -1 * SECS_PER_DAY, 1, -1, 1},
            {0, 0,  -1 * SECS_PER_DAY, 0, -1, 0},
            {0, 0,  0,        -2, -1,  NANOS_PER_DAY - 2},
            {0, 0,  0,        -1, -1,  NANOS_PER_DAY - 1},
            {0, 0,  0,         0,  0,  0},
            {0, 0,  0,         1,  0,  1},
            {0, 0,  0,         2,  0,  2},
            {0, 0,  1,         0,  0,  1 * NANOS_PER_SEC},
            {0, 0,  2,         0,  0,  2 * NANOS_PER_SEC},
            {0, 0,  3, 333333333,  0,  3 * NANOS_PER_SEC + 333333333},
            {0, 0,  1 * SECS_PER_DAY, 0,  1, 0},
            {0, 0,  1 * SECS_PER_DAY, 1,  1, 1},
            {0, 0,  2 * SECS_PER_DAY, 5,  2, 5},

            {1, 0,  -2 * SECS_PER_DAY, 5, -1, 5},
            {1, 0,  -1 * SECS_PER_DAY, 1, 0, 1},
            {1, 0,  -1 * SECS_PER_DAY, 0, 0, 0},
            {1, 0,  0,        -2,  0,  NANOS_PER_DAY - 2},
            {1, 0,  0,        -1,  0,  NANOS_PER_DAY - 1},
            {1, 0,  0,         0,  1,  0},
            {1, 0,  0,         1,  1,  1},
            {1, 0,  0,         2,  1,  2},
            {1, 0,  1,         0,  1,  1 * NANOS_PER_SEC},
            {1, 0,  2,         0,  1,  2 * NANOS_PER_SEC},
            {1, 0,  3, 333333333,  1,  3 * NANOS_PER_SEC + 333333333},
            {1, 0,  1 * SECS_PER_DAY, 0,  2, 0},
            {1, 0,  1 * SECS_PER_DAY, 1,  2, 1},
            {1, 0,  2 * SECS_PER_DAY, 5,  3, 5},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus(long mjd, long nanos, long plusSeconds, int plusNanos, long expectedMjd, long expectedNanos) {
        UtcInstant result = UtcInstant.ofModifiedJulianDay(mjd, nanos).plus(Duration.ofSeconds(plusSeconds, plusNanos));
        assertEquals(expectedMjd, result.getModifiedJulianDay());
        assertEquals(expectedNanos, result.getNanoOfDay());
    }

    @Test
    public void test_plus_overflowTooBig() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);
        assertThrows(ArithmeticException.class, () -> utcInstant.plus(Duration.ofNanos(1)));
    }

    @Test
    public void test_plus_overflowTooSmall() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> utcInstant.plus(Duration.ofNanos(-1)));
    }

    //-----------------------------------------------------------------------
    // minus(Duration)
    //-----------------------------------------------------------------------
    public static Object[][] data_minus() {
        // Columns: startMjd, startNanoOfDay, durationSecs, durationNanos, expectedMjd, expectedNanoOfDay
        return new Object[][] {
            {0, 0,  2 * SECS_PER_DAY, -5, -2, 5},
            {0, 0,  1 * SECS_PER_DAY, -1, -1, 1},
            {0, 0,  1 * SECS_PER_DAY, 0, -1, 0},
            {0, 0,  0,          2, -1,  NANOS_PER_DAY - 2},
            {0, 0,  0,          1, -1,  NANOS_PER_DAY - 1},
            {0, 0,  0,          0,  0,  0},
            {0, 0,  0,         -1,  0,  1},
            {0, 0,  0,         -2,  0,  2},
            {0, 0,  -1,         0,  0,  1 * NANOS_PER_SEC},
            {0, 0,  -2,         0,  0,  2 * NANOS_PER_SEC},
            {0, 0,  -3, -333333333,  0,  3 * NANOS_PER_SEC + 333333333},
            {0, 0,  -1 * SECS_PER_DAY, 0,  1, 0},
            {0, 0,  -1 * SECS_PER_DAY, -1,  1, 1},
            {0, 0,  -2 * SECS_PER_DAY, -5,  2, 5},

            {1, 0,  2 * SECS_PER_DAY, -5, -1, 5},
            {1, 0,  1 * SECS_PER_DAY, -1, 0, 1},
            {1, 0,  1 * SECS_PER_DAY, 0, 0, 0},
            {1, 0,  0,          2,  0,  NANOS_PER_DAY - 2},
            {1, 0,  0,          1,  0,  NANOS_PER_DAY - 1},
            {1, 0,  0,          0,  1,  0},
            {1, 0,  0,         -1,  1,  1},
            {1, 0,  0,         -2,  1,  2},
            {1, 0,  -1,         0,  1,  1 * NANOS_PER_SEC},
            {1, 0,  -2,         0,  1,  2 * NANOS_PER_SEC},
            {1, 0,  -3, -333333333,  1,  3 * NANOS_PER_SEC + 333333333},
            {1, 0,  -1 * SECS_PER_DAY, 0,  2, 0},
            {1, 0,  -1 * SECS_PER_DAY, -1,  2, 1},
            {1, 0,  -2 * SECS_PER_DAY, -5,  3, 5},
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus(long mjd, long nanos, long minusSeconds, int minusNanos, long expectedMjd, long expectedNanos) {
        UtcInstant result = UtcInstant.ofModifiedJulianDay(mjd, nanos).minus(Duration.ofSeconds(minusSeconds, minusNanos));
        assertEquals(expectedMjd, result.getModifiedJulianDay());
        assertEquals(expectedNanos, result.getNanoOfDay());
    }

    @Test
    public void test_minus_overflowTooSmall() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> utcInstant.minus(Duration.ofNanos(1)));
    }

    @Test
    public void test_minus_overflowTooBig() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);
        assertThrows(ArithmeticException.class, () -> utcInstant.minus(Duration.ofNanos(-1)));
    }

    //-----------------------------------------------------------------------
    // durationUntil()
    //-----------------------------------------------------------------------
    @Test
    public void test_durationUntil_oneDayNoLeap() {
        UtcInstant utc1 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_30, 0);
        UtcInstant utc2 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        Duration test = utc1.durationUntil(utc2);
        assertEquals(SECS_PER_DAY, test.getSeconds());
        assertEquals(0, test.getNano());
    }

    @Test
    public void test_durationUntil_oneDayLeap() {
        // A day containing a leap second has SECS_PER_DAY + 1 seconds
        UtcInstant utc1 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        UtcInstant utc2 = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        Duration test = utc1.durationUntil(utc2);
        assertEquals(SECS_PER_DAY + 1, test.getSeconds());
        assertEquals(0, test.getNano());
    }

    @Test
    public void test_durationUntil_oneDayLeapNegative() {
        UtcInstant utc1 = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        UtcInstant utc2 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        Duration test = utc1.durationUntil(utc2);
        assertEquals(-(SECS_PER_DAY + 1), test.getSeconds());
        assertEquals(0, test.getNano());
    }

    //-----------------------------------------------------------------------
    // toTaiInstant()
    //-----------------------------------------------------------------------
    @Test
    public void test_toTaiInstant() {
        // TAI seconds are counted from 1958-01-01 (MJD_TAI_EPOCH) with a 10-second UTC offset
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(MJD_TAI_EPOCH + i, j * NANOS_PER_SEC + 2L);
                TaiInstant taiInstant = utcInstant.toTaiInstant();
                assertEquals(i * SECS_PER_DAY + j + TAI_UTC_OFFSET_SECS, taiInstant.getTaiSeconds());
                assertEquals(2, taiInstant.getNano());
            }
        }
    }

    @Test
    public void test_toTaiInstant_maxInvalid() {
        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> utcInstant.toTaiInstant());
    }

    //-----------------------------------------------------------------------
    // toInstant()
    //-----------------------------------------------------------------------
    @Test
    public void test_toInstant() {
        // Round-trip check: UTC instants around 1980-01-01 convert correctly to Java Instant
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                Instant expected = Instant.ofEpochSecond(UNIX_EPOCH_SECS_AT_1980_01_01 + i * SECS_PER_DAY + j).plusNanos(2);
                UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(MJD_1980_01_01 + i, j * NANOS_PER_SEC + 2);
                assertEquals(expected, utcInstant.toInstant());
            }
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_comparisons() {
        assertOrderedChronologically(
            UtcInstant.ofModifiedJulianDay(-2L, 0),
            UtcInstant.ofModifiedJulianDay(-2L, NANOS_PER_DAY - 2),
            UtcInstant.ofModifiedJulianDay(-2L, NANOS_PER_DAY - 1),
            UtcInstant.ofModifiedJulianDay(-1L, 0),
            UtcInstant.ofModifiedJulianDay(-1L, 1),
            UtcInstant.ofModifiedJulianDay(-1L, NANOS_PER_DAY - 2),
            UtcInstant.ofModifiedJulianDay(-1L, NANOS_PER_DAY - 1),
            UtcInstant.ofModifiedJulianDay(0L, 0),
            UtcInstant.ofModifiedJulianDay(0L, 1),
            UtcInstant.ofModifiedJulianDay(0L, 2),
            UtcInstant.ofModifiedJulianDay(0L, NANOS_PER_DAY - 1),
            UtcInstant.ofModifiedJulianDay(1L, 0),
            UtcInstant.ofModifiedJulianDay(2L, 0)
        );
    }

    /**
     * Verifies that the given instants are in strict chronological order by exhaustively
     * checking all pairwise combinations of compareTo, isBefore, isAfter, and equals.
     */
    void assertOrderedChronologically(UtcInstant... instants) {
        for (int i = 0; i < instants.length; i++) {
            UtcInstant a = instants[i];
            for (int j = 0; j < instants.length; j++) {
                UtcInstant b = instants[j];
                if (i < j) {
                    assertEquals(-1, a.compareTo(b));
                    assertNotEquals(a, b);
                    assertTrue(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                } else if (i > j) {
                    assertEquals(1, a.compareTo(b));
                    assertNotEquals(a, b);
                    assertFalse(a.isBefore(b));
                    assertTrue(a.isAfter(b));
                } else {
                    assertEquals(0, a.compareTo(b));
                    assertEquals(a, b);
                    assertFalse(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                }
            }
        }
    }

    @Test
    public void test_compareTo_ObjectNull() {
        UtcInstant a = UtcInstant.ofModifiedJulianDay(0L, 0);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> a.compareTo(null));
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void test_compareToNonUtcInstant() {
        Comparable c = UtcInstant.ofModifiedJulianDay(0L, 2);
        assertThrows(ClassCastException.class, () -> c.compareTo(new Object()));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(UtcInstant.ofModifiedJulianDay(5L, 20), UtcInstant.ofModifiedJulianDay(5L, 20))
            .addEqualityGroup(UtcInstant.ofModifiedJulianDay(5L, 30), UtcInstant.ofModifiedJulianDay(5L, 30))
            .addEqualityGroup(UtcInstant.ofModifiedJulianDay(6L, 20), UtcInstant.ofModifiedJulianDay(6L, 20))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    public static Object[][] data_toString() {
        // Columns: mjd, nanoOfDay, expectedString
        return new Object[][] {
            {MJD_UNIX_EPOCH,      0,                              "1970-01-01T00:00:00Z"},
            {MJD_UNIX_EPOCH + 1,  1,                              "1970-01-02T00:00:00.000000001Z"},
            {MJD_UNIX_EPOCH + 1,  999,                            "1970-01-02T00:00:00.000000999Z"},
            {MJD_UNIX_EPOCH + 1,  1000,                           "1970-01-02T00:00:00.000001Z"},
            {MJD_UNIX_EPOCH + 1,  999000,                         "1970-01-02T00:00:00.000999Z"},
            {MJD_UNIX_EPOCH + 1,  1000000,                        "1970-01-02T00:00:00.001Z"},
            {MJD_UNIX_EPOCH + 31, 999999999,                      "1970-02-01T00:00:00.999999999Z"},
            {MJD_UNIX_EPOCH + 32, 1000000000,                     "1970-02-02T00:00:01Z"},
            {MJD_UNIX_EPOCH + 33, 60L * 1000000000L,              "1970-02-03T00:01:00Z"},
            {MJD_UNIX_EPOCH + 34, 60L * 60L * 1000000000L,        "1970-02-04T01:00:00Z"},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC,  "1972-12-31T23:59:59Z"},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY,                  "1972-12-31T23:59:60Z"},
            {MJD_1973_01_01,      0,                              "1973-01-01T00:00:00Z"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(long mjd, long nod, String expected) {
        assertEquals(expected, UtcInstant.ofModifiedJulianDay(mjd, nod).toString());
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString_parse(long mjd, long nod, String str) {
        assertEquals(UtcInstant.ofModifiedJulianDay(mjd, nod), UtcInstant.parse(str));
    }

}

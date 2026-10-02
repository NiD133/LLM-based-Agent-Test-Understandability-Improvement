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

    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;
    private static final long MJD_1973_12_31_LEAP = MJD_1972_12_31_LEAP + 365;
    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1000000000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

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
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(2, 3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(instant);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertEquals(instant, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // ofModifiedJulianDay(long,long)
    //-----------------------------------------------------------------------
    @Test
    public void factory_ofModifiedJulianDay_long_long() {
        for (long mjd = -2; mjd <= 2; mjd++) {
            for (int nanoOfDay = 0; nanoOfDay < 10; nanoOfDay++) {
                UtcInstant instant = UtcInstant.ofModifiedJulianDay(mjd, nanoOfDay);
                assertUtcFields(instant, mjd, nanoOfDay);
                assertFalse(instant.isLeapSecond());
            }
        }
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_endNormal() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1);
        assertUtcFields(instant, MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1);
        assertFalse(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:59.999999999Z", instant.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_startLeap() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY);
        assertUtcFields(instant, MJD_1972_12_31_LEAP, NANOS_PER_DAY);
        assertTrue(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60Z", instant.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1);
        assertUtcFields(instant, MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1);
        assertTrue(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", instant.toString());
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
        UtcInstant instant = UtcInstant.of(Instant.ofEpochSecond(0, 2));  // 1970-01-01
        assertUtcFields(instant, 40587, 2);
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
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOffset = 0; secondOffset < 10; secondOffset++) {
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(36204 + dayOffset, secondOffset * NANOS_PER_SEC + 2L);
                TaiInstant tai = TaiInstant.ofTaiSeconds(dayOffset * SECS_PER_DAY + secondOffset + 10, 2);
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
        return new @Nullable Object[][] {
            {0L, 12345L, 1L, 1L, 12345L},
            {0L, 12345L, -1L, -1L, 12345L},
            {7L, 12345L, 2L, 2L, 12345L},
            {7L, 12345L, -2L, -2L, 12345L},
            {-99L, 12345L, 3L, 3L, 12345L},
            {-99L, 12345L, -3L, -3L, 12345L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_30, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_31_LEAP, MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_01_01, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_12_31_LEAP, MJD_1973_12_31_LEAP, NANOS_PER_DAY},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withModifiedJulianDay")
    public void test_withModifiedJulianDay(long mjd, long nanos, long newMjd, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant base = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant changed = base.withModifiedJulianDay(newMjd);
            assertUtcFields(changed, expectedMjd.longValue(), expectedNanos);
        } else {
            assertThrows(DateTimeException.class, () -> base.withModifiedJulianDay(newMjd));
        }
    }

    //-----------------------------------------------------------------------
    // withNanoOfDay()
    //-----------------------------------------------------------------------
    public static @Nullable Object[][] data_withNanoOfDay() {
        return new @Nullable Object[][] {
            {0L, 12345L, 1L, 0L, 1L},
            {0L, 12345L, -1L, null, 0L},
            {7L, 12345L, 2L, 7L, 2L},
            {-99L, 12345L, 3L, -99L, 3L},
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_30, NANOS_PER_DAY - 1},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1973_01_01, NANOS_PER_DAY - 1},
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY, MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L},
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L},
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNanoOfDay")
    public void test_withNanoOfDay(long mjd, long nanos, long newNanoOfDay, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant base = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant changed = base.withNanoOfDay(newNanoOfDay);
            assertUtcFields(changed, expectedMjd.longValue(), expectedNanos);
        } else {
            assertThrows(DateTimeException.class, () -> base.withNanoOfDay(newNanoOfDay));
        }
    }

    //-----------------------------------------------------------------------
    // plus(Duration)
    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
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
        assertUtcFields(result, expectedMjd, expectedNanos);
    }

    @Test
    public void test_plus_overflowTooBig() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);
        assertThrows(ArithmeticException.class, () -> instant.plus(Duration.ofNanos(1)));
    }

    @Test
    public void test_plus_overflowTooSmall() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> instant.plus(Duration.ofNanos(-1)));
    }

    //-----------------------------------------------------------------------
    // minus(Duration)
    //-----------------------------------------------------------------------
    public static Object[][] data_minus() {
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
        assertUtcFields(result, expectedMjd, expectedNanos);
    }

    @Test
    public void test_minus_overflowTooSmall() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> instant.minus(Duration.ofNanos(1)));
    }

    @Test
    public void test_minus_overflowTooBig() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);
        assertThrows(ArithmeticException.class, () -> instant.minus(Duration.ofNanos(-1)));
    }

    //-----------------------------------------------------------------------
    // durationUntil()
    //-----------------------------------------------------------------------
    @Test
    public void test_durationUntil_oneDayNoLeap() {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(MJD_1972_12_30, 0);
        UtcInstant end = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        Duration duration = start.durationUntil(end);
        assertDuration(duration, 86400, 0);
    }

    @Test
    public void test_durationUntil_oneDayLeap() {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        UtcInstant end = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        Duration duration = start.durationUntil(end);
        assertDuration(duration, 86401, 0);
    }

    @Test
    public void test_durationUntil_oneDayLeapNegative() {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        UtcInstant end = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        Duration duration = start.durationUntil(end);
        assertDuration(duration, -86401, 0);
    }

    //-----------------------------------------------------------------------
    // toTaiInstant()
    //-----------------------------------------------------------------------
    @Test
    public void test_toTaiInstant() {
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOffset = 0; secondOffset < 10; secondOffset++) {
                UtcInstant utc = UtcInstant.ofModifiedJulianDay(36204 + dayOffset, secondOffset * NANOS_PER_SEC + 2L);
                TaiInstant tai = utc.toTaiInstant();
                assertEquals(dayOffset * SECS_PER_DAY + secondOffset + 10, tai.getTaiSeconds());
                assertEquals(2, tai.getNano());
            }
        }
    }

    @Test
    public void test_toTaiInstant_maxInvalid() {
        UtcInstant utc = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> utc.toTaiInstant());
    }

    //-----------------------------------------------------------------------
    // toInstant()
    //-----------------------------------------------------------------------
    @Test
    public void test_toInstant() {
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOffset = 0; secondOffset < 10; secondOffset++) {
                Instant expected = Instant.ofEpochSecond(315532800 + dayOffset * SECS_PER_DAY + secondOffset).plusNanos(2);
                UtcInstant utc = UtcInstant.ofModifiedJulianDay(44239 + dayOffset, secondOffset * NANOS_PER_SEC + 2);
                assertEquals(expected, utc.toInstant());
            }
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_comparisons() {
        doTest_comparisons_UtcInstant(
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

    void doTest_comparisons_UtcInstant(UtcInstant... instants) {
        for (int i = 0; i < instants.length; i++) {
            UtcInstant earlier = instants[i];
            for (int j = 0; j < instants.length; j++) {
                UtcInstant later = instants[j];
                if (i < j) {
                    assertEquals(-1, earlier.compareTo(later));
                    assertNotEquals(earlier, later);
                    assertTrue(earlier.isBefore(later));
                    assertFalse(earlier.isAfter(later));
                } else if (i > j) {
                    assertEquals(1, earlier.compareTo(later));
                    assertNotEquals(earlier, later);
                    assertFalse(earlier.isBefore(later));
                    assertTrue(earlier.isAfter(later));
                } else {
                    assertEquals(0, earlier.compareTo(later));
                    assertEquals(earlier, later);
                    assertFalse(earlier.isBefore(later));
                    assertFalse(earlier.isAfter(later));
                }
            }
        }
    }

    @Test
    public void test_compareTo_ObjectNull() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(0L, 0);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> instant.compareTo(null));
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void test_compareToNonUtcInstant() {
        Comparable comparable = UtcInstant.ofModifiedJulianDay(0L, 2);
        assertThrows(ClassCastException.class, () -> comparable.compareTo(new Object()));
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
        return new Object[][] {
            {40587, 0, "1970-01-01T00:00:00Z"},
            {40588, 1, "1970-01-02T00:00:00.000000001Z"},
            {40588, 999, "1970-01-02T00:00:00.000000999Z"},
            {40588, 1000, "1970-01-02T00:00:00.000001Z"},
            {40588, 999000, "1970-01-02T00:00:00.000999Z"},
            {40588, 1000000, "1970-01-02T00:00:00.001Z"},
            {40618, 999999999, "1970-02-01T00:00:00.999999999Z"},
            {40619, 1000000000, "1970-02-02T00:00:01Z"},
            {40620, 60L * 1000000000L, "1970-02-03T00:01:00Z"},
            {40621, 60L * 60L * 1000000000L, "1970-02-04T01:00:00Z"},
            {MJD_1972_12_31_LEAP, 24L * 60L * 60L * 1000000000L - 1000000000L, "1972-12-31T23:59:59Z"},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, "1972-12-31T23:59:60Z"},
            {MJD_1973_01_01, 0, "1973-01-01T00:00:00Z"},
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

    private static void assertUtcFields(UtcInstant instant, long expectedMjd, long expectedNanoOfDay) {
        assertEquals(expectedMjd, instant.getModifiedJulianDay());
        assertEquals(expectedNanoOfDay, instant.getNanoOfDay());
    }

    private static void assertDuration(Duration duration, long expectedSeconds, int expectedNanos) {
        assertEquals(expectedSeconds, duration.getSeconds());
        assertEquals(expectedNanos, duration.getNano());
    }

}

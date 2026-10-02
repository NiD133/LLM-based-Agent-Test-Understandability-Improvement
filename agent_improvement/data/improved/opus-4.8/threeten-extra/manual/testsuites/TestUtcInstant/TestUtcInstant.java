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

    // Modified Julian Days (MJD) framing the well-known leap second at the end of 1972.
    // 1972-12-31 was a leap day: it contained the extra second 23:59:60.
    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;
    // The next leap day, one (365-day) year after the 1972 leap day.
    private static final long MJD_1973_12_31_LEAP = MJD_1972_12_31_LEAP + 365;

    // The Modified Julian Day of the Unix epoch, 1970-01-01.
    private static final long MJD_1970_01_01 = 40587;
    // 1958-01-01 (MJD 36204) is the reference date used by the TAI <-> UTC conversion tests.
    private static final long MJD_REF_TAI = 36204;
    // 1980-01-01 (MJD 44239, epoch second 315532800) is the reference date used by the toInstant test.
    private static final long MJD_REF_INSTANT = 44239;
    private static final long EPOCH_SECOND_1980_01_01 = 315532800L;
    // The fixed TAI-minus-UTC offset (in seconds) in effect at the TAI reference date above.
    private static final long TAI_UTC_OFFSET_SECONDS = 10;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1000000000L;
    // Length of an ordinary day and of a positive-leap-second day, expressed in nanoseconds.
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
        UtcInstant original = UtcInstant.ofModifiedJulianDay(2, 3);

        // Round-trip the instant through Java serialization and confirm it survives unchanged.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertEquals(original, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // ofModififiedJulianDay(long,long)
    //-----------------------------------------------------------------------
    @Test
    public void factory_ofModifiedJulianDay_long_long() {
        // A small grid of ordinary (non-leap) days and nano-of-day values is stored verbatim.
        for (long mjd = -2; mjd <= 2; mjd++) {
            for (int nanoOfDay = 0; nanoOfDay < 10; nanoOfDay++) {
                UtcInstant test = UtcInstant.ofModifiedJulianDay(mjd, nanoOfDay);
                assertEquals(mjd, test.getModifiedJulianDay());
                assertEquals(nanoOfDay, test.getNanoOfDay());
                assertFalse(test.isLeapSecond());
            }
        }
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_endNormal() {
        // The last nanosecond of the normal part of the leap day: still not a leap second.
        UtcInstant test = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1);
        assertEquals(MJD_1972_12_31_LEAP, test.getModifiedJulianDay());
        assertEquals(NANOS_PER_DAY - 1, test.getNanoOfDay());
        assertFalse(test.isLeapSecond());
        assertEquals("1972-12-31T23:59:59.999999999Z", test.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_startLeap() {
        // The very first nanosecond of the leap second (23:59:60).
        UtcInstant test = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY);
        assertEquals(MJD_1972_12_31_LEAP, test.getModifiedJulianDay());
        assertEquals(NANOS_PER_DAY, test.getNanoOfDay());
        assertTrue(test.isLeapSecond());
        assertEquals("1972-12-31T23:59:60Z", test.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        // The last nanosecond of the leap second, the maximum valid nano-of-day on a leap day.
        UtcInstant test = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1);
        assertEquals(MJD_1972_12_31_LEAP, test.getModifiedJulianDay());
        assertEquals(NANOS_PER_LEAP_DAY - 1, test.getNanoOfDay());
        assertTrue(test.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", test.toString());
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosNegative() {
        // A negative nano-of-day is never valid.
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, -1));
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap() {
        // On an ordinary day there is no leap second, so NANOS_PER_DAY is already out of range.
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, NANOS_PER_DAY));
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_leap() {
        // Even on a leap day, one nanosecond past the leap second is out of range.
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY));
    }

    //-----------------------------------------------------------------------
    // of(Instant)
    //-----------------------------------------------------------------------
    @Test
    public void factory_of_Instant() {
        // 2 nanoseconds past the Unix epoch (1970-01-01T00:00:00).
        UtcInstant test = UtcInstant.of(Instant.ofEpochSecond(0, 2));
        assertEquals(MJD_1970_01_01, test.getModifiedJulianDay());
        assertEquals(2, test.getNanoOfDay());
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
        // Walk a range of days and seconds around the TAI reference date, building the matching
        // TaiInstant for each and checking that of(TaiInstant) reconstructs the expected UtcInstant.
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int second = 0; second < 10; second++) {
                UtcInstant expected =
                    UtcInstant.ofModifiedJulianDay(MJD_REF_TAI + dayOffset, second * NANOS_PER_SEC + 2L);
                TaiInstant tai = TaiInstant.ofTaiSeconds(
                    dayOffset * SECS_PER_DAY + second + TAI_UTC_OFFSET_SECONDS, 2);
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
        // The last ordinary second of the leap day parses to nano-of-day one second before end-of-normal-day.
        assertEquals(
            UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC),
            UtcInstant.parse("1972-12-31T23:59:59Z"));
        // The leap second itself (23:59:60) parses to nano-of-day NANOS_PER_DAY.
        assertEquals(
            UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY),
            UtcInstant.parse("1972-12-31T23:59:60Z"));
    }

    public static Object[][] data_badParse() {
        return new Object[][] {
            {""},                       // empty text
            {"A"},                      // not a date-time at all
            {"2012-13-01T00:00:00Z"},   // month 13 is invalid
        };
    }

    @ParameterizedTest
    @MethodSource("data_badParse")
    public void factory_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeException.class, () -> UtcInstant.parse(str));
    }

    @Test
    public void factory_parse_CharSequence_invalidLeapSecond() {
        // 23:59:60 is only valid on a leap day; 1972-11-11 is not one.
        assertThrows(DateTimeException.class, () -> UtcInstant.parse("1972-11-11T23:59:60Z"));
    }

    @Test
    public void factory_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> UtcInstant.parse((String) null));
    }

    //-----------------------------------------------------------------------
    // withModifiedJulianDay()
    //-----------------------------------------------------------------------
    // Columns: startMjd, startNanoOfDay, newMjd, expectedMjd, expectedNanoOfDay.
    // A null expectedMjd means the change is invalid and must throw DateTimeException
    // (it would leave a leap-second nano-of-day on a non-leap day).
    public static @Nullable Object[][] data_withModifiedJulianDay() {
        return new @Nullable Object[][] {
            {0L, 12345L, 1L, 1L, 12345L},
            {0L, 12345L, -1L, -1L, 12345L},
            {7L, 12345L, 2L, 2L, 12345L},
            {7L, 12345L, -2L, -2L, 12345L},
            {-99L, 12345L, 3L, 3L, 12345L},
            {-99L, 12345L, -3L, -3L, 12345L},
            // Moving a leap-second instant onto an ordinary day is invalid.
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_30, null, 0L},
            // Keeping it on its own leap day is fine.
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_31_LEAP, MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_01_01, null, 0L},
            // Moving it onto another leap day is also fine.
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_12_31_LEAP, MJD_1973_12_31_LEAP, NANOS_PER_DAY},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withModifiedJulianDay")
    public void test_withModifiedJulianDay(long mjd, long nanos, long newMjd, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant result = start.withModifiedJulianDay(newMjd);
            assertEquals(expectedMjd.longValue(), result.getModifiedJulianDay());
            assertEquals(expectedNanos, result.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> start.withModifiedJulianDay(newMjd));
        }
    }

    //-----------------------------------------------------------------------
    // withNanoOfDay()
    //-----------------------------------------------------------------------
    // Columns: startMjd, startNanoOfDay, newNanoOfDay, expectedMjd, expectedNanoOfDay.
    // A null expectedMjd means the new nano-of-day is invalid for that day and must throw.
    public static @Nullable Object[][] data_withNanoOfDay() {
        return new @Nullable Object[][] {
            {0L, 12345L, 1L, 0L, 1L},
            {0L, 12345L, -1L, null, 0L},                                      // negative is invalid
            {7L, 12345L, 2L, 7L, 2L},
            {-99L, 12345L, 3L, -99L, 3L},
            // Last ordinary nanosecond of the day is valid on any day.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_30, NANOS_PER_DAY - 1},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1973_01_01, NANOS_PER_DAY - 1},
            // Leap-second nano-of-day (NANOS_PER_DAY): only valid on the leap day.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY, MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L},
            // Last nanosecond of the leap second: only valid on the leap day.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L},
            // One nanosecond past the leap second: invalid on every day.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNanoOfDay")
    public void test_withNanoOfDay(long mjd, long nanos, long newNanoOfDay, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant result = start.withNanoOfDay(newNanoOfDay);
            assertEquals(expectedMjd.longValue(), result.getModifiedJulianDay());
            assertEquals(expectedNanos, result.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> start.withNanoOfDay(newNanoOfDay));
        }
    }

    //-----------------------------------------------------------------------
    // plus(Duration)
    //-----------------------------------------------------------------------
    // Columns: startMjd, startNanoOfDay, plusSeconds, plusNanos, expectedMjd, expectedNanoOfDay.
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
        assertEquals(expectedMjd, result.getModifiedJulianDay());
        assertEquals(expectedNanos, result.getNanoOfDay());
    }

    @Test
    public void test_plus_overflowTooBig() {
        // Adding to the largest representable instant overflows.
        UtcInstant max = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);
        assertThrows(ArithmeticException.class, () -> max.plus(Duration.ofNanos(1)));
    }

    @Test
    public void test_plus_overflowTooSmall() {
        // Subtracting (via a negative duration) from the smallest representable instant overflows.
        UtcInstant min = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> min.plus(Duration.ofNanos(-1)));
    }

    //-----------------------------------------------------------------------
    // minus(Duration)
    //-----------------------------------------------------------------------
    // Columns: startMjd, startNanoOfDay, minusSeconds, minusNanos, expectedMjd, expectedNanoOfDay.
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
        assertEquals(expectedMjd, result.getModifiedJulianDay());
        assertEquals(expectedNanos, result.getNanoOfDay());
    }

    @Test
    public void test_minus_overflowTooSmall() {
        // Subtracting from the smallest representable instant overflows.
        UtcInstant min = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> min.minus(Duration.ofNanos(1)));
    }

    @Test
    public void test_minus_overflowTooBig() {
        // Subtracting a negative duration (i.e. adding) from the largest instant overflows.
        UtcInstant max = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);
        assertThrows(ArithmeticException.class, () -> max.minus(Duration.ofNanos(-1)));
    }

    //-----------------------------------------------------------------------
    // durationUntil()
    //-----------------------------------------------------------------------
    @Test
    public void test_durationUntil_oneDayNoLeap() {
        // From the start of an ordinary day to the start of the next day: exactly 86400 seconds.
        UtcInstant start = UtcInstant.ofModifiedJulianDay(MJD_1972_12_30, 0);
        UtcInstant end = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        Duration test = start.durationUntil(end);
        assertEquals(86400, test.getSeconds());
        assertEquals(0, test.getNano());
    }

    @Test
    public void test_durationUntil_oneDayLeap() {
        // Crossing the leap day adds the extra leap second: 86401 seconds.
        UtcInstant start = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        UtcInstant end = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        Duration test = start.durationUntil(end);
        assertEquals(86401, test.getSeconds());
        assertEquals(0, test.getNano());
    }

    @Test
    public void test_durationUntil_oneDayLeapNegative() {
        // The same span measured backwards: -86401 seconds.
        UtcInstant start = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        UtcInstant end = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        Duration test = start.durationUntil(end);
        assertEquals(-86401, test.getSeconds());
        assertEquals(0, test.getNano());
    }

    //-----------------------------------------------------------------------
    // toTaiInstant()
    //-----------------------------------------------------------------------
    @Test
    public void test_toTaiInstant() {
        // Inverse of factory_of_TaiInstant: each UtcInstant around the reference date should
        // convert to the TaiInstant whose seconds are dayOffset days plus the leap offset.
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int second = 0; second < 10; second++) {
                UtcInstant utc =
                    UtcInstant.ofModifiedJulianDay(MJD_REF_TAI + dayOffset, second * NANOS_PER_SEC + 2L);
                TaiInstant test = utc.toTaiInstant();
                assertEquals(dayOffset * SECS_PER_DAY + second + TAI_UTC_OFFSET_SECONDS, test.getTaiSeconds());
                assertEquals(2, test.getNano());
            }
        }
    }

    @Test
    public void test_toTaiInstant_maxInvalid() {
        // The maximum MJD cannot be expressed as a TaiInstant without overflow.
        UtcInstant max = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> max.toTaiInstant());
    }

    //-----------------------------------------------------------------------
    // toInstant()
    //-----------------------------------------------------------------------
    @Test
    public void test_toInstant() {
        // Each UtcInstant around the 1980-01-01 reference date maps to the matching Instant
        // (epoch second of the reference date, offset by whole days and seconds, plus 2 nanos).
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int second = 0; second < 10; second++) {
                Instant expected = Instant
                    .ofEpochSecond(EPOCH_SECOND_1980_01_01 + dayOffset * SECS_PER_DAY + second)
                    .plusNanos(2);
                UtcInstant test =
                    UtcInstant.ofModifiedJulianDay(MJD_REF_INSTANT + dayOffset, second * NANOS_PER_SEC + 2);
                assertEquals(expected, test.toInstant());
            }
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_comparisons() {
        // The arguments are listed in strictly increasing time-line order; the helper then checks
        // compareTo / isBefore / isAfter / equals for every ordered pair.
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
        // instants must already be in ascending order. For each pair (earlier, later) verify the
        // full set of comparison contracts in both directions and for the reflexive case.
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
        // Comparing against an unrelated type must fail with ClassCastException.
        Comparable c = UtcInstant.ofModifiedJulianDay(0L, 2);
        assertThrows(ClassCastException.class, () -> c.compareTo(new Object()));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        // Each group holds instants that must be equal to each other and unequal to the other groups.
        new EqualsTester()
            .addEqualityGroup(UtcInstant.ofModifiedJulianDay(5L, 20), UtcInstant.ofModifiedJulianDay(5L, 20))
            .addEqualityGroup(UtcInstant.ofModifiedJulianDay(5L, 30), UtcInstant.ofModifiedJulianDay(5L, 30))
            .addEqualityGroup(UtcInstant.ofModifiedJulianDay(6L, 20), UtcInstant.ofModifiedJulianDay(6L, 20))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    // Columns: mjd, nanoOfDay, expected ISO-8601 string.
    public static Object[][] data_toString() {
        return new Object[][] {
            {MJD_1970_01_01, 0, "1970-01-01T00:00:00Z"},
            {40588, 1, "1970-01-02T00:00:00.000000001Z"},
            {40588, 999, "1970-01-02T00:00:00.000000999Z"},
            {40588, 1000, "1970-01-02T00:00:00.000001Z"},
            {40588, 999000, "1970-01-02T00:00:00.000999Z"},
            {40588, 1000000, "1970-01-02T00:00:00.001Z"},
            {40618, 999999999, "1970-02-01T00:00:00.999999999Z"},
            {40619, 1000000000, "1970-02-02T00:00:01Z"},
            {40620, 60L * 1000000000L, "1970-02-03T00:01:00Z"},
            {40621, 60L * 60L * 1000000000L, "1970-02-04T01:00:00Z"},
            // The leap day: last ordinary second, then the leap second, then the following day.
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
        // The same table read the other way: parsing the rendered string must round-trip.
        assertEquals(UtcInstant.ofModifiedJulianDay(mjd, nod), UtcInstant.parse(str));
    }

}

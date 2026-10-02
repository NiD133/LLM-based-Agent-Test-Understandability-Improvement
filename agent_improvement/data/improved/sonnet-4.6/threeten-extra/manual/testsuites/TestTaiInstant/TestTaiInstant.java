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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link TaiInstant}.
 *
 * <p>TAI (International Atomic Time) counts uninterrupted SI seconds from its epoch of
 * 1958-01-01T00:00:00(TAI). Internally a {@code TaiInstant} is stored as (taiSeconds, nanos),
 * where nanos is always normalised into the range [0, 999_999_999].
 *
 * <p>Key reference constants used throughout these tests:
 * <ul>
 *   <li>{@code MJD_TAI_EPOCH  = 36204} — Modified Julian Day of 1958-01-01 (TAI epoch)</li>
 *   <li>{@code MJD_UNIX_EPOCH = 40587} — Modified Julian Day of 1970-01-01 (Unix epoch)</li>
 *   <li>{@code TAI_OFFSET_AT_TAI_EPOCH = 10} — TAI was 10 s ahead of UTC when TAI started</li>
 *   <li>{@code UNIX_SECONDS_AT_TAI_EPOCH = -378691200} — Unix timestamp (s from 1970-01-01) of
 *       the TAI epoch (1958-01-01), i.e. {@code -(MJD_UNIX_EPOCH - MJD_TAI_EPOCH) * 86400}</li>
 * </ul>
 */
public class TestTaiInstant {

    /** Modified Julian Day of the TAI epoch: 1958-01-01T00:00:00(TAI). */
    private static final long MJD_TAI_EPOCH = 36204L;

    /** Modified Julian Day of the Unix epoch: 1970-01-01T00:00:00(UTC). */
    private static final long MJD_UNIX_EPOCH = 40587L;

    /** TAI-UTC difference in seconds at the start of the TAI time-scale (1958-01-01). */
    private static final long TAI_OFFSET_AT_TAI_EPOCH = 10L;

    /** Number of SI seconds in one day. */
    private static final long SECONDS_PER_DAY = 24 * 60 * 60;

    /**
     * Unix timestamp (seconds from 1970-01-01) of the TAI epoch (1958-01-01).
     * Equal to {@code -(MJD_UNIX_EPOCH - MJD_TAI_EPOCH) * SECONDS_PER_DAY}.
     */
    private static final long UNIX_SECONDS_AT_TAI_EPOCH =
            -(MJD_UNIX_EPOCH - MJD_TAI_EPOCH) * SECONDS_PER_DAY;

    //-----------------------------------------------------------------------
    // Interface checks
    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(TaiInstant.class));
        assertTrue(Comparable.class.isAssignableFrom(TaiInstant.class));
    }

    //-----------------------------------------------------------------------
    // serialization
    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        TaiInstant test = TaiInstant.ofTaiSeconds(2, 3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertEquals(test, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // ofTaiSeconds(long, long)
    //-----------------------------------------------------------------------

    /**
     * Verifies that {@code ofTaiSeconds} normalises the nanosecond argument into [0, 999_999_999]:
     * <ul>
     *   <li>Non-negative nanos in range are stored verbatim.</li>
     *   <li>Negative nanos borrow one second and wrap around to a positive value.</li>
     *   <li>Nanos at the very top of the valid range (999_999_990..999_999_999) are stored verbatim.</li>
     * </ul>
     */
    @Test
    public void factory_ofTaiSecondslong_long() {
        for (long i = -2; i <= 2; i++) {
            // Non-negative nanos in range [0, 9]: stored verbatim
            for (int j = 0; j < 10; j++) {
                TaiInstant t = TaiInstant.ofTaiSeconds(i, j);
                assertEquals(i, t.getTaiSeconds());
                assertEquals(j, t.getNano());
            }
            // Negative nanos [-10, -1]: second decrements by 1, nano wraps to a positive value
            for (int j = -10; j < 0; j++) {
                TaiInstant t = TaiInstant.ofTaiSeconds(i, j);
                assertEquals(i - 1, t.getTaiSeconds());
                assertEquals(j + 1000000000, t.getNano());
            }
            // Nanos at the top of the valid range [999_999_990, 999_999_999]: stored verbatim
            for (int j = 999999990; j < 1000000000; j++) {
                TaiInstant t = TaiInstant.ofTaiSeconds(i, j);
                assertEquals(i, t.getTaiSeconds());
                assertEquals(j, t.getNano());
            }
        }
    }

    @Test
    public void factory_ofTaiSeconds_long_long_nanosNegativeAdjusted() {
        TaiInstant test = TaiInstant.ofTaiSeconds(2L, -1);
        assertEquals(1, test.getTaiSeconds());
        assertEquals(999999999, test.getNano());
    }

    @Test
    public void factory_ofTaiSeconds_long_long_tooBig() {
        assertThrows(ArithmeticException.class, () -> TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 1000000000));
    }

    //-----------------------------------------------------------------------
    // of(Instant)
    //-----------------------------------------------------------------------

    /**
     * Converts the Unix epoch (Instant.EPOCH + 2 ns) to TAI.
     * Expected TAI seconds = (days from TAI epoch to Unix epoch) * 86400 + TAI-UTC offset at TAI epoch.
     */
    @Test
    public void factory_of_Instant() {
        TaiInstant test = TaiInstant.of(Instant.ofEpochSecond(0, 2));
        long expectedTaiSeconds = (MJD_UNIX_EPOCH - MJD_TAI_EPOCH) * SECONDS_PER_DAY + TAI_OFFSET_AT_TAI_EPOCH;
        assertEquals(expectedTaiSeconds, test.getTaiSeconds());
        assertEquals(2, test.getNano());
    }

    @Test
    public void factory_of_Instant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TaiInstant.of((Instant) null));
    }

    //-----------------------------------------------------------------------
    // of(UtcInstant)
    //-----------------------------------------------------------------------

    /**
     * Converts UtcInstants to TAI across a range of days and sub-second offsets.
     *
     * <p>For a UtcInstant at MJD {@code MJD_TAI_EPOCH + dayOffset} with nanoseconds
     * {@code secondOffset * 1_000_000_000 + 2}, the expected TAI seconds are:
     * {@code dayOffset * SECONDS_PER_DAY + secondOffset + TAI_OFFSET_AT_TAI_EPOCH}.
     */
    @Test
    public void factory_of_UtcInstant() {
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                TaiInstant test = TaiInstant.of(UtcInstant.ofModifiedJulianDay(MJD_TAI_EPOCH + i, j * 1000000000L + 2L));
                assertEquals(i * SECONDS_PER_DAY + j + TAI_OFFSET_AT_TAI_EPOCH, test.getTaiSeconds());
                assertEquals(2, test.getNano());
            }
        }
    }

    @Test
    public void factory_of_UtcInstant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TaiInstant.of((UtcInstant) null));
    }

    //-----------------------------------------------------------------------
    // parse(CharSequence)
    //-----------------------------------------------------------------------
    @Test
    public void factory_parse_CharSequence() {
        for (int i = -1000; i < 1000; i++) {
            for (int j = 900000000; j < 990000000; j += 10000000) {
                String str = i + "." + j + "s(TAI)";
                TaiInstant test = TaiInstant.parse(str);
                assertEquals(i, test.getTaiSeconds());
                assertEquals(j, test.getNano());
            }
        }
    }

    public static Object[][] data_badParse() {
        return new Object[][] {
            {"A.123456789s(TAI)"},        // non-numeric seconds part
            {"123.12345678As(TAI)"},       // non-numeric character in nanoseconds
            {"123.123456789"},             // missing 's(TAI)' suffix entirely
            {"123.123456789s"},            // incomplete suffix — missing '(TAI)'
            {"+123.123456789s(TAI)"},      // leading '+' sign not accepted
            {"-123.123s(TAI)"},            // nanoseconds must be exactly 9 digits
        };
    }

    @ParameterizedTest
    @MethodSource("data_badParse")
    public void factory_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> TaiInstant.parse(str));
    }

    @Test
    public void factory_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TaiInstant.parse(null));
    }

    //-----------------------------------------------------------------------
    // withTAISeconds()
    //-----------------------------------------------------------------------

    // Columns: initialTaiSecs, initialNanos, newTaiSecs, expectedTaiSecs, expectedNanos
    public static Object[][] data_withTAISeconds() {
        return new Object[][] {
            {0L, 12345L, 1L, 1L, 12345L},
            {0L, 12345L, -1L, -1L, 12345L},
            {7L, 12345L, 2L, 2L, 12345L},
            {7L, 12345L, -2L, -2L, 12345L},
            {-99L, 12345L, 3L, 3L, 12345L},
            {-99L, 12345L, -3L, -3L, 12345L},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withTAISeconds")
    public void test_withTAISeconds(long tai, long nanos, long newTai, Long expectedTai, Long expectedNanos) {
        TaiInstant i = TaiInstant.ofTaiSeconds(tai, nanos).withTaiSeconds(newTai);
        assertEquals(expectedTai.longValue(), i.getTaiSeconds());
        assertEquals(expectedNanos.longValue(), i.getNano());
    }

    //-----------------------------------------------------------------------
    // withNano()
    //-----------------------------------------------------------------------

    // Columns: initialTaiSecs, initialNanos, newNano, expectedTaiSecs (null = expect IllegalArgumentException), expectedNanos
    public static @Nullable Object[][] data_withNano() {
        return new @Nullable Object[][] {
            {0L, 12345L, 1, 0L, 1L},
            {7L, 12345L, 2, 7L, 2L},
            {-99L, 12345L, 3, -99L, 3L},
            {-99L, 12345L, 999999999, -99L, 999999999L},
            {-99L, 12345L, -1, null, 0L},            // negative nano → IllegalArgumentException
            {-99L, 12345L, 1000000000, null, 0L},    // nano >= 1_000_000_000 → IllegalArgumentException
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNano")
    public void test_withNano(long tai, long nanos, int newNano, @Nullable Long expectedTai, long expectedNanos) {
        TaiInstant i = TaiInstant.ofTaiSeconds(tai, nanos);
        if (expectedTai != null) {
            TaiInstant withNano = i.withNano(newNano);
            assertEquals(expectedTai.longValue(), withNano.getTaiSeconds());
            assertEquals(expectedNanos, withNano.getNano());
        } else {
            assertThrows(IllegalArgumentException.class, () -> i.withNano(newNano));
        }
    }

    //-----------------------------------------------------------------------
    // plus(Duration)
    //-----------------------------------------------------------------------

    // Columns: baseSecs, baseNanos, addSecs, addNanos, expectedSecs, expectedNanos
    public static Object[][] data_plus() {
        return new Object[][] {
            {Long.MIN_VALUE, 0, Long.MAX_VALUE, 0, -1, 0},

            {-4, 666666667, -4, 666666667, -7, 333333334},
            {-4, 666666667, -3,         0, -7, 666666667},
            {-4, 666666667, -2,         0, -6, 666666667},
            {-4, 666666667, -1,         0, -5, 666666667},
            {-4, 666666667, -1, 333333334, -4,         1},
            {-4, 666666667, -1, 666666667, -4, 333333334},
            {-4, 666666667, -1, 999999999, -4, 666666666},
            {-4, 666666667,  0,         0, -4, 666666667},
            {-4, 666666667,  0,         1, -4, 666666668},
            {-4, 666666667,  0, 333333333, -3,         0},
            {-4, 666666667,  0, 666666666, -3, 333333333},
            {-4, 666666667,  1,         0, -3, 666666667},
            {-4, 666666667,  2,         0, -2, 666666667},
            {-4, 666666667,  3,         0, -1, 666666667},
            {-4, 666666667,  3, 333333333,  0,         0},

            {-3, 0, -4, 666666667, -7, 666666667},
            {-3, 0, -3,         0, -6,         0},
            {-3, 0, -2,         0, -5,         0},
            {-3, 0, -1,         0, -4,         0},
            {-3, 0, -1, 333333334, -4, 333333334},
            {-3, 0, -1, 666666667, -4, 666666667},
            {-3, 0, -1, 999999999, -4, 999999999},
            {-3, 0,  0,         0, -3,         0},
            {-3, 0,  0,         1, -3,         1},
            {-3, 0,  0, 333333333, -3, 333333333},
            {-3, 0,  0, 666666666, -3, 666666666},
            {-3, 0,  1,         0, -2,         0},
            {-3, 0,  2,         0, -1,         0},
            {-3, 0,  3,         0,  0,         0},
            {-3, 0,  3, 333333333,  0, 333333333},

            {-2, 0, -4, 666666667, -6, 666666667},
            {-2, 0, -3,         0, -5,         0},
            {-2, 0, -2,         0, -4,         0},
            {-2, 0, -1,         0, -3,         0},
            {-2, 0, -1, 333333334, -3, 333333334},
            {-2, 0, -1, 666666667, -3, 666666667},
            {-2, 0, -1, 999999999, -3, 999999999},
            {-2, 0,  0,         0, -2,         0},
            {-2, 0,  0,         1, -2,         1},
            {-2, 0,  0, 333333333, -2, 333333333},
            {-2, 0,  0, 666666666, -2, 666666666},
            {-2, 0,  1,         0, -1,         0},
            {-2, 0,  2,         0,  0,         0},
            {-2, 0,  3,         0,  1,         0},
            {-2, 0,  3, 333333333,  1, 333333333},

            {-1, 0, -4, 666666667, -5, 666666667},
            {-1, 0, -3,         0, -4,         0},
            {-1, 0, -2,         0, -3,         0},
            {-1, 0, -1,         0, -2,         0},
            {-1, 0, -1, 333333334, -2, 333333334},
            {-1, 0, -1, 666666667, -2, 666666667},
            {-1, 0, -1, 999999999, -2, 999999999},
            {-1, 0,  0,         0, -1,         0},
            {-1, 0,  0,         1, -1,         1},
            {-1, 0,  0, 333333333, -1, 333333333},
            {-1, 0,  0, 666666666, -1, 666666666},
            {-1, 0,  1,         0,  0,         0},
            {-1, 0,  2,         0,  1,         0},
            {-1, 0,  3,         0,  2,         0},
            {-1, 0,  3, 333333333,  2, 333333333},

            {-1, 666666667, -4, 666666667, -4, 333333334},
            {-1, 666666667, -3,         0, -4, 666666667},
            {-1, 666666667, -2,         0, -3, 666666667},
            {-1, 666666667, -1,         0, -2, 666666667},
            {-1, 666666667, -1, 333333334, -1,         1},
            {-1, 666666667, -1, 666666667, -1, 333333334},
            {-1, 666666667, -1, 999999999, -1, 666666666},
            {-1, 666666667,  0,         0, -1, 666666667},
            {-1, 666666667,  0,         1, -1, 666666668},
            {-1, 666666667,  0, 333333333,  0,         0},
            {-1, 666666667,  0, 666666666,  0, 333333333},
            {-1, 666666667,  1,         0,  0, 666666667},
            {-1, 666666667,  2,         0,  1, 666666667},
            {-1, 666666667,  3,         0,  2, 666666667},
            {-1, 666666667,  3, 333333333,  3,         0},

            {0, 0, -4, 666666667, -4, 666666667},
            {0, 0, -3,         0, -3,         0},
            {0, 0, -2,         0, -2,         0},
            {0, 0, -1,         0, -1,         0},
            {0, 0, -1, 333333334, -1, 333333334},
            {0, 0, -1, 666666667, -1, 666666667},
            {0, 0, -1, 999999999, -1, 999999999},
            {0, 0,  0,         0,  0,         0},
            {0, 0,  0,         1,  0,         1},
            {0, 0,  0, 333333333,  0, 333333333},
            {0, 0,  0, 666666666,  0, 666666666},
            {0, 0,  1,         0,  1,         0},
            {0, 0,  2,         0,  2,         0},
            {0, 0,  3,         0,  3,         0},
            {0, 0,  3, 333333333,  3, 333333333},

            {0, 333333333, -4, 666666667, -3,         0},
            {0, 333333333, -3,         0, -3, 333333333},
            {0, 333333333, -2,         0, -2, 333333333},
            {0, 333333333, -1,         0, -1, 333333333},
            {0, 333333333, -1, 333333334, -1, 666666667},
            {0, 333333333, -1, 666666667,  0,         0},
            {0, 333333333, -1, 999999999,  0, 333333332},
            {0, 333333333,  0,         0,  0, 333333333},
            {0, 333333333,  0,         1,  0, 333333334},
            {0, 333333333,  0, 333333333,  0, 666666666},
            {0, 333333333,  0, 666666666,  0, 999999999},
            {0, 333333333,  1,         0,  1, 333333333},
            {0, 333333333,  2,         0,  2, 333333333},
            {0, 333333333,  3,         0,  3, 333333333},
            {0, 333333333,  3, 333333333,  3, 666666666},

            {1, 0, -4, 666666667, -3, 666666667},
            {1, 0, -3,         0, -2,         0},
            {1, 0, -2,         0, -1,         0},
            {1, 0, -1,         0,  0,         0},
            {1, 0, -1, 333333334,  0, 333333334},
            {1, 0, -1, 666666667,  0, 666666667},
            {1, 0, -1, 999999999,  0, 999999999},
            {1, 0,  0,         0,  1,         0},
            {1, 0,  0,         1,  1,         1},
            {1, 0,  0, 333333333,  1, 333333333},
            {1, 0,  0, 666666666,  1, 666666666},
            {1, 0,  1,         0,  2,         0},
            {1, 0,  2,         0,  3,         0},
            {1, 0,  3,         0,  4,         0},
            {1, 0,  3, 333333333,  4, 333333333},

            {2, 0, -4, 666666667, -2, 666666667},
            {2, 0, -3,         0, -1,         0},
            {2, 0, -2,         0,  0,         0},
            {2, 0, -1,         0,  1,         0},
            {2, 0, -1, 333333334,  1, 333333334},
            {2, 0, -1, 666666667,  1, 666666667},
            {2, 0, -1, 999999999,  1, 999999999},
            {2, 0,  0,         0,  2,         0},
            {2, 0,  0,         1,  2,         1},
            {2, 0,  0, 333333333,  2, 333333333},
            {2, 0,  0, 666666666,  2, 666666666},
            {2, 0,  1,         0,  3,         0},
            {2, 0,  2,         0,  4,         0},
            {2, 0,  3,         0,  5,         0},
            {2, 0,  3, 333333333,  5, 333333333},

            {3, 0, -4, 666666667, -1, 666666667},
            {3, 0, -3,         0,  0,         0},
            {3, 0, -2,         0,  1,         0},
            {3, 0, -1,         0,  2,         0},
            {3, 0, -1, 333333334,  2, 333333334},
            {3, 0, -1, 666666667,  2, 666666667},
            {3, 0, -1, 999999999,  2, 999999999},
            {3, 0,  0,         0,  3,         0},
            {3, 0,  0,         1,  3,         1},
            {3, 0,  0, 333333333,  3, 333333333},
            {3, 0,  0, 666666666,  3, 666666666},
            {3, 0,  1,         0,  4,         0},
            {3, 0,  2,         0,  5,         0},
            {3, 0,  3,         0,  6,         0},
            {3, 0,  3, 333333333,  6, 333333333},

            {3, 333333333, -4, 666666667,  0,         0},
            {3, 333333333, -3,         0,  0, 333333333},
            {3, 333333333, -2,         0,  1, 333333333},
            {3, 333333333, -1,         0,  2, 333333333},
            {3, 333333333, -1, 333333334,  2, 666666667},
            {3, 333333333, -1, 666666667,  3,         0},
            {3, 333333333, -1, 999999999,  3, 333333332},
            {3, 333333333,  0,         0,  3, 333333333},
            {3, 333333333,  0,         1,  3, 333333334},
            {3, 333333333,  0, 333333333,  3, 666666666},
            {3, 333333333,  0, 666666666,  3, 999999999},
            {3, 333333333,  1,         0,  4, 333333333},
            {3, 333333333,  2,         0,  5, 333333333},
            {3, 333333333,  3,         0,  6, 333333333},
            {3, 333333333,  3, 333333333,  6, 666666666},

            {Long.MAX_VALUE, 0, Long.MIN_VALUE, 0, -1, 0},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus(long seconds, int nanos, long plusSeconds, int plusNanos, long expectedSeconds, int expectedNanoOfSecond) {
        TaiInstant i = TaiInstant.ofTaiSeconds(seconds, nanos).plus(Duration.ofSeconds(plusSeconds, plusNanos));
        assertEquals(expectedSeconds, i.getTaiSeconds());
        assertEquals(expectedNanoOfSecond, i.getNano());
    }

    @Test
    public void test_plus_overflowTooBig() {
        TaiInstant i = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);
        assertThrows(ArithmeticException.class, () -> i.plus(Duration.ofSeconds(0, 1)));
    }

    @Test
    public void test_plus_overflowTooSmall() {
        TaiInstant i = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> i.plus(Duration.ofSeconds(-1, 999999999)));
    }

    //-----------------------------------------------------------------------
    // minus(Duration)
    //-----------------------------------------------------------------------

    // Columns: baseSecs, baseNanos, subtractSecs, subtractNanos, expectedSecs, expectedNanos
    public static Object[][] data_minus() {
        return new Object[][] {
            {Long.MIN_VALUE, 0, Long.MIN_VALUE + 1, 0, -1, 0},

            {-4, 666666667, -4, 666666667,  0,         0},
            {-4, 666666667, -3,         0, -1, 666666667},
            {-4, 666666667, -2,         0, -2, 666666667},
            {-4, 666666667, -1,         0, -3, 666666667},
            {-4, 666666667, -1, 333333334, -3, 333333333},
            {-4, 666666667, -1, 666666667, -3,         0},
            {-4, 666666667, -1, 999999999, -4, 666666668},
            {-4, 666666667,  0,         0, -4, 666666667},
            {-4, 666666667,  0,         1, -4, 666666666},
            {-4, 666666667,  0, 333333333, -4, 333333334},
            {-4, 666666667,  0, 666666666, -4,         1},
            {-4, 666666667,  1,         0, -5, 666666667},
            {-4, 666666667,  2,         0, -6, 666666667},
            {-4, 666666667,  3,         0, -7, 666666667},
            {-4, 666666667,  3, 333333333, -7, 333333334},

            {-3, 0, -4, 666666667,  0, 333333333},
            {-3, 0, -3,         0,  0,         0},
            {-3, 0, -2,         0, -1,         0},
            {-3, 0, -1,         0, -2,         0},
            {-3, 0, -1, 333333334, -3, 666666666},
            {-3, 0, -1, 666666667, -3, 333333333},
            {-3, 0, -1, 999999999, -3,         1},
            {-3, 0,  0,         0, -3,         0},
            {-3, 0,  0,         1, -4, 999999999},
            {-3, 0,  0, 333333333, -4, 666666667},
            {-3, 0,  0, 666666666, -4, 333333334},
            {-3, 0,  1,         0, -4,         0},
            {-3, 0,  2,         0, -5,         0},
            {-3, 0,  3,         0, -6,         0},
            {-3, 0,  3, 333333333, -7, 666666667},

            {-2, 0, -4, 666666667,  1, 333333333},
            {-2, 0, -3,         0,  1,         0},
            {-2, 0, -2,         0,  0,         0},
            {-2, 0, -1,         0, -1,         0},
            {-2, 0, -1, 333333334, -2, 666666666},
            {-2, 0, -1, 666666667, -2, 333333333},
            {-2, 0, -1, 999999999, -2,         1},
            {-2, 0,  0,         0, -2,         0},
            {-2, 0,  0,         1, -3, 999999999},
            {-2, 0,  0, 333333333, -3, 666666667},
            {-2, 0,  0, 666666666, -3, 333333334},
            {-2, 0,  1,         0, -3,         0},
            {-2, 0,  2,         0, -4,         0},
            {-2, 0,  3,         0, -5,         0},
            {-2, 0,  3, 333333333, -6, 666666667},

            {-1, 0, -4, 666666667,  2, 333333333},
            {-1, 0, -3,         0,  2,         0},
            {-1, 0, -2,         0,  1,         0},
            {-1, 0, -1,         0,  0,         0},
            {-1, 0, -1, 333333334, -1, 666666666},
            {-1, 0, -1, 666666667, -1, 333333333},
            {-1, 0, -1, 999999999, -1,         1},
            {-1, 0,  0,         0, -1,         0},
            {-1, 0,  0,         1, -2, 999999999},
            {-1, 0,  0, 333333333, -2, 666666667},
            {-1, 0,  0, 666666666, -2, 333333334},
            {-1, 0,  1,         0, -2,         0},
            {-1, 0,  2,         0, -3,         0},
            {-1, 0,  3,         0, -4,         0},
            {-1, 0,  3, 333333333, -5, 666666667},

            {-1, 666666667, -4, 666666667,  3,         0},
            {-1, 666666667, -3,         0,  2, 666666667},
            {-1, 666666667, -2,         0,  1, 666666667},
            {-1, 666666667, -1,         0,  0, 666666667},
            {-1, 666666667, -1, 333333334,  0, 333333333},
            {-1, 666666667, -1, 666666667,  0,         0},
            {-1, 666666667, -1, 999999999, -1, 666666668},
            {-1, 666666667,  0,         0, -1, 666666667},
            {-1, 666666667,  0,         1, -1, 666666666},
            {-1, 666666667,  0, 333333333, -1, 333333334},
            {-1, 666666667,  0, 666666666, -1,         1},
            {-1, 666666667,  1,         0, -2, 666666667},
            {-1, 666666667,  2,         0, -3, 666666667},
            {-1, 666666667,  3,         0, -4, 666666667},
            {-1, 666666667,  3, 333333333, -4, 333333334},

            {0, 0, -4, 666666667,  3, 333333333},
            {0, 0, -3,         0,  3,         0},
            {0, 0, -2,         0,  2,         0},
            {0, 0, -1,         0,  1,         0},
            {0, 0, -1, 333333334,  0, 666666666},
            {0, 0, -1, 666666667,  0, 333333333},
            {0, 0, -1, 999999999,  0,         1},
            {0, 0,  0,         0,  0,         0},
            {0, 0,  0,         1, -1, 999999999},
            {0, 0,  0, 333333333, -1, 666666667},
            {0, 0,  0, 666666666, -1, 333333334},
            {0, 0,  1,         0, -1,         0},
            {0, 0,  2,         0, -2,         0},
            {0, 0,  3,         0, -3,         0},
            {0, 0,  3, 333333333, -4, 666666667},

            {0, 333333333, -4, 666666667,  3, 666666666},
            {0, 333333333, -3,         0,  3, 333333333},
            {0, 333333333, -2,         0,  2, 333333333},
            {0, 333333333, -1,         0,  1, 333333333},
            {0, 333333333, -1, 333333334,  0, 999999999},
            {0, 333333333, -1, 666666667,  0, 666666666},
            {0, 333333333, -1, 999999999,  0, 333333334},
            {0, 333333333,  0,         0,  0, 333333333},
            {0, 333333333,  0,         1,  0, 333333332},
            {0, 333333333,  0, 333333333,  0,         0},
            {0, 333333333,  0, 666666666, -1, 666666667},
            {0, 333333333,  1,         0, -1, 333333333},
            {0, 333333333,  2,         0, -2, 333333333},
            {0, 333333333,  3,         0, -3, 333333333},
            {0, 333333333,  3, 333333333, -3,         0},

            {1, 0, -4, 666666667,  4, 333333333},
            {1, 0, -3,         0,  4,         0},
            {1, 0, -2,         0,  3,         0},
            {1, 0, -1,         0,  2,         0},
            {1, 0, -1, 333333334,  1, 666666666},
            {1, 0, -1, 666666667,  1, 333333333},
            {1, 0, -1, 999999999,  1,         1},
            {1, 0,  0,         0,  1,         0},
            {1, 0,  0,         1,  0, 999999999},
            {1, 0,  0, 333333333,  0, 666666667},
            {1, 0,  0, 666666666,  0, 333333334},
            {1, 0,  1,         0,  0,         0},
            {1, 0,  2,         0, -1,         0},
            {1, 0,  3,         0, -2,         0},
            {1, 0,  3, 333333333, -3, 666666667},

            {2, 0, -4, 666666667,  5, 333333333},
            {2, 0, -3,         0,  5,         0},
            {2, 0, -2,         0,  4,         0},
            {2, 0, -1,         0,  3,         0},
            {2, 0, -1, 333333334,  2, 666666666},
            {2, 0, -1, 666666667,  2, 333333333},
            {2, 0, -1, 999999999,  2,         1},
            {2, 0,  0,         0,  2,         0},
            {2, 0,  0,         1,  1, 999999999},
            {2, 0,  0, 333333333,  1, 666666667},
            {2, 0,  0, 666666666,  1, 333333334},
            {2, 0,  1,         0,  1,         0},
            {2, 0,  2,         0,  0,         0},
            {2, 0,  3,         0, -1,         0},
            {2, 0,  3, 333333333, -2, 666666667},

            {3, 0, -4, 666666667,  6, 333333333},
            {3, 0, -3,         0,  6,         0},
            {3, 0, -2,         0,  5,         0},
            {3, 0, -1,         0,  4,         0},
            {3, 0, -1, 333333334,  3, 666666666},
            {3, 0, -1, 666666667,  3, 333333333},
            {3, 0, -1, 999999999,  3,         1},
            {3, 0,  0,         0,  3,         0},
            {3, 0,  0,         1,  2, 999999999},
            {3, 0,  0, 333333333,  2, 666666667},
            {3, 0,  0, 666666666,  2, 333333334},
            {3, 0,  1,         0,  2,         0},
            {3, 0,  2,         0,  1,         0},
            {3, 0,  3,         0,  0,         0},
            {3, 0,  3, 333333333, -1, 666666667},

            {3, 333333333, -4, 666666667,  6, 666666666},
            {3, 333333333, -3,         0,  6, 333333333},
            {3, 333333333, -2,         0,  5, 333333333},
            {3, 333333333, -1,         0,  4, 333333333},
            {3, 333333333, -1, 333333334,  3, 999999999},
            {3, 333333333, -1, 666666667,  3, 666666666},
            {3, 333333333, -1, 999999999,  3, 333333334},
            {3, 333333333,  0,         0,  3, 333333333},
            {3, 333333333,  0,         1,  3, 333333332},
            {3, 333333333,  0, 333333333,  3,         0},
            {3, 333333333,  0, 666666666,  2, 666666667},
            {3, 333333333,  1,         0,  2, 333333333},
            {3, 333333333,  2,         0,  1, 333333333},
            {3, 333333333,  3,         0,  0, 333333333},
            {3, 333333333,  3, 333333333,  0,         0},

            {Long.MAX_VALUE, 0, Long.MAX_VALUE, 0, 0, 0},
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus(long seconds, int nanos, long minusSeconds, int minusNanos, long expectedSeconds, int expectedNanoOfSecond) {
        TaiInstant i = TaiInstant.ofTaiSeconds(seconds, nanos).minus(Duration.ofSeconds(minusSeconds, minusNanos));
        assertEquals(expectedSeconds, i.getTaiSeconds());
        assertEquals(expectedNanoOfSecond, i.getNano());
    }

    @Test
    public void test_minus_overflowTooSmall() {
        TaiInstant i = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> i.minus(Duration.ofSeconds(0, 1)));
    }

    @Test
    public void test_minus_overflowTooBig() {
        TaiInstant i = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);
        assertThrows(ArithmeticException.class, () -> i.minus(Duration.ofSeconds(-1, 999999999)));
    }

    //-----------------------------------------------------------------------
    // durationUntil()
    //-----------------------------------------------------------------------
    @Test
    public void test_durationUntil_fifteenSeconds() {
        TaiInstant tai1 = TaiInstant.ofTaiSeconds(10, 0);
        TaiInstant tai2 = TaiInstant.ofTaiSeconds(25, 0);
        Duration test = tai1.durationUntil(tai2);
        assertEquals(15, test.getSeconds());
        assertEquals(0, test.getNano());
    }

    @Test
    public void test_durationUntil_twoNanos() {
        TaiInstant tai1 = TaiInstant.ofTaiSeconds(4, 5);
        TaiInstant tai2 = TaiInstant.ofTaiSeconds(4, 7);
        Duration test = tai1.durationUntil(tai2);
        assertEquals(0, test.getSeconds());
        assertEquals(2, test.getNano());
    }

    @Test
    public void test_durationUntil_twoNanosNegative() {
        TaiInstant tai1 = TaiInstant.ofTaiSeconds(4, 9);
        TaiInstant tai2 = TaiInstant.ofTaiSeconds(4, 7);
        Duration test = tai1.durationUntil(tai2);
        assertEquals(-1, test.getSeconds());
        assertEquals(999999998, test.getNano());
    }

    //-----------------------------------------------------------------------
    // toUtcInstant()
    //-----------------------------------------------------------------------

    /**
     * Verifies that a TAI instant round-trips to the expected UtcInstant.
     *
     * <p>For TAI second {@code dayOffset * SECONDS_PER_DAY + secondOffset + TAI_OFFSET_AT_TAI_EPOCH}
     * with nano 2, the corresponding UTC instant is MJD {@code MJD_TAI_EPOCH + dayOffset},
     * nanoseconds {@code secondOffset * 1_000_000_000 + 2}.
     */
    @Test
    public void test_toUtcInstant() {
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(MJD_TAI_EPOCH + i, j * 1000000000L + 2L);
                TaiInstant test = TaiInstant.ofTaiSeconds(i * SECONDS_PER_DAY + j + TAI_OFFSET_AT_TAI_EPOCH, 2);
                assertEquals(expected, test.toUtcInstant());
            }
        }
    }

    //-----------------------------------------------------------------------
    // toInstant()
    //-----------------------------------------------------------------------

    /**
     * Verifies that a TAI instant converts to the expected {@link java.time.Instant}.
     *
     * <p>{@code UNIX_SECONDS_AT_TAI_EPOCH} is the Unix timestamp of 1958-01-01 (the TAI epoch),
     * equal to {@code -(MJD_UNIX_EPOCH - MJD_TAI_EPOCH) * SECONDS_PER_DAY = -378691200}.
     */
    @Test
    public void test_toInstant() {
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                Instant expected = Instant.ofEpochSecond(UNIX_SECONDS_AT_TAI_EPOCH + i * SECONDS_PER_DAY + j).plusNanos(2);
                TaiInstant test = TaiInstant.ofTaiSeconds(i * SECONDS_PER_DAY + j + TAI_OFFSET_AT_TAI_EPOCH, 2);
                assertEquals(expected, test.toInstant());
            }
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_comparisons() {
        assertChronologicalOrder(
            TaiInstant.ofTaiSeconds(-2L, 0),
            TaiInstant.ofTaiSeconds(-2L, 999999998),
            TaiInstant.ofTaiSeconds(-2L, 999999999),
            TaiInstant.ofTaiSeconds(-1L, 0),
            TaiInstant.ofTaiSeconds(-1L, 1),
            TaiInstant.ofTaiSeconds(-1L, 999999998),
            TaiInstant.ofTaiSeconds(-1L, 999999999),
            TaiInstant.ofTaiSeconds(0L, 0),
            TaiInstant.ofTaiSeconds(0L, 1),
            TaiInstant.ofTaiSeconds(0L, 2),
            TaiInstant.ofTaiSeconds(0L, 999999999),
            TaiInstant.ofTaiSeconds(1L, 0),
            TaiInstant.ofTaiSeconds(2L, 0)
        );
    }

    /**
     * Asserts that the supplied instants are in strict chronological (ascending) order by
     * verifying {@code compareTo}, {@code equals}, {@code isBefore}, and {@code isAfter}
     * for every pair.
     */
    void assertChronologicalOrder(TaiInstant... instants) {
        for (int i = 0; i < instants.length; i++) {
            TaiInstant a = instants[i];
            for (int j = 0; j < instants.length; j++) {
                TaiInstant b = instants[j];
                if (i < j) {
                    assertTrue(a.compareTo(b) < 0);
                    assertFalse(a.equals(b));
                    assertTrue(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                } else if (i > j) {
                    assertTrue(a.compareTo(b) > 0);
                    assertFalse(a.equals(b));
                    assertFalse(a.isBefore(b));
                    assertTrue(a.isAfter(b));
                } else {
                    assertEquals(0, a.compareTo(b));
                    assertTrue(a.equals(b));
                    assertFalse(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                }
            }
        }
    }

    @Test
    public void test_compareTo_ObjectNull() {
        TaiInstant a = TaiInstant.ofTaiSeconds(0L, 0);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> a.compareTo(null));
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void test_compareToNonTaiInstant() {
        Comparable c = TaiInstant.ofTaiSeconds(0L, 2);
        assertThrows(ClassCastException.class, () -> c.compareTo(new Object()));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(TaiInstant.ofTaiSeconds(5L, 20), TaiInstant.ofTaiSeconds(5L, 20))
            .addEqualityGroup(TaiInstant.ofTaiSeconds(5L, 30), TaiInstant.ofTaiSeconds(5L, 30))
            .addEqualityGroup(TaiInstant.ofTaiSeconds(6L, 20), TaiInstant.ofTaiSeconds(6L, 20))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString_standard() {
        TaiInstant t = TaiInstant.ofTaiSeconds(123L, 123456789);
        assertEquals("123.123456789s(TAI)", t.toString());
    }

    @Test
    public void test_toString_negative() {
        TaiInstant t = TaiInstant.ofTaiSeconds(-123L, 123456789);
        assertEquals("-123.123456789s(TAI)", t.toString());
    }

    @Test
    public void test_toString_zeroDecimal() {
        TaiInstant t = TaiInstant.ofTaiSeconds(0L, 567);
        assertEquals("0.000000567s(TAI)", t.toString());
    }

}

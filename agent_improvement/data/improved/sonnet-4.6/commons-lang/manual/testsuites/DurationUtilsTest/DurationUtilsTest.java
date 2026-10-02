/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.lang3.time;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

/**
 * Tests {@link DurationUtils}.
 */
class DurationUtilsTest extends AbstractLangTest {

    // System-property keys used by the get*() tests; values must match the @SetSystemProperty annotations.
    private static final String PROP_ONE         = "Seconds1"; // value = 1
    private static final String PROP_LONG_MAX    = "Seconds2"; // value = Long.MAX_VALUE

    // -------------------------------------------------------------------------
    // get() / getMillis() / getSeconds()
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("get() / getMillis() / getSeconds() — read Duration from a system property")
    class GetFromSystemPropertyTests {

        @Test
        @DisplayName("get(key, ChronoUnit.SECONDS, def) — null/empty key returns default; valid key returns parsed seconds")
        @SetSystemProperties({
            @SetSystemProperty(key = "Seconds1", value = "1"),
            @SetSystemProperty(key = "Seconds2", value = "9223372036854775807") }) // Long.MAX_VALUE
        void testGetSeconds() {
            assertEquals(Duration.ofSeconds(0),            DurationUtils.get(null,          ChronoUnit.SECONDS, 0));
            assertEquals(Duration.ofSeconds(0),            DurationUtils.get("",            ChronoUnit.SECONDS, 0));
            assertEquals(Duration.ofSeconds(1),            DurationUtils.get(PROP_ONE,      ChronoUnit.SECONDS, 0));
            assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.get(PROP_LONG_MAX, ChronoUnit.SECONDS, 0));
        }

        @Test
        @DisplayName("get(key, ChronoUnit.MILLIS, def) — null/empty key returns default; valid key returns parsed millis")
        @SetSystemProperties({
            @SetSystemProperty(key = "Seconds1", value = "1"),
            @SetSystemProperty(key = "Seconds2", value = "9223372036854775807") }) // Long.MAX_VALUE
        void testGetMillis() {
            assertEquals(Duration.ofMillis(0),            DurationUtils.get(null,          ChronoUnit.MILLIS, 0));
            assertEquals(Duration.ofMillis(0),            DurationUtils.get("",            ChronoUnit.MILLIS, 0));
            assertEquals(Duration.ofMillis(1),            DurationUtils.get(PROP_ONE,      ChronoUnit.MILLIS, 0));
            assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.get(PROP_LONG_MAX, ChronoUnit.MILLIS, 0));
        }

        @Test
        @DisplayName("getMillis() — convenience overload that always uses milliseconds unit")
        @SetSystemProperties({
            @SetSystemProperty(key = "Seconds1", value = "1"),
            @SetSystemProperty(key = "Seconds2", value = "9223372036854775807") }) // Long.MAX_VALUE
        void testGetMilliseconds() {
            assertEquals(Duration.ofMillis(0),            DurationUtils.getMillis(null,          0));
            assertEquals(Duration.ofMillis(0),            DurationUtils.getMillis("",            0));
            assertEquals(Duration.ofMillis(1),            DurationUtils.getMillis(PROP_ONE,      0));
            assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.getMillis(PROP_LONG_MAX, 0));
        }

        @Test
        @DisplayName("getSeconds() — convenience overload that always uses seconds unit")
        @SetSystemProperties({
            @SetSystemProperty(key = "Seconds1", value = "1"),
            @SetSystemProperty(key = "Seconds2", value = "9223372036854775807") }) // Long.MAX_VALUE
        void testGetSecondsConvenience() {
            assertEquals(Duration.ofSeconds(0),            DurationUtils.getSeconds(null,          0));
            assertEquals(Duration.ofSeconds(0),            DurationUtils.getSeconds("",            0));
            assertEquals(Duration.ofSeconds(1),            DurationUtils.getSeconds(PROP_ONE,      0));
            assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.getSeconds(PROP_LONG_MAX, 0));
        }
    }

    // -------------------------------------------------------------------------
    // getNanosOfMilli() / getNanosOfMiili() (deprecated)
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("getNanosOfMilli() — sub-millisecond nanosecond remainder of a Duration")
    class GetNanosOfMilliTests {

        @Test
        @DisplayName("returns nanos modulo 1,000,000 — null treated as zero")
        void testGetNanosOfMilli() {
            assertEquals(0,       DurationUtils.getNanosOfMilli(null));
            assertEquals(0,       DurationUtils.getNanosOfMilli(Duration.ZERO));
            assertEquals(1,       DurationUtils.getNanosOfMilli(Duration.ofNanos(1)));
            assertEquals(10,      DurationUtils.getNanosOfMilli(Duration.ofNanos(10)));
            assertEquals(100,     DurationUtils.getNanosOfMilli(Duration.ofNanos(100)));
            assertEquals(1_000,   DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000)));
            assertEquals(10_000,  DurationUtils.getNanosOfMilli(Duration.ofNanos(10_000)));
            assertEquals(100_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(100_000)));
            // exactly 1 ms worth of nanos wraps back to 0
            assertEquals(0,       DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_000)));
            assertEquals(1,       DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_001)));
        }

        @Test
        @DisplayName("deprecated getNanosOfMiili() delegates to getNanosOfMilli() — same results")
        @SuppressWarnings("deprecation")
        void testGetNanosOfMiili_deprecated() {
            assertEquals(0,       DurationUtils.getNanosOfMiili(null));
            assertEquals(0,       DurationUtils.getNanosOfMiili(Duration.ZERO));
            assertEquals(1,       DurationUtils.getNanosOfMiili(Duration.ofNanos(1)));
            assertEquals(10,      DurationUtils.getNanosOfMiili(Duration.ofNanos(10)));
            assertEquals(100,     DurationUtils.getNanosOfMiili(Duration.ofNanos(100)));
            assertEquals(1_000,   DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000)));
            assertEquals(10_000,  DurationUtils.getNanosOfMiili(Duration.ofNanos(10_000)));
            assertEquals(100_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(100_000)));
            assertEquals(0,       DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_000)));
            assertEquals(1,       DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_001)));
        }
    }

    // -------------------------------------------------------------------------
    // isPositive()
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("isPositive() — false for zero and negative durations, true for positive")
    void testIsPositive() {
        assertFalse(DurationUtils.isPositive(Duration.ZERO));
        assertFalse(DurationUtils.isPositive(Duration.ofMillis(-1)));
        assertTrue(DurationUtils.isPositive(Duration.ofMillis(1)));
    }

    // -------------------------------------------------------------------------
    // LONG_TO_INT_RANGE
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("LONG_TO_INT_RANGE.fit() — clamps longs to [Integer.MIN_VALUE, Integer.MAX_VALUE]")
    void testLongToIntRangeFit() {
        assertEquals(0, DurationUtils.LONG_TO_INT_RANGE.fit(0L));

        // Values at and beyond Integer.MIN_VALUE boundary
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 1));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 2));

        // Values at and beyond Integer.MAX_VALUE boundary
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 1));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 2));

        // Full long extremes are clamped to int extremes
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MIN_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MAX_VALUE));

        // Values within short range pass through unchanged
        assertEquals(Short.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MIN_VALUE));
        assertEquals(Short.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MAX_VALUE));
    }

    // -------------------------------------------------------------------------
    // of() — measure elapsed Duration of a lambda
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("of() — measures elapsed Duration of a lambda execution")
    class OfTests {

        @Test
        @DisplayName("of(Consumer<Instant>) — start instant is before now; returned duration is non-negative")
        void testOfConsumer() {
            // The Consumer receives the captured start Instant; assert it is at or before the current time
            assertTrue(DurationUtils.of(start -> assertTrue(start.compareTo(Instant.now()) <= 0)).compareTo(Duration.ZERO) >= 0);

            // The start Instant passed to the consumer must not be before the Instant captured just before the call
            final Instant before = Instant.now();
            DurationUtils.of(start -> assertTrue(start.compareTo(before) >= 0));
        }

        @Test
        @DisplayName("of(FailableRunnable) — returned duration is non-negative")
        void testOfRunnable() {
            assertTrue(DurationUtils.of(DurationUtilsTest.this::testSince).compareTo(Duration.ZERO) >= 0);
        }

        @Test
        @DisplayName("of(FailableRunnable) — propagates checked exceptions thrown by the runnable")
        void testOfRunnableThrowing() {
            assertThrows(IOException.class, () -> DurationUtils.of(() -> {
                throw new IOException();
            }));
        }
    }

    // -------------------------------------------------------------------------
    // since()
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("since() — duration from EPOCH/MIN is non-negative; from Instant.MAX is non-positive")
    void testSince() {
        assertTrue(DurationUtils.since(Instant.EPOCH).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MIN).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MAX).compareTo(Duration.ZERO) <= 0);
    }

    // -------------------------------------------------------------------------
    // toDuration()
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("toDuration() — converts every TimeUnit to the equivalent Duration")
    void testToDuration() {
        assertEquals(Duration.ofDays(1),    DurationUtils.toDuration(1,     TimeUnit.DAYS));
        assertEquals(Duration.ofHours(1),   DurationUtils.toDuration(1,     TimeUnit.HOURS));
        assertEquals(Duration.ofMillis(1),  DurationUtils.toDuration(1_000, TimeUnit.MICROSECONDS));
        assertEquals(Duration.ofMillis(1),  DurationUtils.toDuration(1,     TimeUnit.MILLISECONDS));
        assertEquals(Duration.ofMinutes(1), DurationUtils.toDuration(1,     TimeUnit.MINUTES));
        assertEquals(Duration.ofNanos(1),   DurationUtils.toDuration(1,     TimeUnit.NANOSECONDS));
        assertEquals(Duration.ofSeconds(1), DurationUtils.toDuration(1,     TimeUnit.SECONDS));
        assertEquals( 1, DurationUtils.toDuration( 1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(-1, DurationUtils.toDuration(-1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals( 0, DurationUtils.toDuration( 0, TimeUnit.SECONDS).toMillis());
    }

    // -------------------------------------------------------------------------
    // toMillisInt()
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("toMillisInt() — converts Duration to milliseconds clamped to int range")
    class ToMillisIntTests {

        @Test
        @DisplayName("typical values and boundary clamping within long range")
        void testToMillisInt() {
            assertEquals(0,  DurationUtils.toMillisInt(Duration.ZERO));
            assertEquals(1,  DurationUtils.toMillisInt(Duration.ofMillis(1)));
            assertEquals(-1, DurationUtils.toMillisInt(Duration.ofMillis(-1)));

            // Exact int boundaries pass through unchanged
            assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MIN_VALUE)));
            assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MAX_VALUE)));

            // Values just beyond int max are clamped to Integer.MAX_VALUE
            assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 1)));
            assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 2)));

            // Values just beyond int min are clamped to Integer.MIN_VALUE
            assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 1)));
            assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 2)));

            // Nanosecond-based durations at long extremes are also clamped
            assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MIN_VALUE)));
            assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MAX_VALUE)));
        }

        @Test
        @DisplayName("null Duration throws NullPointerException")
        void testToMillisIntNullDuration() {
            assertNullPointerException(() -> DurationUtils.toMillisInt(null));
        }

        @Test
        @DisplayName("Duration whose ms value overflows Long.MAX_VALUE clamps to Integer.MAX_VALUE")
        void testToMillisIntOverflowToMaxInteger() {
            // Duration.ofSeconds(Long.MAX_VALUE / 1000 + 1) will overflow when toMillis() is called
            assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofSeconds(Long.MAX_VALUE / 1000 + 1)));
        }

        @Test
        @DisplayName("Duration whose ms value underflows Long.MIN_VALUE clamps to Integer.MIN_VALUE")
        void testToMillisIntUnderflowToMinInteger() {
            assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofSeconds(Long.MIN_VALUE / 1000 - 1)));
        }
    }

    // -------------------------------------------------------------------------
    // toMillisLong()
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("toMillisLong() — converts Duration to milliseconds clamped to long range")
    class ToMillisLongTests {

        @Test
        @DisplayName("typical values and long-range boundary clamping without ArithmeticException")
        void testToMillisLong() {
            assertEquals(0,             DurationUtils.toMillisLong(Duration.ZERO));
            assertEquals(1,             DurationUtils.toMillisLong(Duration.ofMillis(1)));
            assertEquals(-1,            DurationUtils.toMillisLong(Duration.ofMillis(-1)));
            assertEquals(Long.MIN_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MIN_VALUE)));
            assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MAX_VALUE)));
            // Duration in seconds that overflows long ms is clamped to Long.MAX_VALUE
            assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofSeconds(Long.MAX_VALUE)));
        }

        @Test
        @DisplayName("null Duration throws NullPointerException")
        void testToMillisLongNullDuration() {
            assertNullPointerException(() -> DurationUtils.toMillisLong(null));
        }
    }

    // -------------------------------------------------------------------------
    // zeroIfNull()
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("zeroIfNull() — returns Duration.ZERO for null, otherwise the given duration unchanged")
    void testZeroIfNull() {
        assertEquals(Duration.ZERO,      DurationUtils.zeroIfNull(null));
        assertEquals(Duration.ofDays(1), DurationUtils.zeroIfNull(Duration.ofDays(1)));
    }
}

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
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

/**
 * Tests {@link DurationUtils}.
 */
class DurationUtilsTest extends AbstractLangTest {

    /** Name of the system property seeded with the value {@code "1"}. */
    private static final String PROP_ONE = "Seconds1";

    /** Name of the system property seeded with {@link Long#MAX_VALUE}. */
    private static final String PROP_MAX = "Seconds2";

    /**
     * {@link Long#MAX_VALUE} expressed as a string so it can be used both as an annotation
     * value (which requires a compile-time constant) and referenced in the assertions below.
     */
    private static final String LONG_MAX_VALUE_TEXT = "9223372036854775807";

    /** Default value supplied to the {@code get*} lookups so the property value is always used. */
    private static final long NO_DEFAULT = 0;

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = PROP_ONE, value = "1"),
        @SetSystemProperty(key = PROP_MAX, value = LONG_MAX_VALUE_TEXT) })
    void testGet() {
        // A null or empty key falls back to the default; a present key parses the property value.
        // ChronoUnit.SECONDS
        assertEquals(Duration.ofSeconds(0), DurationUtils.get(null, ChronoUnit.SECONDS, NO_DEFAULT));
        assertEquals(Duration.ofSeconds(0), DurationUtils.get("", ChronoUnit.SECONDS, NO_DEFAULT));
        assertEquals(Duration.ofSeconds(1), DurationUtils.get(PROP_ONE, ChronoUnit.SECONDS, NO_DEFAULT));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.get(PROP_MAX, ChronoUnit.SECONDS, NO_DEFAULT));
        // ChronoUnit.MILLIS
        assertEquals(Duration.ofMillis(0), DurationUtils.get(null, ChronoUnit.MILLIS, NO_DEFAULT));
        assertEquals(Duration.ofMillis(0), DurationUtils.get("", ChronoUnit.MILLIS, NO_DEFAULT));
        assertEquals(Duration.ofMillis(1), DurationUtils.get(PROP_ONE, ChronoUnit.MILLIS, NO_DEFAULT));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.get(PROP_MAX, ChronoUnit.MILLIS, NO_DEFAULT));
    }

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = PROP_ONE, value = "1"),
        @SetSystemProperty(key = PROP_MAX, value = LONG_MAX_VALUE_TEXT) })
    void testGetMilliseconds() {
        // A null or empty key yields the default; a present key is read as milliseconds.
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis(null, NO_DEFAULT));
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis("", NO_DEFAULT));
        assertEquals(Duration.ofMillis(1), DurationUtils.getMillis(PROP_ONE, NO_DEFAULT));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.getMillis(PROP_MAX, NO_DEFAULT));
    }

    @Test
    void testGetNanosOfMiili() {
        // Deprecated spelling; must behave identically to getNanosOfMilli (see testGetNanosOfMilli).
        // The result is the sub-millisecond nanosecond remainder, i.e. nanos % 1_000_000.
        assertEquals(0, DurationUtils.getNanosOfMiili(null));
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ZERO));
        assertEquals(1, DurationUtils.getNanosOfMiili(Duration.ofNanos(1)));
        assertEquals(10, DurationUtils.getNanosOfMiili(Duration.ofNanos(10)));
        assertEquals(100, DurationUtils.getNanosOfMiili(Duration.ofNanos(100)));
        assertEquals(1_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000)));
        assertEquals(10_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(10_000)));
        assertEquals(100_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(100_000)));
        // Exactly one millisecond leaves no remainder; one nanosecond past it leaves 1.
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_000)));
        assertEquals(1, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_001)));
    }

    @Test
    void testGetNanosOfMilli() {
        // Returns the sub-millisecond nanosecond remainder, i.e. nanos % 1_000_000.
        assertEquals(0, DurationUtils.getNanosOfMilli(null));
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ZERO));
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1)));
        assertEquals(10, DurationUtils.getNanosOfMilli(Duration.ofNanos(10)));
        assertEquals(100, DurationUtils.getNanosOfMilli(Duration.ofNanos(100)));
        assertEquals(1_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000)));
        assertEquals(10_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(10_000)));
        assertEquals(100_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(100_000)));
        // Exactly one millisecond leaves no remainder; one nanosecond past it leaves 1.
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_000)));
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_001)));
    }

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = PROP_ONE, value = "1"),
        @SetSystemProperty(key = PROP_MAX, value = LONG_MAX_VALUE_TEXT) })
    void testGetSeconds() {
        // A null or empty key yields the default; a present key is read as seconds.
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds(null, NO_DEFAULT));
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds("", NO_DEFAULT));
        assertEquals(Duration.ofSeconds(1), DurationUtils.getSeconds(PROP_ONE, NO_DEFAULT));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.getSeconds(PROP_MAX, NO_DEFAULT));
    }

    @Test
    void testIsPositive() {
        // Only a strictly greater-than-zero duration is positive.
        assertFalse(DurationUtils.isPositive(Duration.ZERO));
        assertFalse(DurationUtils.isPositive(Duration.ofMillis(-1)));
        assertTrue(DurationUtils.isPositive(Duration.ofMillis(1)));
    }

    @Test
    void testLongToIntRangeFit() {
        // fit() clamps a long into the int range, leaving in-range values untouched.
        assertEquals(0, DurationUtils.LONG_TO_INT_RANGE.fit(0L));
        // Values at or below the int minimum clamp to Integer.MIN_VALUE.
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 1));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 2));
        // Values at or above the int maximum clamp to Integer.MAX_VALUE.
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 1));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 2));
        // The extreme long bounds clamp to the matching int bounds.
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MIN_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MAX_VALUE));
        // Values well inside the int range pass through unchanged.
        assertEquals(Short.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MIN_VALUE));
        assertEquals(Short.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MAX_VALUE));
    }

    @Test
    void testOfConsumer() {
        // of(consumer) times the consumer and hands it the start instant; the measured duration is never negative.
        assertTrue(DurationUtils.of(start -> assertTrue(start.compareTo(Instant.now()) <= 0)).compareTo(Duration.ZERO) >= 0);
        // The start instant passed to the consumer is taken at/after the moment captured just before the call.
        final Instant before = Instant.now();
        DurationUtils.of(start -> assertTrue(start.compareTo(before) >= 0));
    }

    @Test
    void testOfRunnble() {
        // of(runnable) times the runnable; the measured duration is never negative.
        assertTrue(DurationUtils.of(this::testSince).compareTo(Duration.ZERO) >= 0);
    }

    @Test
    void testOfRunnbleThrowing() {
        // An exception thrown by the runnable propagates out of of().
        assertThrows(IOException.class, () -> DurationUtils.of(() -> {
            throw new IOException();
        }));
    }

    @Test
    void testSince() {
        // since() measures from the given instant to now: non-negative for past instants, non-positive for future ones.
        assertTrue(DurationUtils.since(Instant.EPOCH).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MIN).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MAX).compareTo(Duration.ZERO) <= 0);
    }

    @Test
    void testToDuration() {
        // Each TimeUnit maps to the equivalent Duration.
        assertEquals(Duration.ofDays(1), DurationUtils.toDuration(1, TimeUnit.DAYS));
        assertEquals(Duration.ofHours(1), DurationUtils.toDuration(1, TimeUnit.HOURS));
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1_000, TimeUnit.MICROSECONDS));
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1, TimeUnit.MILLISECONDS));
        assertEquals(Duration.ofMinutes(1), DurationUtils.toDuration(1, TimeUnit.MINUTES));
        assertEquals(Duration.ofNanos(1), DurationUtils.toDuration(1, TimeUnit.NANOSECONDS));
        assertEquals(Duration.ofSeconds(1), DurationUtils.toDuration(1, TimeUnit.SECONDS));
        // Positive, negative and zero amounts round-trip through toMillis().
        assertEquals(1, DurationUtils.toDuration(1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(-1, DurationUtils.toDuration(-1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(0, DurationUtils.toDuration(0, TimeUnit.SECONDS).toMillis());
    }

    @Test
    void testToMillisInt() {
        // In-range millisecond durations convert directly.
        assertEquals(0, DurationUtils.toMillisInt(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisInt(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisInt(Duration.ofMillis(-1)));
        // The int bounds convert exactly.
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MAX_VALUE)));
        // Millisecond values beyond the int range clamp to the nearest int bound.
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 1)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 2)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 1)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 2)));
        // Durations whose milliseconds overflow a long still clamp to the int bounds.
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MAX_VALUE)));
    }

    @Test
    void testToMillisIntNullDuration() {
        // A null duration is rejected.
        assertNullPointerException(() -> DurationUtils.toMillisInt(null));
    }

    @Test
    void testToMillisIntOverflowToMaxInteger() {
        // Duration.ofSeconds(Long.MAX_VALUE / 1000 + 1) overflows when toMillis() is called, so it clamps high.
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofSeconds(Long.MAX_VALUE / 1000 + 1)));
    }

    @Test
    void testToMillisIntUnderflowToMinInteger() {
        // Symmetric to the overflow case: a millisecond underflow clamps low.
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofSeconds(Long.MIN_VALUE / 1000 - 1)));
    }

    @Test
    void testToMillisLong() {
        // In-range and exact-bound durations convert directly to milliseconds.
        assertEquals(0, DurationUtils.toMillisLong(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisLong(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisLong(Duration.ofMillis(-1)));
        assertEquals(Long.MIN_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MIN_VALUE)));
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MAX_VALUE)));
        // A seconds-based duration whose milliseconds overflow a long clamps to Long.MAX_VALUE instead of throwing.
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofSeconds(Long.MAX_VALUE)));
    }

    @Test
    void testToMillisLongNullDuration() {
        // A null duration is rejected.
        assertNullPointerException(() -> DurationUtils.toMillisLong(null));
    }

    @Test
    void testZeroIfNull() {
        // null becomes Duration.ZERO; a non-null duration is returned unchanged.
        assertEquals(Duration.ZERO, DurationUtils.zeroIfNull(null));
        assertEquals(Duration.ofDays(1), DurationUtils.zeroIfNull(Duration.ofDays(1)));
    }
}

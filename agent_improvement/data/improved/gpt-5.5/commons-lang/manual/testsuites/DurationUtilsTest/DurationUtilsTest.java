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

    private static final String ONE_UNIT_PROPERTY = "Seconds1";
    private static final String LONG_MAX_PROPERTY = "Seconds2";

    private static void assertNanosOfMiili(final int expected, final Duration duration) {
        assertEquals(expected, DurationUtils.getNanosOfMiili(duration));
    }

    private static void assertNanosOfMilli(final int expected, final Duration duration) {
        assertEquals(expected, DurationUtils.getNanosOfMilli(duration));
    }

    private static void assertToMillisInt(final int expected, final Duration duration) {
        assertEquals(expected, DurationUtils.toMillisInt(duration));
    }

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = ONE_UNIT_PROPERTY, value = "1"),
        @SetSystemProperty(key = LONG_MAX_PROPERTY, value = "9223372036854775807") }) // Long.MAX_VALUE
    void testGet() {
        assertEquals(Duration.ofSeconds(0), DurationUtils.get(null, ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(0), DurationUtils.get("", ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(1), DurationUtils.get("Seconds1", ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.get("Seconds2", ChronoUnit.SECONDS, 0));

        assertEquals(Duration.ofMillis(0), DurationUtils.get(null, ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(0), DurationUtils.get("", ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(1), DurationUtils.get("Seconds1", ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.get("Seconds2", ChronoUnit.MILLIS, 0));
    }

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = ONE_UNIT_PROPERTY, value = "1"),
        @SetSystemProperty(key = LONG_MAX_PROPERTY, value = "9223372036854775807") }) // Long.MAX_VALUE
    void testGetMilliseconds() {
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis(null, 0));
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis("", 0));
        assertEquals(Duration.ofMillis(1), DurationUtils.getMillis("Seconds1", 0));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.getMillis("Seconds2", 0));
    }

    @Test
    void testGetNanosOfMiili() {
        assertNanosOfMiili(0, null);
        assertNanosOfMiili(0, Duration.ZERO);
        assertNanosOfMiili(1, Duration.ofNanos(1));
        assertNanosOfMiili(10, Duration.ofNanos(10));
        assertNanosOfMiili(100, Duration.ofNanos(100));
        assertNanosOfMiili(1_000, Duration.ofNanos(1_000));
        assertNanosOfMiili(10_000, Duration.ofNanos(10_000));
        assertNanosOfMiili(100_000, Duration.ofNanos(100_000));
        assertNanosOfMiili(0, Duration.ofNanos(1_000_000));
        assertNanosOfMiili(1, Duration.ofNanos(1_000_001));
    }

    @Test
    void testGetNanosOfMilli() {
        assertNanosOfMilli(0, null);
        assertNanosOfMilli(0, Duration.ZERO);
        assertNanosOfMilli(1, Duration.ofNanos(1));
        assertNanosOfMilli(10, Duration.ofNanos(10));
        assertNanosOfMilli(100, Duration.ofNanos(100));
        assertNanosOfMilli(1_000, Duration.ofNanos(1_000));
        assertNanosOfMilli(10_000, Duration.ofNanos(10_000));
        assertNanosOfMilli(100_000, Duration.ofNanos(100_000));
        assertNanosOfMilli(0, Duration.ofNanos(1_000_000));
        assertNanosOfMilli(1, Duration.ofNanos(1_000_001));
    }

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = ONE_UNIT_PROPERTY, value = "1"),
        @SetSystemProperty(key = LONG_MAX_PROPERTY, value = "9223372036854775807") }) // Long.MAX_VALUE
    void testGetSeconds() {
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds(null, 0));
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds("", 0));
        assertEquals(Duration.ofSeconds(1), DurationUtils.getSeconds("Seconds1", 0));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.getSeconds("Seconds2", 0));
    }

    @Test
    void testIsPositive() {
        assertFalse(DurationUtils.isPositive(Duration.ZERO));
        assertFalse(DurationUtils.isPositive(Duration.ofMillis(-1)));
        assertTrue(DurationUtils.isPositive(Duration.ofMillis(1)));
    }

    @Test
    void testLongToIntRangeFit() {
        assertEquals(0, DurationUtils.LONG_TO_INT_RANGE.fit(0L));

        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 1));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 2));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 1));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 2));

        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MIN_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MAX_VALUE));

        assertEquals(Short.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MIN_VALUE));
        assertEquals(Short.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MAX_VALUE));
    }

    @Test
    void testOfConsumer() {
        assertTrue(DurationUtils.of(start -> assertTrue(start.compareTo(Instant.now()) <= 0)).compareTo(Duration.ZERO) >= 0);

        final Instant before = Instant.now();
        DurationUtils.of(start -> assertTrue(start.compareTo(before) >= 0));
    }

    @Test
    void testOfRunnble() {
        assertTrue(DurationUtils.of(this::testSince).compareTo(Duration.ZERO) >= 0);
    }

    @Test
    void testOfRunnbleThrowing() {
        assertThrows(IOException.class, () -> DurationUtils.of(() -> {
            throw new IOException();
        }));
    }

    @Test
    void testSince() {
        assertTrue(DurationUtils.since(Instant.EPOCH).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MIN).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MAX).compareTo(Duration.ZERO) <= 0);
    }

    @Test
    void testToDuration() {
        assertEquals(Duration.ofDays(1), DurationUtils.toDuration(1, TimeUnit.DAYS));
        assertEquals(Duration.ofHours(1), DurationUtils.toDuration(1, TimeUnit.HOURS));
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1_000, TimeUnit.MICROSECONDS));
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1, TimeUnit.MILLISECONDS));
        assertEquals(Duration.ofMinutes(1), DurationUtils.toDuration(1, TimeUnit.MINUTES));
        assertEquals(Duration.ofNanos(1), DurationUtils.toDuration(1, TimeUnit.NANOSECONDS));
        assertEquals(Duration.ofSeconds(1), DurationUtils.toDuration(1, TimeUnit.SECONDS));
        assertEquals(1, DurationUtils.toDuration(1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(-1, DurationUtils.toDuration(-1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(0, DurationUtils.toDuration(0, TimeUnit.SECONDS).toMillis());
    }

    @Test
    void testToMillisInt() {
        assertToMillisInt(0, Duration.ZERO);
        assertToMillisInt(1, Duration.ofMillis(1));
        assertToMillisInt(-1, Duration.ofMillis(-1));

        assertToMillisInt(Integer.MIN_VALUE, Duration.ofMillis(Integer.MIN_VALUE));
        assertToMillisInt(Integer.MAX_VALUE, Duration.ofMillis(Integer.MAX_VALUE));
        assertToMillisInt(Integer.MAX_VALUE, Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 1));
        assertToMillisInt(Integer.MAX_VALUE, Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 2));
        assertToMillisInt(Integer.MIN_VALUE, Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 1));
        assertToMillisInt(Integer.MIN_VALUE, Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 2));

        assertToMillisInt(Integer.MIN_VALUE, Duration.ofNanos(Long.MIN_VALUE));
        assertToMillisInt(Integer.MAX_VALUE, Duration.ofNanos(Long.MAX_VALUE));
    }

    @Test
    void testToMillisIntNullDuration() {
        assertNullPointerException(() -> DurationUtils.toMillisInt(null));
    }

    @Test
    void testToMillisIntOverflowToMaxInteger() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofSeconds(Long.MAX_VALUE / 1000 + 1)));
    }

    @Test
    void testToMillisIntUnderflowToMinInteger() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofSeconds(Long.MIN_VALUE / 1000 - 1)));
    }

    @Test
    void testToMillisLong() {
        assertEquals(0, DurationUtils.toMillisLong(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisLong(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisLong(Duration.ofMillis(-1)));
        assertEquals(Long.MIN_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MIN_VALUE)));
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MAX_VALUE)));
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofSeconds(Long.MAX_VALUE)));
    }

    @Test
    void testToMillisLongNullDuration() {
        assertNullPointerException(() -> DurationUtils.toMillisLong(null));
    }

    @Test
    void testZeroIfNull() {
        assertEquals(Duration.ZERO, DurationUtils.zeroIfNull(null));
        assertEquals(Duration.ofDays(1), DurationUtils.zeroIfNull(Duration.ofDays(1)));
    }
}

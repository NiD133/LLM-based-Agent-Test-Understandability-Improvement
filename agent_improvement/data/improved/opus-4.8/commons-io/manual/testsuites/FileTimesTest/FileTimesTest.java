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

package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes}.
 * <p>
 * Several tests revolve around NTFS time, which counts the number of 100-nanosecond intervals that have elapsed since
 * 1601-01-01T00:00:00Z. The shared {@link #instantToNtfsProvider()} data set pairs an ISO-8601 instant with its expected
 * NTFS value and is reused by every conversion test so that all boundary cases are exercised consistently.
 * </p>
 */
class FileTimesTest {

    /**
     * Pairs an ISO-8601 instant string with the NTFS time (100-nanosecond units since 1601-01-01) it represents.
     * <p>
     * The data set deliberately covers the extreme boundaries:
     * </p>
     * <ul>
     * <li>the NTFS epoch (1601-01-01) and instants a few 100-ns units on either side of it,</li>
     * <li>the {@code long} overflow limits ({@link Long#MAX_VALUE} / {@link Long#MIN_VALUE}),</li>
     * <li>millisecond-granularity boundaries around {@link FileTimes#HUNDRED_NANOS_PER_MILLISECOND}, and</li>
     * <li>instants around the Unix epoch (1970-01-01), offset by {@link FileTimes#UNIX_TO_NTFS_OFFSET}.</li>
     * </ul>
     */
    public static Stream<Arguments> instantToNtfsProvider() {
        // @formatter:off
        return Stream.of(
            // Around the NTFS epoch (1601-01-01).
            Arguments.of("1601-01-01T00:00:00.0000000Z", 0),
            Arguments.of("1601-01-01T00:00:00.0000001Z", 1),
            Arguments.of("1601-01-01T00:00:00.0000010Z", 10),
            Arguments.of("1601-01-01T00:00:00.0000100Z", 100),
            Arguments.of("1601-01-01T00:00:00.0001000Z", 1000),
            Arguments.of("1600-12-31T23:59:59.9999999Z", -1),
            // Around the long overflow boundaries.
            Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
            Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
            Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
            Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
            Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),
            Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
            Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
            Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
            Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
            Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),
            // Around the millisecond boundary.
            Arguments.of("1601-01-01T00:00:00.0010000Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1601-01-01T00:00:00.0010001Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1601-01-01T00:00:00.0009999Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
            Arguments.of("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
            // Around the Unix epoch (1970-01-01).
            Arguments.of("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET),
            Arguments.of("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
            Arguments.of("1970-01-01T00:00:00.0010000Z", -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
            Arguments.of("1969-12-31T23:59:59.9990000Z", -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND));
        // @formatter:on
    }

    /**
     * Pairs an ISO-8601 instant string with the {@link FileTime} expected after a round trip through NTFS time, using
     * instants near the Unix epoch where second-level granularity is enough.
     */
    public static Stream<Arguments> instantToFileTimeProvider() {
        // @formatter:off
        return Stream.of(
            Arguments.of("1970-01-01T00:00:00Z", FileTime.from(Instant.EPOCH)),
            Arguments.of("1969-12-31T23:59:00Z", FileTime.from(Instant.EPOCH.minusSeconds(60))),
            Arguments.of("1970-01-01T00:01:00Z", FileTime.from(Instant.EPOCH.plusSeconds(60))));
        // @formatter:on
    }

    /**
     * Pairs an ISO-8601 instant string with whether its epoch-second value fits in a signed 32-bit Unix time. The
     * {@code false} cases sit just beyond the 32-bit minimum (1901-12-13T20:45:52Z) and maximum (2038-01-19T03:14:07Z).
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        // @formatter:off
        return Stream.of(
            Arguments.of("2022-12-27T12:45:22Z", true),
            Arguments.of("2038-01-19T03:14:07Z", true),
            Arguments.of("1901-12-13T23:14:08Z", true),
            Arguments.of("1901-12-13T03:14:08Z", false),
            Arguments.of("2038-01-19T03:14:08Z", false),
            Arguments.of("2099-06-30T12:31:42Z", false));
        // @formatter:on
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testDateToFileTime(final String instant, final long ignoredNtfsTime) {
        final Instant parsedInstant = Instant.parse(instant);
        final FileTime expectedFileTime = FileTime.from(parsedInstant);
        final Date date = Date.from(parsedInstant);
        assertEquals(expectedFileTime.toMillis(), FileTimes.toFileTime(date).toMillis());
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testDateToNtfsTime(final String instant, final long ntfsTime) {
        // A Date only has millisecond resolution, so the expected NTFS value is truncated to a whole millisecond.
        final long expectedNtfsMillis = Math.floorDiv(ntfsTime, FileTimes.HUNDRED_NANOS_PER_MILLISECOND) * FileTimes.HUNDRED_NANOS_PER_MILLISECOND;
        final Date date = Date.from(Instant.parse(instant));
        final long actualNtfsTime = FileTimes.toNtfsTime(date);
        // toNtfsTime saturates at Long.MIN/MAX_VALUE instead of overflowing; only verify the non-saturated cases.
        final boolean saturated = actualNtfsTime == Long.MIN_VALUE || actualNtfsTime == Long.MAX_VALUE;
        if (!saturated) {
            assertEquals(expectedNtfsMillis, actualNtfsTime);
            assertEquals(expectedNtfsMillis, FileTimes.toNtfsTime(date.getTime()));
            assertEquals(expectedNtfsMillis, FileTimes.toNtfsTime(FileTimes.ntfsTimeToInstant(ntfsTime).toEpochMilli()));
        }
        // Round trip NTFS -> Instant -> NTFS preserves the original value at full 100-ns resolution.
        assertEquals(ntfsTime, FileTimes.toNtfsTime(FileTimes.ntfsTimeToInstant(ntfsTime)));
    }

    @Test
    void testEpoch() {
        assertEquals(0, FileTimes.EPOCH.toMillis());
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testFileTimeToDate(final String instant, final long ignoredNtfsTime) {
        final Instant parsedInstant = Instant.parse(instant);
        final FileTime fileTime = FileTime.from(parsedInstant);
        final Date expectedDate = Date.from(parsedInstant);
        assertEquals(expectedDate, FileTimes.toDate(fileTime));
    }

    @ParameterizedTest
    @MethodSource("instantToFileTimeProvider")
    void testFileTimeToNtfsTimeRoundTrip(final String instant, final FileTime expectedFileTime) {
        final Instant parsedInstant = Instant.parse(instant);
        final FileTime fileTime = FileTime.from(parsedInstant);
        assertEquals(parsedInstant, fileTime.toInstant()); // sanity check
        assertEquals(expectedFileTime, FileTimes.ntfsTimeToFileTime(FileTimes.toNtfsTime(fileTime)));
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testFileTimeToNtfsTime(final String instant, final long ntfsTime) {
        final FileTime fileTime = FileTime.from(Instant.parse(instant));
        assertEquals(ntfsTime, FileTimes.toNtfsTime(fileTime));
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testFromUnixTime(final String instant, final long ignoredNtfsTime) {
        final long epochSecond = Instant.parse(instant).getEpochSecond();
        assertEquals(epochSecond, FileTimes.fromUnixTime(epochSecond).to(TimeUnit.SECONDS));
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTime(final String instant, final boolean expectedIsUnixTime) {
        assertEquals(expectedIsUnixTime, FileTimes.isUnixTime(FileTime.from(Instant.parse(instant))));
    }

    // Note: intentionally not annotated with @Test, matching the original test (this method does not run).
    void testIsUnixTimeFileTimeNull() {
        assertTrue(FileTimes.isUnixTime(null));
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTimeLong(final String instant, final boolean expectedIsUnixTime) {
        assertEquals(expectedIsUnixTime, FileTimes.isUnixTime(Instant.parse(instant).getEpochSecond()));
    }

    @Test
    void testMaxJavaTime() {
        final long javaTime = Long.MAX_VALUE;
        final Instant instant = Instant.ofEpochMilli(javaTime);
        assertEquals(javaTime, instant.toEpochMilli()); // sanity check
        final long ntfsTime = FileTimes.toNtfsTime(javaTime);
        // toNtfsTime saturates at Long.MAX_VALUE instead of overflowing; the round trip is only meaningful otherwise.
        if (ntfsTime != Long.MAX_VALUE) {
            assertEquals(javaTime, FileTimes.ntfsTimeToInstant(ntfsTime).toEpochMilli());
        }
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testMaxJavaTimeParam(final String ignoredInstant, final long javaTime) {
        final Instant instant = Instant.ofEpochMilli(javaTime);
        assertEquals(javaTime, instant.toEpochMilli()); // sanity check
        final long ntfsTime = FileTimes.toNtfsTime(javaTime);
        // toNtfsTime saturates at Long.MIN/MAX_VALUE instead of overflowing; the round trip is only meaningful otherwise.
        final boolean saturated = ntfsTime == Long.MIN_VALUE || ntfsTime == Long.MAX_VALUE;
        if (!saturated) {
            assertEquals(javaTime, FileTimes.ntfsTimeToInstant(ntfsTime).toEpochMilli());
        }
    }

    @Test
    void testMinusMillis() {
        final int millisToSubtract = 2;
        assertEquals(Instant.EPOCH.minusMillis(millisToSubtract), FileTimes.minusMillis(FileTimes.EPOCH, millisToSubtract).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.minusMillis(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    void testMinusNanos() {
        final int nanosToSubtract = 2;
        assertEquals(Instant.EPOCH.minusNanos(nanosToSubtract), FileTimes.minusNanos(FileTimes.EPOCH, nanosToSubtract).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.minusNanos(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    void testMinusSeconds() {
        final int secondsToSubtract = 2;
        assertEquals(Instant.EPOCH.minusSeconds(secondsToSubtract), FileTimes.minusSeconds(FileTimes.EPOCH, secondsToSubtract).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.minusSeconds(FileTimes.EPOCH, 0).toInstant());
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testNtfsTimeToDate(final String instant, final long ntfsTime) {
        assertEquals(Instant.parse(instant).toEpochMilli(), FileTimes.ntfsTimeToDate(ntfsTime).toInstant().toEpochMilli());
    }

    @ParameterizedTest
    @MethodSource("instantToNtfsProvider")
    void testNtfsTimeToFileTime(final String instant, final long ntfsTime) {
        final Instant parsedInstant = Instant.parse(instant);
        final FileTime fileTime = FileTime.from(parsedInstant);
        assertEquals(parsedInstant, fileTime.toInstant()); // sanity check
        assertEquals(parsedInstant, FileTimes.ntfsTimeToInstant(ntfsTime));
        assertEquals(fileTime, FileTimes.ntfsTimeToFileTime(ntfsTime));
    }

    @Test
    void testNullDateToNullFileTime() {
        assertNull(FileTimes.toFileTime(null));
    }

    @Test
    void testNullFileTimeToNullDate() {
        assertNull(FileTimes.toDate(null));
    }

    @Test
    void testPlusMinusMillis() {
        final int millisToAdd = 2;
        assertEquals(Instant.EPOCH.plusMillis(millisToAdd), FileTimes.plusMillis(FileTimes.EPOCH, millisToAdd).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.plusMillis(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    void testPlusNanos() {
        final int nanosToAdd = 2;
        assertEquals(Instant.EPOCH.plusNanos(nanosToAdd), FileTimes.plusNanos(FileTimes.EPOCH, nanosToAdd).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.plusNanos(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    void testPlusSeconds() {
        final int secondsToAdd = 2;
        assertEquals(Instant.EPOCH.plusSeconds(secondsToAdd), FileTimes.plusSeconds(FileTimes.EPOCH, secondsToAdd).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.plusSeconds(FileTimes.EPOCH, 0).toInstant());
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testToUnixTime(final String instant, final boolean expectedIsUnixTime) {
        assertEquals(expectedIsUnixTime, FileTimes.isUnixTime(FileTimes.toUnixTime(FileTime.from(Instant.parse(instant)))));
    }
}

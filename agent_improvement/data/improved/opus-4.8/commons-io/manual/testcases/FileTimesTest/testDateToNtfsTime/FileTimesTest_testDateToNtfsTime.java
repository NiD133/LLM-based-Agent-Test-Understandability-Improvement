package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the round-trip conversion between {@link Instant}/{@link Date} and NTFS time in
 * {@link FileTimes}.
 * <p>
 * An NTFS time is the number of 100-nanosecond intervals since 1601-01-01T00:00:00Z.
 * Each test case pairs an ISO-8601 instant with its expected NTFS time value.
 * </p>
 */
public class FileTimesTest_testDateToNtfsTime {

    /** Number of 100-nanosecond NTFS units in a single millisecond. */
    private static final long NANOS_PER_MILLI = FileTimes.HUNDRED_NANOS_PER_MILLISECOND;

    /** Number of 100-nanosecond NTFS units between the Unix epoch and the NTFS epoch. */
    private static final long UNIX_TO_NTFS_OFFSET = FileTimes.UNIX_TO_NTFS_OFFSET;

    /**
     * Supplies (instant, expectedNtfsTime) pairs, grouped by the scenario each one exercises.
     */
    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(
            // Just at and just after the NTFS epoch (1601-01-01), in 100-ns steps.
            Arguments.of("1601-01-01T00:00:00.0000000Z", 0),
            Arguments.of("1601-01-01T00:00:00.0000001Z", 1),
            Arguments.of("1601-01-01T00:00:00.0000010Z", 10),
            Arguments.of("1601-01-01T00:00:00.0000100Z", 100),
            Arguments.of("1601-01-01T00:00:00.0001000Z", 1000),
            // Just before the NTFS epoch.
            Arguments.of("1600-12-31T23:59:59.9999999Z", -1),

            // Largest representable NTFS times (near Long.MAX_VALUE).
            Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
            Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
            Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
            Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
            Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),

            // Smallest representable NTFS times (near Long.MIN_VALUE).
            Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
            Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
            Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
            Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
            Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

            // One-millisecond boundaries around the NTFS epoch.
            Arguments.of("1601-01-01T00:00:00.0010000Z", NANOS_PER_MILLI),
            Arguments.of("1601-01-01T00:00:00.0010001Z", NANOS_PER_MILLI + 1),
            Arguments.of("1601-01-01T00:00:00.0009999Z", NANOS_PER_MILLI - 1),
            Arguments.of("1600-12-31T23:59:59.9990000Z", -NANOS_PER_MILLI),
            Arguments.of("1600-12-31T23:59:59.9990001Z", -NANOS_PER_MILLI + 1),
            Arguments.of("1600-12-31T23:59:59.9989999Z", -NANOS_PER_MILLI - 1),

            // Around the Unix epoch (1970-01-01), which sits at -UNIX_TO_NTFS_OFFSET.
            Arguments.of("1970-01-01T00:00:00.0000000Z", -UNIX_TO_NTFS_OFFSET),
            Arguments.of("1970-01-01T00:00:00.0000001Z", -UNIX_TO_NTFS_OFFSET + 1),
            Arguments.of("1970-01-01T00:00:00.0010000Z", -UNIX_TO_NTFS_OFFSET + NANOS_PER_MILLI),
            Arguments.of("1969-12-31T23:59:59.9999999Z", -UNIX_TO_NTFS_OFFSET - 1),
            Arguments.of("1969-12-31T23:59:59.9990000Z", -UNIX_TO_NTFS_OFFSET - NANOS_PER_MILLI));
    }

    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testDateToNtfsTime(final String instantStr, final long ntfsTime) {
        // A Date has only millisecond resolution, so the expected NTFS value is the
        // input truncated (floored) down to a whole-millisecond boundary.
        final long ntfsTimeFlooredToMillis = Math.floorDiv(ntfsTime, NANOS_PER_MILLI) * NANOS_PER_MILLI;

        final Instant instant = Instant.parse(instantStr);
        final Date date = Date.from(instant);
        final long actualNtfsTime = FileTimes.toNtfsTime(date);

        // toNtfsTime saturates at Long.MIN/MAX instead of overflowing; for those clamped
        // results we only verify the Instant round-trip below, not the millisecond value.
        final boolean isSaturated = actualNtfsTime == Long.MIN_VALUE || actualNtfsTime == Long.MAX_VALUE;
        if (!isSaturated) {
            assertEquals(ntfsTimeFlooredToMillis, actualNtfsTime);
            // Converting via the raw Java millis must give the same result.
            assertEquals(ntfsTimeFlooredToMillis, FileTimes.toNtfsTime(date.getTime()));
            // And so must converting the NTFS time -> Instant -> Java millis -> NTFS time.
            assertEquals(ntfsTimeFlooredToMillis, FileTimes.toNtfsTime(FileTimes.ntfsTimeToInstant(ntfsTime).toEpochMilli()));
        }

        // The full-precision Instant round-trip must reproduce the original NTFS time exactly.
        assertEquals(ntfsTime, FileTimes.toNtfsTime(FileTimes.ntfsTimeToInstant(ntfsTime)));
    }
}

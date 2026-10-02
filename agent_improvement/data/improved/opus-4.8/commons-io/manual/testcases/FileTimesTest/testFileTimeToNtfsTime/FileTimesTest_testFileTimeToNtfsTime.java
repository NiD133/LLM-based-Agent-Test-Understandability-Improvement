package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#toNtfsTime(FileTime)}.
 * <p>
 * An NTFS time is the number of 100-nanosecond intervals elapsed since the NTFS epoch,
 * 1601-01-01T00:00:00Z. This test feeds a series of {@link Instant} strings and the NTFS time
 * each one is expected to convert to.
 * </p>
 */
public class FileTimesTest_testFileTimeToNtfsTime {

    /** Number of 100-nanosecond intervals in one millisecond. */
    private static final long HUNDRED_NANOS_PER_MS = FileTimes.HUNDRED_NANOS_PER_MILLISECOND;

    /** Offset (in 100-nanosecond intervals) of the NTFS epoch (1601) relative to the Unix epoch (1970). */
    private static final long UNIX_TO_NTFS_OFFSET = FileTimes.UNIX_TO_NTFS_OFFSET;

    /**
     * Pairs of (ISO-8601 instant, expected NTFS time) covering the conversion's key behaviours:
     * the NTFS epoch boundary, the {@code long} overflow/underflow saturation points, millisecond
     * granularity, and the Unix epoch.
     */
    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(
            // --- Around the NTFS epoch (1601-01-01), in single 100-nanosecond steps ---
            Arguments.of("1601-01-01T00:00:00.0000000Z", 0),
            Arguments.of("1601-01-01T00:00:00.0000001Z", 1),
            Arguments.of("1601-01-01T00:00:00.0000010Z", 10),
            Arguments.of("1601-01-01T00:00:00.0000100Z", 100),
            Arguments.of("1601-01-01T00:00:00.0001000Z", 1000),
            Arguments.of("1600-12-31T23:59:59.9999999Z", -1),

            // --- Near Long.MAX_VALUE: the largest representable NTFS times ---
            Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
            Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
            Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
            Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
            Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),

            // --- Near Long.MIN_VALUE: the smallest representable NTFS times ---
            Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
            Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
            Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
            Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
            Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

            // --- Millisecond granularity around the NTFS epoch ---
            Arguments.of("1601-01-01T00:00:00.0010000Z", HUNDRED_NANOS_PER_MS),
            Arguments.of("1601-01-01T00:00:00.0010001Z", HUNDRED_NANOS_PER_MS + 1),
            Arguments.of("1601-01-01T00:00:00.0009999Z", HUNDRED_NANOS_PER_MS - 1),
            Arguments.of("1600-12-31T23:59:59.9990000Z", -HUNDRED_NANOS_PER_MS),
            Arguments.of("1600-12-31T23:59:59.9990001Z", -HUNDRED_NANOS_PER_MS + 1),
            Arguments.of("1600-12-31T23:59:59.9989999Z", -HUNDRED_NANOS_PER_MS - 1),

            // --- Around the Unix epoch (1970-01-01), whose NTFS time is the offset itself ---
            Arguments.of("1970-01-01T00:00:00.0000000Z", -UNIX_TO_NTFS_OFFSET),
            Arguments.of("1970-01-01T00:00:00.0000001Z", -UNIX_TO_NTFS_OFFSET + 1),
            Arguments.of("1970-01-01T00:00:00.0010000Z", -UNIX_TO_NTFS_OFFSET + HUNDRED_NANOS_PER_MS),
            Arguments.of("1969-12-31T23:59:59.9999999Z", -UNIX_TO_NTFS_OFFSET - 1),
            Arguments.of("1969-12-31T23:59:59.9990000Z", -UNIX_TO_NTFS_OFFSET - HUNDRED_NANOS_PER_MS));
    }

    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testFileTimeToNtfsTime(final String instant, final long ntfsTime) {
        final FileTime fileTime = FileTime.from(Instant.parse(instant));
        assertEquals(ntfsTime, FileTimes.toNtfsTime(fileTime));
    }
}

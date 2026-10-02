package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testFromUnixTime {

    /**
     * Provides (instantString, ntfsTime) pairs shared with NTFS-conversion tests.
     * This test only uses the {@code instantString} column; {@code ntfsTime} is
     * included so the provider can be reused across multiple test methods.
     */
    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(
                // NTFS epoch (1601-01-01) and its immediate neighbours
                Arguments.of("1601-01-01T00:00:00.0000000Z", 0),
                Arguments.of("1601-01-01T00:00:00.0000001Z", 1),
                Arguments.of("1601-01-01T00:00:00.0000010Z", 10),
                Arguments.of("1601-01-01T00:00:00.0000100Z", 100),
                Arguments.of("1601-01-01T00:00:00.0001000Z", 1000),
                Arguments.of("1600-12-31T23:59:59.9999999Z", -1),

                // Far-future boundary: Long.MAX_VALUE and values just below it
                Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
                Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
                Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
                Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
                Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),

                // Far-past boundary: Long.MIN_VALUE and values just above it
                Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
                Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
                Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
                Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
                Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

                // One-millisecond boundary (HUNDRED_NANOS_PER_MILLISECOND) on both sides
                Arguments.of("1601-01-01T00:00:00.0010000Z",  FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1601-01-01T00:00:00.0010001Z",  FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                Arguments.of("1601-01-01T00:00:00.0009999Z",  FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
                Arguments.of("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                Arguments.of("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),

                // Unix epoch (1970-01-01) and its immediate neighbours
                Arguments.of("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET),
                Arguments.of("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
                Arguments.of("1970-01-01T00:00:00.0010000Z", -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
                Arguments.of("1969-12-31T23:59:59.9990000Z", -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND)
        );
    }

    /**
     * Verifies that {@link FileTimes#fromUnixTime(long)} round-trips correctly:
     * converting a Unix epoch second to a {@link java.nio.file.attribute.FileTime}
     * and reading it back in seconds must yield the original value.
     *
     * @param instant  ISO-8601 string from which the Unix epoch second is derived.
     * @param ntfsTime NTFS 100-nanosecond value supplied by the shared provider
     *                 (not used by this assertion).
     */
    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testFromUnixTime(final String instant, final long ntfsTime) {
        final long epochSecond = Instant.parse(instant).getEpochSecond();
        assertEquals(epochSecond, FileTimes.fromUnixTime(epochSecond).to(TimeUnit.SECONDS));
    }
}

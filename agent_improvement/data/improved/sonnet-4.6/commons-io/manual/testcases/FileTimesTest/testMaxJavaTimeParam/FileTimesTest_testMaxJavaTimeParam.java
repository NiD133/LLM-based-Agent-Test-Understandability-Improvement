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

public class FileTimesTest_testMaxJavaTimeParam {

    /**
     * Provides pairs of (human-readable ISO instant string, Java millisecond timestamp) for NTFS
     * conversion round-trip tests. The ISO instant string is included for readability only — it
     * documents what each millisecond value represents in calendar terms.
     */
    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        // @formatter:off
        return Stream.of(
            // NTFS epoch and small positive offsets (100-nanosecond units from 1601-01-01)
            Arguments.of("1601-01-01T00:00:00.0000000Z",           0),
            Arguments.of("1601-01-01T00:00:00.0000001Z",           1),
            Arguments.of("1601-01-01T00:00:00.0000010Z",           10),
            Arguments.of("1601-01-01T00:00:00.0000100Z",           100),
            Arguments.of("1601-01-01T00:00:00.0001000Z",           1000),
            // One unit before NTFS epoch (negative NTFS time)
            Arguments.of("1600-12-31T23:59:59.9999999Z",           -1),
            // Long.MAX_VALUE boundary and nearby values
            Arguments.of("+30828-09-14T02:48:05.477580700Z",       Long.MAX_VALUE),
            Arguments.of("+30828-09-14T02:48:05.477580600Z",       Long.MAX_VALUE - 1),
            Arguments.of("+30828-09-14T02:48:05.477579700Z",       Long.MAX_VALUE - 10),
            Arguments.of("+30828-09-14T02:48:05.477570700Z",       Long.MAX_VALUE - 100),
            Arguments.of("+30828-09-14T02:48:05.477480700Z",       Long.MAX_VALUE - 1000),
            // Long.MIN_VALUE boundary and nearby values
            Arguments.of("-27627-04-19T21:11:54.522419200Z",       Long.MIN_VALUE),
            Arguments.of("-27627-04-19T21:11:54.522419300Z",       Long.MIN_VALUE + 1),
            Arguments.of("-27627-04-19T21:11:54.522420200Z",       Long.MIN_VALUE + 10),
            Arguments.of("-27627-04-19T21:11:54.522429200Z",       Long.MIN_VALUE + 100),
            Arguments.of("-27627-04-19T21:11:54.522519200Z",       Long.MIN_VALUE + 1000),
            // Values at exact millisecond boundaries (HUNDRED_NANOS_PER_MILLISECOND multiples)
            Arguments.of("1601-01-01T00:00:00.0010000Z",           FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1601-01-01T00:00:00.0010001Z",           FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1601-01-01T00:00:00.0009999Z",           FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
            Arguments.of("1600-12-31T23:59:59.9990000Z",           -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1600-12-31T23:59:59.9990001Z",           -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1600-12-31T23:59:59.9989999Z",           -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
            // Values around the Unix epoch (1970-01-01), expressed as NTFS offsets
            Arguments.of("1970-01-01T00:00:00.0000000Z",           -FileTimes.UNIX_TO_NTFS_OFFSET),
            Arguments.of("1970-01-01T00:00:00.0000001Z",           -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
            Arguments.of("1970-01-01T00:00:00.0010000Z",           -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1969-12-31T23:59:59.9999999Z",           -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
            Arguments.of("1969-12-31T23:59:59.9990000Z",           -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND)
        );
        // @formatter:on
    }

    /**
     * Verifies that converting a Java millisecond timestamp to NTFS time and back recovers the
     * original value, except when the conversion overflows (in which case {@code toNtfsTime}
     * clamps to {@link Long#MIN_VALUE} or {@link Long#MAX_VALUE}).
     *
     * @param expectedInstantStr human-readable ISO-8601 string describing {@code javaTime}
     *                           (used for test-output legibility only, not asserted)
     * @param javaTime           milliseconds since the Unix epoch to convert
     */
    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testMaxJavaTimeParam(final String expectedInstantStr, final long javaTime) {
        final Instant instant = Instant.ofEpochMilli(javaTime);
        // Sanity-check: the millisecond value survives an Instant round-trip unchanged.
        assertEquals(javaTime, instant.toEpochMilli());

        final long ntfsTime = FileTimes.toNtfsTime(javaTime);
        final Instant roundTripped = FileTimes.ntfsTimeToInstant(ntfsTime);

        // When the NTFS result was clamped to the long boundary (overflow), the round-trip
        // cannot reproduce the original millisecond value — skip the assertion in that case.
        if (ntfsTime != Long.MIN_VALUE && ntfsTime != Long.MAX_VALUE) {
            assertEquals(javaTime, roundTripped.toEpochMilli());
        }
    }
}

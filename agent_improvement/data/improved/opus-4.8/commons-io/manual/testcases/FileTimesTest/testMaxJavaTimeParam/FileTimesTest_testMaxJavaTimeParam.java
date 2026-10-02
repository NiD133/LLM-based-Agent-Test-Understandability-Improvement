package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that converting a Java time (milliseconds since the Unix epoch) to an NTFS time with
 * {@link FileTimes#toNtfsTime(long)} and back with {@link FileTimes#ntfsTimeToInstant(long)} is a
 * loss-less round trip, except where {@code toNtfsTime} deliberately saturates to
 * {@link Long#MAX_VALUE} / {@link Long#MIN_VALUE} instead of overflowing.
 */
public class FileTimesTest_testMaxJavaTimeParam {

    /**
     * Java times (milliseconds since the Unix epoch) exercised by {@link #testMaxJavaTimeParam(long)}.
     * <p>
     * The values cluster around boundaries that are interesting for the NTFS conversion: the epoch
     * itself, the {@code long} extremes (where saturation is expected), and the conversion constants
     * {@code HUNDRED_NANOS_PER_MILLISECOND} and {@code UNIX_TO_NTFS_OFFSET}.
     * </p>
     */
    public static Stream<Arguments> javaTimeProvider() {
        final long hundredNanosPerMilli = FileTimes.HUNDRED_NANOS_PER_MILLISECOND;
        final long unixToNtfsOffset = FileTimes.UNIX_TO_NTFS_OFFSET;
        return LongStream.of(
                // Around the Unix epoch.
                0, 1, 10, 100, 1000, -1,
                // Around Long.MAX_VALUE: toNtfsTime is expected to saturate at the top of these.
                Long.MAX_VALUE, Long.MAX_VALUE - 1, Long.MAX_VALUE - 10, Long.MAX_VALUE - 100, Long.MAX_VALUE - 1000,
                // Around Long.MIN_VALUE: toNtfsTime is expected to saturate at the bottom of these.
                Long.MIN_VALUE, Long.MIN_VALUE + 1, Long.MIN_VALUE + 10, Long.MIN_VALUE + 100, Long.MIN_VALUE + 1000,
                // Around one millisecond expressed in NTFS 100-nanosecond units.
                hundredNanosPerMilli, hundredNanosPerMilli + 1, hundredNanosPerMilli - 1,
                -hundredNanosPerMilli, -hundredNanosPerMilli + 1, -hundredNanosPerMilli - 1,
                // Around the Windows-epoch-to-Unix-epoch offset.
                -unixToNtfsOffset, -unixToNtfsOffset + 1, -unixToNtfsOffset + hundredNanosPerMilli,
                -unixToNtfsOffset - 1, -unixToNtfsOffset - hundredNanosPerMilli)
                .mapToObj(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("javaTimeProvider")
    void testMaxJavaTimeParam(final long javaTime) {
        // Sanity check: the Instant must faithfully round-trip the input milliseconds.
        final Instant instant = Instant.ofEpochMilli(javaTime);
        assertEquals(javaTime, instant.toEpochMilli());

        final long ntfsTime = FileTimes.toNtfsTime(javaTime);
        final Instant roundTripped = FileTimes.ntfsTimeToInstant(ntfsTime);

        // toNtfsTime saturates at the long extremes rather than overflowing, so a round trip is only
        // expected to be loss-less when the NTFS value did not hit those boundaries.
        final boolean saturated = ntfsTime == Long.MIN_VALUE || ntfsTime == Long.MAX_VALUE;
        if (!saturated) {
            assertEquals(javaTime, roundTripped.toEpochMilli());
        }
    }
}

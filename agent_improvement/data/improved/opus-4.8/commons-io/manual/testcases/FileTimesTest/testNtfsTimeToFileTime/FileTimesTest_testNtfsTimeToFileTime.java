package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#ntfsTimeToFileTime(long)} (and the sibling
 * {@link FileTimes#ntfsTimeToInstant(long)}).
 * <p>
 * An NTFS time is a count of 100-nanosecond intervals since 1601-01-01T00:00:00Z.
 * Each test case pairs an NTFS time value with the UTC instant it represents.
 * </p>
 */
public class FileTimesTest_testNtfsTimeToFileTime {

    /**
     * Provides {@code (expected ISO-8601 instant, NTFS time)} pairs covering:
     * <ul>
     *   <li>the NTFS epoch (1601) and the first few 100-ns ticks around it,</li>
     *   <li>the extreme {@code long} NTFS values and their neighbours,</li>
     *   <li>millisecond boundaries (one millisecond = 10 000 hundred-ns units), and</li>
     *   <li>the Unix epoch (1970), expressed relative to the NTFS epoch.</li>
     * </ul>
     */
    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(
                // --- NTFS epoch (1601-01-01) and the first 100-nanosecond ticks ---
                Arguments.of("1601-01-01T00:00:00.0000000Z", 0),
                Arguments.of("1601-01-01T00:00:00.0000001Z", 1),
                Arguments.of("1601-01-01T00:00:00.0000010Z", 10),
                Arguments.of("1601-01-01T00:00:00.0000100Z", 100),
                Arguments.of("1601-01-01T00:00:00.0001000Z", 1000),
                Arguments.of("1600-12-31T23:59:59.9999999Z", -1),

                // --- Largest representable NTFS times (Long.MAX_VALUE and neighbours) ---
                Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
                Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
                Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
                Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
                Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),

                // --- Smallest representable NTFS times (Long.MIN_VALUE and neighbours) ---
                Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
                Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
                Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
                Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
                Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

                // --- Millisecond boundaries (1 ms = HUNDRED_NANOS_PER_MILLISECOND units) ---
                Arguments.of("1601-01-01T00:00:00.0010000Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1601-01-01T00:00:00.0010001Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                Arguments.of("1601-01-01T00:00:00.0009999Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
                Arguments.of("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                Arguments.of("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),

                // --- Unix epoch (1970-01-01), offset from the NTFS epoch by UNIX_TO_NTFS_OFFSET ---
                Arguments.of("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET),
                Arguments.of("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
                Arguments.of("1970-01-01T00:00:00.0010000Z", -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
                Arguments.of("1969-12-31T23:59:59.9990000Z", -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND));
    }

    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testNtfsTimeToFileTime(final String instantStr, final long ntfsTime) {
        final Instant expectedInstant = Instant.parse(instantStr);
        final FileTime expectedFileTime = FileTime.from(expectedInstant);

        // Sanity check: the expected FileTime round-trips back to the expected Instant.
        assertEquals(expectedInstant, expectedFileTime.toInstant());

        // The NTFS time converts to the expected Instant and the expected FileTime.
        assertEquals(expectedInstant, FileTimes.ntfsTimeToInstant(ntfsTime));
        assertEquals(expectedFileTime, FileTimes.ntfsTimeToFileTime(ntfsTime));
    }
}

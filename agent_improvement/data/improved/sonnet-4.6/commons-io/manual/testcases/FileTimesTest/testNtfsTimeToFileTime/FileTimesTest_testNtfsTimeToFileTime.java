package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testNtfsTimeToFileTime {

    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(
                // NTFS epoch (1601-01-01T00:00:00Z) maps to ntfsTime = 0
                Arguments.of("1601-01-01T00:00:00.0000000Z", 0),

                // Small positive offsets from NTFS epoch (1, 10, 100, 1000 hundred-nanosecond units)
                Arguments.of("1601-01-01T00:00:00.0000001Z", 1),
                Arguments.of("1601-01-01T00:00:00.0000010Z", 10),
                Arguments.of("1601-01-01T00:00:00.0000100Z", 100),
                Arguments.of("1601-01-01T00:00:00.0001000Z", 1000),

                // One hundred-nanosecond unit before NTFS epoch (negative ntfsTime)
                Arguments.of("1600-12-31T23:59:59.9999999Z", -1),

                // Maximum representable NTFS time (Long.MAX_VALUE) and values just below it
                Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
                Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
                Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
                Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
                Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),

                // Minimum representable NTFS time (Long.MIN_VALUE) and values just above it
                Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
                Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
                Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
                Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
                Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

                // Exactly one millisecond after NTFS epoch, and adjacent boundary values
                Arguments.of("1601-01-01T00:00:00.0010000Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1601-01-01T00:00:00.0010001Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                Arguments.of("1601-01-01T00:00:00.0009999Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),

                // Exactly one millisecond before NTFS epoch, and adjacent boundary values
                Arguments.of("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                Arguments.of("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                Arguments.of("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),

                // Unix epoch (1970-01-01T00:00:00Z) expressed as NTFS time using the NTFS-to-Unix offset
                Arguments.of("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET),
                Arguments.of("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
                Arguments.of("1970-01-01T00:00:00.0010000Z", -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),

                // One hundred-nanosecond unit and one millisecond before Unix epoch
                Arguments.of("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
                Arguments.of("1969-12-31T23:59:59.9990000Z", -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND)
        );
    }

    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testNtfsTimeToFileTime(final String instantStr, final long ntfsTime) {
        final Instant instant = Instant.parse(instantStr);
        final FileTime fileTime = FileTime.from(instant);
        // sanity check
        assertEquals(instant, fileTime.toInstant());
        assertEquals(instant, FileTimes.ntfsTimeToInstant(ntfsTime));
        assertEquals(fileTime, FileTimes.ntfsTimeToFileTime(ntfsTime));
    }
}

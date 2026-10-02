package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#toFileTime(Date)}.
 *
 * <p>For each instant below, converting the instant to a {@link Date} and then to a
 * {@link FileTime} via {@link FileTimes#toFileTime(Date)} must yield the same
 * millisecond value as converting the instant straight to a {@link FileTime}.</p>
 */
public class FileTimesTest_testDateToFileTime {

    /**
     * Supplies the instants under test.
     *
     * <p>The second argument is the instant's value expressed in NTFS 100-nanosecond
     * units. It is shared with sibling NTFS conversion tests but is not used here, so
     * the test method names it {@code unusedNtfsTime}. The instants deliberately span
     * the full NTFS range: the 1601 NTFS epoch, the Unix epoch (1970), and the extreme
     * boundaries that map to {@link Long#MAX_VALUE} / {@link Long#MIN_VALUE}.</p>
     */
    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(
            // Around the NTFS epoch (1601-01-01), in 100-nanosecond steps.
            Arguments.of("1601-01-01T00:00:00.0000000Z", 0),
            Arguments.of("1601-01-01T00:00:00.0000001Z", 1),
            Arguments.of("1601-01-01T00:00:00.0000010Z", 10),
            Arguments.of("1601-01-01T00:00:00.0000100Z", 100),
            Arguments.of("1601-01-01T00:00:00.0001000Z", 1000),
            Arguments.of("1600-12-31T23:59:59.9999999Z", -1),

            // Upper boundary: instants mapping to Long.MAX_VALUE and just below.
            Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
            Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
            Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
            Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
            Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),

            // Lower boundary: instants mapping to Long.MIN_VALUE and just above.
            Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
            Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
            Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
            Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
            Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

            // One millisecond (in 100-nanosecond units) on either side of the NTFS epoch.
            Arguments.of("1601-01-01T00:00:00.0010000Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1601-01-01T00:00:00.0010001Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1601-01-01T00:00:00.0009999Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
            Arguments.of("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),

            // Around the Unix epoch (1970-01-01), offset from the NTFS epoch.
            Arguments.of("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET),
            Arguments.of("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
            Arguments.of("1970-01-01T00:00:00.0010000Z", -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
            Arguments.of("1969-12-31T23:59:59.9990000Z", -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND));
    }

    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testDateToFileTime(final String instantText, final long unusedNtfsTime) {
        final Instant instant = Instant.parse(instantText);
        final FileTime expectedFileTime = FileTime.from(instant);
        final Date date = Date.from(instant);

        // Converting via Date must preserve the instant to millisecond precision.
        assertEquals(expectedFileTime.toMillis(), FileTimes.toFileTime(date).toMillis());
    }
}

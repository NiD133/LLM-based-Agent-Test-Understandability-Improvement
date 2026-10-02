package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#toDate(FileTime)}.
 */
public class FileTimesTest_testFileTimeToDate {

    /**
     * A representative spread of UTC instants, expressed as ISO-8601 strings.
     * <p>
     * The values are chosen to exercise {@link FileTimes#toDate(FileTime)} across a wide range of
     * timestamps: around the NTFS epoch (1601-01-01), around the Unix epoch (1970-01-01), and at the
     * extreme instants reachable by NTFS file times.
     * </p>
     * <p>
     * Note: the original parameter source paired each instant with an NTFS tick count, but that count
     * was never consulted by this test, so only the instants are kept here.
     * </p>
     */
    static Stream<String> instantProvider() {
        return Stream.of(
            // Around the NTFS epoch (1601-01-01), at 100-nanosecond resolution.
            "1601-01-01T00:00:00.0000000Z",
            "1601-01-01T00:00:00.0000001Z",
            "1601-01-01T00:00:00.0000010Z",
            "1601-01-01T00:00:00.0000100Z",
            "1601-01-01T00:00:00.0001000Z",
            "1600-12-31T23:59:59.9999999Z",
            // Maximum NTFS file time and a few ticks below it.
            "+30828-09-14T02:48:05.477580700Z",
            "+30828-09-14T02:48:05.477580600Z",
            "+30828-09-14T02:48:05.477579700Z",
            "+30828-09-14T02:48:05.477570700Z",
            "+30828-09-14T02:48:05.477480700Z",
            // Minimum NTFS file time and a few ticks above it.
            "-27627-04-19T21:11:54.522419200Z",
            "-27627-04-19T21:11:54.522419300Z",
            "-27627-04-19T21:11:54.522420200Z",
            "-27627-04-19T21:11:54.522429200Z",
            "-27627-04-19T21:11:54.522519200Z",
            // One millisecond either side of the NTFS epoch.
            "1601-01-01T00:00:00.0010000Z",
            "1601-01-01T00:00:00.0010001Z",
            "1601-01-01T00:00:00.0009999Z",
            "1600-12-31T23:59:59.9990000Z",
            "1600-12-31T23:59:59.9990001Z",
            "1600-12-31T23:59:59.9989999Z",
            // Around the Unix epoch (1970-01-01).
            "1970-01-01T00:00:00.0000000Z",
            "1970-01-01T00:00:00.0000001Z",
            "1970-01-01T00:00:00.0010000Z",
            "1969-12-31T23:59:59.9999999Z",
            "1969-12-31T23:59:59.9990000Z");
    }

    /**
     * Converting a {@link FileTime} to a {@link Date} must yield the same instant (truncated to
     * milliseconds, which is all a {@link Date} can hold) as converting the underlying instant
     * directly with {@link Date#from(Instant)}.
     */
    @ParameterizedTest
    @MethodSource("instantProvider")
    void testFileTimeToDate(final String instantText) {
        final Instant instant = Instant.parse(instantText);
        final FileTime fileTime = FileTime.from(instant);
        final Date expectedDate = Date.from(instant);

        assertEquals(expectedDate, FileTimes.toDate(fileTime));
    }
}

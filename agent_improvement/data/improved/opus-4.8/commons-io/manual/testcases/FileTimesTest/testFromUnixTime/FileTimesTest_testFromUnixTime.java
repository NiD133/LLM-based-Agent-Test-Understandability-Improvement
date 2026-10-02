package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests {@link FileTimes#fromUnixTime(long)}.
 */
public class FileTimesTest_testFromUnixTime {

    /**
     * A spread of UTC instants whose epoch-second values exercise the conversion:
     * the NTFS epoch (1601), the Unix epoch (1970), points just before/after each,
     * and the extreme instants that map to {@code Long.MAX_VALUE} / {@code Long.MIN_VALUE}
     * NTFS times. Only the epoch-second component of each instant matters for this test;
     * the sub-second digits are intentionally varied but irrelevant to {@code fromUnixTime}.
     */
    @ParameterizedTest
    @ValueSource(strings = {
        // Around the NTFS epoch (1601-01-01).
        "1601-01-01T00:00:00.0000000Z",
        "1601-01-01T00:00:00.0000001Z",
        "1601-01-01T00:00:00.0000010Z",
        "1601-01-01T00:00:00.0000100Z",
        "1601-01-01T00:00:00.0001000Z",
        "1600-12-31T23:59:59.9999999Z",
        // Instants near the maximum representable NTFS time.
        "+30828-09-14T02:48:05.477580700Z",
        "+30828-09-14T02:48:05.477580600Z",
        "+30828-09-14T02:48:05.477579700Z",
        "+30828-09-14T02:48:05.477570700Z",
        "+30828-09-14T02:48:05.477480700Z",
        // Instants near the minimum representable NTFS time.
        "-27627-04-19T21:11:54.522419200Z",
        "-27627-04-19T21:11:54.522419300Z",
        "-27627-04-19T21:11:54.522420200Z",
        "-27627-04-19T21:11:54.522429200Z",
        "-27627-04-19T21:11:54.522519200Z",
        // Millisecond boundaries around the NTFS epoch.
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
        "1969-12-31T23:59:59.9990000Z"
    })
    void testFromUnixTime(final String instant) {
        final long epochSecond = Instant.parse(instant).getEpochSecond();
        // fromUnixTime takes Unix seconds, so reading the FileTime back as seconds
        // must yield the original epoch-second value.
        assertEquals(epochSecond, FileTimes.fromUnixTime(epochSecond).to(TimeUnit.SECONDS));
    }
}

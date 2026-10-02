package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testNtfsTimeToDate {

    private static Arguments ntfsDateCase(final String instant, final long ntfsTime) {
        return Arguments.of(instant, ntfsTime);
    }

    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(
                // NTFS epoch and nearby 100-nanosecond units.
                ntfsDateCase("1601-01-01T00:00:00.0000000Z", 0),
                ntfsDateCase("1601-01-01T00:00:00.0000001Z", 1),
                ntfsDateCase("1601-01-01T00:00:00.0000010Z", 10),
                ntfsDateCase("1601-01-01T00:00:00.0000100Z", 100),
                ntfsDateCase("1601-01-01T00:00:00.0001000Z", 1000),
                ntfsDateCase("1600-12-31T23:59:59.9999999Z", -1),

                // Extremes of the signed NTFS timestamp range.
                ntfsDateCase("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
                ntfsDateCase("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
                ntfsDateCase("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
                ntfsDateCase("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
                ntfsDateCase("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),
                ntfsDateCase("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
                ntfsDateCase("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
                ntfsDateCase("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
                ntfsDateCase("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
                ntfsDateCase("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

                // Millisecond boundaries around the NTFS epoch.
                ntfsDateCase("1601-01-01T00:00:00.0010000Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                ntfsDateCase("1601-01-01T00:00:00.0010001Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                ntfsDateCase("1601-01-01T00:00:00.0009999Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
                ntfsDateCase("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                ntfsDateCase("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
                ntfsDateCase("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),

                // Unix epoch and adjacent NTFS values.
                ntfsDateCase("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET),
                ntfsDateCase("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
                ntfsDateCase("1970-01-01T00:00:00.0010000Z",
                        -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
                ntfsDateCase("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
                ntfsDateCase("1969-12-31T23:59:59.9990000Z",
                        -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND));
    }

    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testNtfsTimeToDate(final String instant, final long ntfsTime) {
        assertEquals(Instant.parse(instant).toEpochMilli(), FileTimes.ntfsTimeToDate(ntfsTime).toInstant().toEpochMilli());
    }
}

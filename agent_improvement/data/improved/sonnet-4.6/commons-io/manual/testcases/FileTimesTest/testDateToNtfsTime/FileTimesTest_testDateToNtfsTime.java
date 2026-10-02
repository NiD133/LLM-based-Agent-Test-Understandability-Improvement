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

public class FileTimesTest_testDateToNtfsTime {

    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        // Each pair is (ISO-8601 instant string, expected NTFS 100-ns value).
        // NTFS epoch = 1601-01-01T00:00:00Z; negative values are before that epoch.
        return Stream.of(
            // --- NTFS epoch boundary ---
            Arguments.of("1601-01-01T00:00:00.0000000Z",  0),
            Arguments.of("1601-01-01T00:00:00.0000001Z",  1),
            Arguments.of("1601-01-01T00:00:00.0000010Z",  10),
            Arguments.of("1601-01-01T00:00:00.0000100Z",  100),
            Arguments.of("1601-01-01T00:00:00.0001000Z",  1000),
            // one 100-ns tick before the NTFS epoch (negative NTFS time)
            Arguments.of("1600-12-31T23:59:59.9999999Z",  -1),

            // --- Long.MAX_VALUE boundary (far future) ---
            Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE),
            Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1),
            Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10),
            Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100),
            Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000),

            // --- Long.MIN_VALUE boundary (far past) ---
            Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE),
            Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1),
            Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10),
            Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100),
            Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000),

            // --- Millisecond / HUNDRED_NANOS_PER_MILLISECOND boundary ---
            Arguments.of("1601-01-01T00:00:00.0010000Z",  FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1601-01-01T00:00:00.0010001Z",  FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1601-01-01T00:00:00.0009999Z",  FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),
            Arguments.of("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1),
            Arguments.of("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1),

            // --- Unix epoch (1970-01-01) expressed in NTFS units ---
            Arguments.of("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET),
            Arguments.of("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1),
            Arguments.of("1970-01-01T00:00:00.0010000Z", -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND),
            Arguments.of("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1),
            Arguments.of("1969-12-31T23:59:59.9990000Z", -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND)
        );
    }

    @ParameterizedTest
    @MethodSource("fileTimeNanoUnitsToNtfsProvider")
    void testDateToNtfsTime(final String instantStr, final long ntfsTime) {
        // Date has millisecond precision; truncate ntfsTime down to the nearest millisecond
        // boundary so that the expected value matches what toNtfsTime(Date) can represent.
        final long ntfsTimeAtMillisPrecision =
                Math.floorDiv(ntfsTime, FileTimes.HUNDRED_NANOS_PER_MILLISECOND)
                * FileTimes.HUNDRED_NANOS_PER_MILLISECOND;

        final Instant instant = Instant.parse(instantStr);
        final Date date = Date.from(instant);
        final long actualNtfsTime = FileTimes.toNtfsTime(date);

        // toNtfsTime(Date) clamps extreme values to Long.MIN/MAX_VALUE instead of overflowing,
        // so skip the millisecond-precision assertions for those sentinel results.
        if (actualNtfsTime != Long.MIN_VALUE && actualNtfsTime != Long.MAX_VALUE) {
            assertEquals(ntfsTimeAtMillisPrecision, actualNtfsTime);
            assertEquals(ntfsTimeAtMillisPrecision, FileTimes.toNtfsTime(date.getTime()));
            assertEquals(ntfsTimeAtMillisPrecision,
                    FileTimes.toNtfsTime(FileTimes.ntfsTimeToInstant(ntfsTime).toEpochMilli()));
        }

        // Full 100-ns precision is preserved via the Instant-based round-trip.
        assertEquals(ntfsTime, FileTimes.toNtfsTime(FileTimes.ntfsTimeToInstant(ntfsTime)));
    }
}

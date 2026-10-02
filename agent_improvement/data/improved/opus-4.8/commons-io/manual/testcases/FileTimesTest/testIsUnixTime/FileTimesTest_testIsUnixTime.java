package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#isUnixTime(FileTime)}.
 * <p>
 * A {@link FileTime} fits in "standard Unix time" only when its value in whole seconds since the
 * epoch falls within the signed 32-bit range, i.e. between {@link Integer#MIN_VALUE} and
 * {@link Integer#MAX_VALUE} inclusive. The upper bound corresponds to {@code 2038-01-19T03:14:07Z}
 * (the well-known "Year 2038" limit).
 * </p>
 */
public class FileTimesTest_testIsUnixTime {

    /**
     * Supplies (instant, expectedToFitInUnixTime) pairs covering instants inside the representable
     * range as well as instants just past the upper bound and far beyond it.
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
            // Inside the representable 32-bit-seconds range.
            Arguments.of("2022-12-27T12:45:22Z", true),
            // Exactly at the upper bound: Integer.MAX_VALUE seconds since the epoch.
            Arguments.of("2038-01-19T03:14:07Z", true),
            Arguments.of("1901-12-13T23:14:08Z", true),
            Arguments.of("1901-12-13T03:14:08Z", false),
            // One second past the upper bound, so it no longer fits.
            Arguments.of("2038-01-19T03:14:08Z", false),
            // Well beyond the upper bound.
            Arguments.of("2099-06-30T12:31:42Z", false));
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTime(final String instant, final boolean expectedToFitInUnixTime) {
        final FileTime fileTime = FileTime.from(Instant.parse(instant));

        assertEquals(expectedToFitInUnixTime, FileTimes.isUnixTime(fileTime));
    }
}

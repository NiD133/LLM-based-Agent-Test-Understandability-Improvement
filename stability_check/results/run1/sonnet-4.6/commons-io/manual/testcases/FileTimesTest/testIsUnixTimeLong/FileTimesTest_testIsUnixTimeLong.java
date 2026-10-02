package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testIsUnixTimeLong {

    /**
     * Provides (ISO-8601 instant string, expected isUnixTime result) pairs.
     *
     * Unix time is representable as a signed 32-bit integer, so valid values fall in
     * [Integer.MIN_VALUE, Integer.MAX_VALUE] seconds from epoch, i.e.
     *   min boundary: 1901-12-13T20:45:52Z  (Integer.MIN_VALUE seconds)
     *   max boundary: 2038-01-19T03:14:07Z  (Integer.MAX_VALUE seconds)
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
            // Within Unix time range
            Arguments.of("2022-12-27T12:45:22Z", true),   // ordinary modern date
            Arguments.of("2038-01-19T03:14:07Z", true),   // exactly at the max boundary (Integer.MAX_VALUE s)
            Arguments.of("1901-12-13T23:14:08Z", true),   // one second after the min boundary

            // Outside Unix time range
            Arguments.of("1901-12-13T03:14:08Z", false),  // before the min boundary
            Arguments.of("2038-01-19T03:14:08Z", false),  // one second past the max boundary
            Arguments.of("2099-06-30T12:31:42Z", false)   // far future, well beyond max boundary
        );
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTimeLong(final String instant, final boolean isUnixTime) {
        assertEquals(isUnixTime, FileTimes.isUnixTime(Instant.parse(instant).getEpochSecond()));
    }
}

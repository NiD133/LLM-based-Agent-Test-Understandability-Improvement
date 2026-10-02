package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testIsUnixTimeLong {

    /**
     * Unix time is representable as a 32-bit signed integer, covering the range
     * [Integer.MIN_VALUE, Integer.MAX_VALUE] seconds since the Unix epoch.
     *
     * Boundary values:
     *   MIN  = Integer.MIN_VALUE seconds = 1901-12-13T20:45:52Z
     *   MAX  = Integer.MAX_VALUE seconds = 2038-01-19T03:14:07Z  (Y2038 limit)
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
            // Ordinary date well inside the representable range
            Arguments.of("2022-12-27T12:45:22Z", true),

            // Exactly at the upper boundary (Integer.MAX_VALUE seconds)
            Arguments.of("2038-01-19T03:14:07Z", true),

            // One second above the lower boundary (Integer.MIN_VALUE + 1 seconds)
            Arguments.of("1901-12-13T23:14:08Z", true),

            // One second below the lower boundary (Integer.MIN_VALUE - 1 seconds)
            Arguments.of("1901-12-13T03:14:08Z", false),

            // One second above the upper boundary (Integer.MAX_VALUE + 1 seconds)
            Arguments.of("2038-01-19T03:14:08Z", false),

            // Far-future date well outside the representable range
            Arguments.of("2099-06-30T12:31:42Z", false)
        );
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTimeLong(final String instant, final boolean isUnixTime) {
        assertEquals(isUnixTime, FileTimes.isUnixTime(Instant.parse(instant).getEpochSecond()));
    }
}

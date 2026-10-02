package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testIsUnixTime {

    /**
     * Provides (ISO-8601 instant string, expected isUnixTime result) pairs.
     *
     * Unix time fits in a signed 32-bit integer, so the valid range is
     * [Integer.MIN_VALUE, Integer.MAX_VALUE] seconds since the Unix epoch:
     *   lower bound: 1901-12-13T20:45:52Z  (Integer.MIN_VALUE = -2147483648)
     *   upper bound: 2038-01-19T03:14:07Z  (Integer.MAX_VALUE =  2147483647)
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
            // A typical modern timestamp — well inside the valid range.
            Arguments.of("2022-12-27T12:45:22Z",  true),
            // Exactly the upper bound of Unix time (Integer.MAX_VALUE seconds).
            Arguments.of("2038-01-19T03:14:07Z",  true),
            // Exactly the lower bound of Unix time (Integer.MIN_VALUE seconds).
            Arguments.of("1901-12-13T23:14:08Z",  true),
            // One second before the lower bound — just outside the valid range.
            Arguments.of("1901-12-13T03:14:08Z",  false),
            // One second after the upper bound — just outside the valid range.
            Arguments.of("2038-01-19T03:14:08Z",  false),
            // Far in the future — well outside the valid range.
            Arguments.of("2099-06-30T12:31:42Z",  false)
        );
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTime(final String instant, final boolean isUnixTime) {
        assertEquals(isUnixTime, FileTimes.isUnixTime(FileTime.from(Instant.parse(instant))));
    }
}

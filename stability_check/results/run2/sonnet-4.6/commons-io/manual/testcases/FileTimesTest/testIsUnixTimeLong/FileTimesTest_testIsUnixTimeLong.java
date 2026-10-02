package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testIsUnixTimeLong {

    /**
     * Provides test cases for {@link FileTimes#isUnixTime(long)}.
     *
     * Unix time uses a 32-bit signed integer, so only seconds within the range
     * [Integer.MIN_VALUE, Integer.MAX_VALUE] are representable. The boundary
     * timestamps below are:
     *   - minimum representable:  1901-12-13T20:45:52Z  (Integer.MIN_VALUE seconds)
     *   - maximum representable:  2038-01-19T03:14:07Z  (Integer.MAX_VALUE seconds)
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
            // Within the valid Unix time range
            Arguments.of("2022-12-27T12:45:22Z",  true),   // ordinary modern date
            Arguments.of("2038-01-19T03:14:07Z",  true),   // Integer.MAX_VALUE (upper boundary, inclusive)
            Arguments.of("1901-12-13T23:14:08Z",  true),   // one second above Integer.MIN_VALUE

            // Outside the valid Unix time range
            Arguments.of("1901-12-13T03:14:08Z",  false),  // below Integer.MIN_VALUE boundary
            Arguments.of("2038-01-19T03:14:08Z",  false),  // one second past Integer.MAX_VALUE (overflow)
            Arguments.of("2099-06-30T12:31:42Z",  false)   // far-future date well beyond range
        );
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTimeLong(final String instant, final boolean isUnixTime) {
        long epochSeconds = Instant.parse(instant).getEpochSecond();
        assertEquals(isUnixTime, FileTimes.isUnixTime(epochSeconds));
    }
}

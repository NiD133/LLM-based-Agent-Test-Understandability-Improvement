package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#isUnixTime(long)}.
 * <p>
 * A number of seconds since the epoch is a valid Unix time only when it fits in
 * a signed 32-bit integer, i.e. it lies within
 * {@code [Integer.MIN_VALUE, Integer.MAX_VALUE]} seconds.
 * </p>
 */
public class FileTimesTest_testIsUnixTimeLong {

    /**
     * Supplies instants paired with whether their epoch-second value is representable as a Unix time.
     * <p>
     * Each argument is an ISO-8601 instant and the expected result of
     * {@link FileTimes#isUnixTime(long)} for that instant's epoch seconds.
     * </p>
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
            // Inside the signed 32-bit second range: valid Unix times.
            Arguments.of("2022-12-27T12:45:22Z", true),
            Arguments.of("2038-01-19T03:14:07Z", true),  // Integer.MAX_VALUE seconds (the upper boundary).
            Arguments.of("1901-12-13T23:14:08Z", true),  // Integer.MIN_VALUE seconds (the lower boundary).
            // Outside the signed 32-bit second range: not valid Unix times.
            Arguments.of("1901-12-13T03:14:08Z", false), // One range below Integer.MIN_VALUE.
            Arguments.of("2038-01-19T03:14:08Z", false), // One second past Integer.MAX_VALUE.
            Arguments.of("2099-06-30T12:31:42Z", false)
        );
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTimeLong(final String instant, final boolean expectedIsUnixTime) {
        final long epochSeconds = Instant.parse(instant).getEpochSecond();
        assertEquals(expectedIsUnixTime, FileTimes.isUnixTime(epochSeconds));
    }
}

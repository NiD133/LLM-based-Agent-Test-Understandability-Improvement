package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#isUnixTime(long)}.
 * <p>
 * A number of seconds since the epoch is a valid Unix time only when it fits in a
 * signed 32-bit integer, i.e. within {@code [Integer.MIN_VALUE, Integer.MAX_VALUE]}.
 * The cases below probe timestamps just inside and just outside that range.
 * </p>
 */
public class FileTimesTest_testIsUnixTimeLong {

    private static final boolean REPRESENTABLE = true;
    private static final boolean NOT_REPRESENTABLE = false;

    /**
     * Supplies (ISO-8601 instant, expected {@code isUnixTime} result) pairs.
     */
    public static Stream<Arguments> isUnixTimeProvider() {
        return Stream.of(
            // Within the 32-bit second range: representable as Unix time.
            Arguments.of("2022-12-27T12:45:22Z", REPRESENTABLE),
            Arguments.of("2038-01-19T03:14:07Z", REPRESENTABLE), // Integer.MAX_VALUE seconds.
            Arguments.of("1901-12-13T23:14:08Z", REPRESENTABLE),
            // Outside the 32-bit second range: not representable as Unix time.
            Arguments.of("1901-12-13T03:14:08Z", NOT_REPRESENTABLE), // just below Integer.MIN_VALUE.
            Arguments.of("2038-01-19T03:14:08Z", NOT_REPRESENTABLE), // just above Integer.MAX_VALUE.
            Arguments.of("2099-06-30T12:31:42Z", NOT_REPRESENTABLE));
    }

    @ParameterizedTest
    @MethodSource("isUnixTimeProvider")
    @DisplayName("isUnixTime(long) is true only for seconds within the 32-bit range")
    void testIsUnixTimeLong(final String isoInstant, final boolean expectedIsUnixTime) {
        final long epochSeconds = Instant.parse(isoInstant).getEpochSecond();
        assertEquals(expectedIsUnixTime, FileTimes.isUnixTime(epochSeconds));
    }
}

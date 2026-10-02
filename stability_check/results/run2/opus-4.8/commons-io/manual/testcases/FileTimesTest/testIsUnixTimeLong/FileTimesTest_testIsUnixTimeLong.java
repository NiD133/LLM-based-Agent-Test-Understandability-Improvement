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
 * Standard Unix time stores seconds-since-epoch in a signed 32-bit integer, so a value can be
 * represented as Unix time only when it fits within {@code [Integer.MIN_VALUE, Integer.MAX_VALUE]}.
 * The upper bound (Integer.MAX_VALUE seconds after the epoch) corresponds to 2038-01-19T03:14:07Z,
 * the well-known "Year 2038 problem" cutoff.
 * </p>
 */
public class FileTimesTest_testIsUnixTimeLong {

    /**
     * Provides an ISO-8601 instant together with whether its epoch-second value fits in standard Unix time.
     */
    static Stream<Arguments> epochSecondsWithinUnixRange() {
        return Stream.of(
                // Instants whose epoch-second fits within the signed 32-bit Unix range.
                Arguments.of("2022-12-27T12:45:22Z", true),  // a recent, ordinary date
                Arguments.of("2038-01-19T03:14:07Z", true),  // exactly Integer.MAX_VALUE seconds after the epoch
                Arguments.of("1901-12-13T23:14:08Z", true),  // exactly Integer.MIN_VALUE seconds after the epoch
                // Instants whose epoch-second falls outside the signed 32-bit Unix range.
                Arguments.of("1901-12-13T03:14:08Z", false), // one second below Integer.MIN_VALUE
                Arguments.of("2038-01-19T03:14:08Z", false), // one second above Integer.MAX_VALUE
                Arguments.of("2099-06-30T12:31:42Z", false)  // far beyond Integer.MAX_VALUE
        );
    }

    @ParameterizedTest
    @MethodSource("epochSecondsWithinUnixRange")
    void testIsUnixTimeLong(final String isoInstant, final boolean expectedWithinUnixRange) {
        final long epochSecond = Instant.parse(isoInstant).getEpochSecond();
        assertEquals(expectedWithinUnixRange, FileTimes.isUnixTime(epochSecond));
    }
}

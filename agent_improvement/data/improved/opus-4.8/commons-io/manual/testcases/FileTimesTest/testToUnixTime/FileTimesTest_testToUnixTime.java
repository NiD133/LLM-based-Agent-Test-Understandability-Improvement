package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link FileTimes#toUnixTime(FileTime)} in combination with
 * {@link FileTimes#isUnixTime(long)}.
 * <p>
 * Standard Unix time is stored as a signed 32-bit number of seconds since the epoch, so it can only
 * represent instants in the range {@link Integer#MIN_VALUE}..{@link Integer#MAX_VALUE} seconds. Each
 * case below converts an {@link Instant} to Unix seconds via {@code toUnixTime} and then asserts
 * whether {@code isUnixTime} considers the result to be in range.
 * </p>
 */
public class FileTimesTest_testToUnixTime {

    /**
     * Provides instants paired with the expected {@code isUnixTime} result for their Unix-second value.
     * <p>
     * The boundary instants correspond to {@link Integer#MAX_VALUE} (2038-01-19T03:14:07Z, the last
     * representable second) and {@link Integer#MIN_VALUE} (1901-12-13T20:45:52Z): instants within the
     * 32-bit range are expected to be {@code true}, instants just beyond it are expected to be {@code false}.
     * </p>
     *
     * @return arguments of (ISO-8601 instant, expected isUnixTime result).
     */
    public static Stream<Arguments> instantsWithExpectedIsUnixTime() {
        return Stream.of(
            // In range: a normal recent date.
            Arguments.of("2022-12-27T12:45:22Z", true),
            // In range: the maximum representable Unix second (Integer.MAX_VALUE).
            Arguments.of("2038-01-19T03:14:07Z", true),
            // In range: near the lower boundary.
            Arguments.of("1901-12-13T23:14:08Z", true),
            // Out of range: below Integer.MIN_VALUE seconds.
            Arguments.of("1901-12-13T03:14:08Z", false),
            // Out of range: one second past the maximum representable Unix second.
            Arguments.of("2038-01-19T03:14:08Z", false),
            // Out of range: far in the future, beyond Integer.MAX_VALUE seconds.
            Arguments.of("2099-06-30T12:31:42Z", false));
    }

    @ParameterizedTest(name = "{0} -> isUnixTime={1}")
    @MethodSource("instantsWithExpectedIsUnixTime")
    void testToUnixTime(final String isoInstant, final boolean expectedIsUnixTime) {
        final FileTime fileTime = FileTime.from(Instant.parse(isoInstant));
        final long unixSeconds = FileTimes.toUnixTime(fileTime);

        assertEquals(expectedIsUnixTime, FileTimes.isUnixTime(unixSeconds));
    }
}

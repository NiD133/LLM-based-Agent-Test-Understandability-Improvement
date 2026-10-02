package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link FileTimes#toUnixTime(FileTime)} returns a value that
 * {@link FileTimes#isUnixTime(long)} correctly classifies as within or outside
 * the 32-bit Unix time range [Integer.MIN_VALUE, Integer.MAX_VALUE] seconds.
 */
public class FileTimesTest_testToUnixTime {

    /**
     * Provides (instant, expectedIsUnixTime) pairs covering:
     * <ul>
     *   <li>a typical date well within the Unix time range</li>
     *   <li>the exact upper boundary (2038-01-19T03:14:07Z = Integer.MAX_VALUE seconds)</li>
     *   <li>a date just above the lower boundary — still representable</li>
     *   <li>a date just below the lower boundary — no longer representable</li>
     *   <li>one second past the upper boundary — no longer representable</li>
     *   <li>a far-future date well outside the range</li>
     * </ul>
     */
    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
            // Within range: ordinary modern date
            Arguments.of("2022-12-27T12:45:22Z", true),
            // At upper boundary: Integer.MAX_VALUE seconds since epoch
            Arguments.of("2038-01-19T03:14:07Z", true),
            // Just above lower boundary: still within Integer.MIN_VALUE
            Arguments.of("1901-12-13T23:14:08Z", true),
            // Just below lower boundary: exceeds Integer.MIN_VALUE
            Arguments.of("1901-12-13T03:14:08Z", false),
            // One second past upper boundary: exceeds Integer.MAX_VALUE
            Arguments.of("2038-01-19T03:14:08Z", false),
            // Far future: well outside the Unix time range
            Arguments.of("2099-06-30T12:31:42Z", false)
        );
    }

    @ParameterizedTest(name = "{0} should be representable as Unix time: {1}")
    @MethodSource("isUnixFileTimeProvider")
    void testToUnixTime(final String instant, final boolean expectedIsUnixTime) {
        FileTime fileTime = FileTime.from(Instant.parse(instant));
        long unixSeconds = FileTimes.toUnixTime(fileTime);
        assertEquals(expectedIsUnixTime, FileTimes.isUnixTime(unixSeconds));
    }
}

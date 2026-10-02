package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link SegmentUtils#countArgs(String)}, which counts the number of
 * arguments described by a JVM method descriptor. Each primitive (including
 * {@code long} and {@code double}) and each object/array type counts as one
 * argument.
 */
public class SegmentUtilsTest_testCountArgs {

    /**
     * Pairs of (method descriptor, expected argument count).
     */
    static Stream<Arguments> descriptorsAndExpectedArgCounts() {
        return Stream.of(
                Arguments.of("()V", 0),                      // no arguments
                Arguments.of("(D)V", 1),                     // single double
                Arguments.of("([D)V", 1),                    // double array
                Arguments.of("([[D)V", 1),                   // 2D double array
                Arguments.of("(DD)V", 2),                    // two doubles
                Arguments.of("(DDD)V", 3),                   // three doubles
                Arguments.of("(Lblah/blah;D)V", 2),          // object then double
                Arguments.of("(Lblah/blah;DLbLah;)V", 3));   // object, double, object
    }

    @ParameterizedTest
    @MethodSource("descriptorsAndExpectedArgCounts")
    void testCountArgs(final String descriptor, final int expectedArgsCount) {
        assertEquals(expectedArgsCount, SegmentUtils.countArgs(descriptor));
    }
}

package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SegmentUtilsTest_testCountArgs {

    /**
     * Provides (descriptor, expectedArgCount) pairs for testCountArgs.
     *
     * Each descriptor is a JVM method descriptor of the form "(args)return".
     * countArgs counts logical argument slots: arrays count as 1 regardless of
     * dimensions, object types (Lpath/to/Class;) count as 1, and primitive
     * types other than D/J also count as 1.
     */
    static Stream<Arguments> countArgs() {
        return Stream.of(
            Arguments.of("()V",                     0), // no arguments
            Arguments.of("(D)V",                    1), // one double
            Arguments.of("([D)V",                   1), // one-dimensional double array counts as 1
            Arguments.of("([[D)V",                  1), // two-dimensional double array still counts as 1
            Arguments.of("(DD)V",                   2), // two doubles
            Arguments.of("(DDD)V",                  3), // three doubles
            Arguments.of("(Lblah/blah;D)V",         2), // one object type + one double
            Arguments.of("(Lblah/blah;DLbLah;)V",  3)  // one object type + one double + one object type
        );
    }

    @ParameterizedTest
    @MethodSource("countArgs")
    void testCountArgs(final String descriptor, final int expectedArgsCount) {
        assertEquals(expectedArgsCount, SegmentUtils.countArgs(descriptor));
    }
}

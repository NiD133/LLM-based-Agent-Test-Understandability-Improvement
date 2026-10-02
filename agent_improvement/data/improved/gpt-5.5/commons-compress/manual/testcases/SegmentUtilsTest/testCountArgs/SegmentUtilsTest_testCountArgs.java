package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SegmentUtilsTest_testCountArgs {

    static Stream<Arguments> countArgs() {
        return Stream.of(
                Arguments.of("()V", 0),
                Arguments.of("(D)V", 1),
                Arguments.of("([D)V", 1),
                Arguments.of("([[D)V", 1),
                Arguments.of("(DD)V", 2),
                Arguments.of("(DDD)V", 3),
                Arguments.of("(Lblah/blah;D)V", 2),
                Arguments.of("(Lblah/blah;DLbLah;)V", 3));
    }

    @ParameterizedTest
    @MethodSource("countArgs")
    void testCountArgs(final String descriptor, final int expectedArgsCount) {
        assertEquals(expectedArgsCount, SegmentUtils.countArgs(descriptor));
    }
}

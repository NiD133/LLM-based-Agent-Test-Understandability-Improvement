package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link SegmentUtils#countInvokeInterfaceArgs(String)}.
 *
 * <p>For an invokeinterface call, every {@code long} ({@code J}) and {@code double} ({@code D})
 * argument counts as 2; every other argument (including array arguments) counts as 1.</p>
 */
public class SegmentUtilsTest_testCountInvokeInterfaceArgs {

    /**
     * Provides method descriptors paired with the expected invokeinterface argument count.
     *
     * @return a stream of (descriptor, expectedCount) argument pairs.
     */
    static Stream<Arguments> descriptorsAndExpectedCounts() {
        return Stream.of(
                Arguments.of("(Z)V", 1),                    // single boolean -> 1
                Arguments.of("(D)V", 2),                    // single double -> 2
                Arguments.of("(J)V", 2),                    // single long -> 2
                Arguments.of("([D)V", 1),                   // double array -> 1
                Arguments.of("([[D)V", 1),                  // 2D double array -> 1
                Arguments.of("(DD)V", 4),                   // two doubles -> 2 + 2
                Arguments.of("(Lblah/blah;D)V", 3),         // object + double -> 1 + 2
                Arguments.of("(Lblah/blah;DLbLah;)V", 4),   // object + double + object -> 1 + 2 + 1
                Arguments.of("([Lblah/blah;DLbLah;)V", 4)); // object array + double + object -> 1 + 2 + 1
    }

    @ParameterizedTest
    @MethodSource("descriptorsAndExpectedCounts")
    void testCountInvokeInterfaceArgs(final String descriptor, final int expectedCount) {
        assertEquals(expectedCount, SegmentUtils.countInvokeInterfaceArgs(descriptor));
    }
}

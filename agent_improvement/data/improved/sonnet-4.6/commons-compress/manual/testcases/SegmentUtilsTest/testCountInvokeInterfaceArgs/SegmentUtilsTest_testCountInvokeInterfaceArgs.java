package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link SegmentUtils#countInvokeInterfaceArgs(String)}.
 *
 * For invokeinterface, longs (J) and doubles (D) count as 2 slots each;
 * all other types (including arrays and object references) count as 1 slot.
 */
public class SegmentUtilsTest_testCountInvokeInterfaceArgs {

    static Stream<Arguments> countInvokeInterfaceArgs() {
        return Stream.of(
            // Single primitive (1-slot): boolean, char, int, etc. → 1
            Arguments.of("(Z)V", 1),
            // Single double (2-slot wide type) → 2
            Arguments.of("(D)V", 2),
            // Single long (2-slot wide type) → 2
            Arguments.of("(J)V", 2),
            // Single array of double (array ref = 1 slot) → 1
            Arguments.of("([D)V", 1),
            // Single 2-D array of double (array ref = 1 slot) → 1
            Arguments.of("([[D)V", 1),
            // Two doubles (2 + 2 slots) → 4
            Arguments.of("(DD)V", 4),
            // Object reference (1 slot) + double (2 slots) → 3
            Arguments.of("(Lblah/blah;D)V", 3),
            // Object reference (1) + double (2) + object reference (1) → 4
            Arguments.of("(Lblah/blah;DLbLah;)V", 4),
            // Array of object references (1) + double (2) + object reference (1) → 4
            Arguments.of("([Lblah/blah;DLbLah;)V", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("countInvokeInterfaceArgs")
    void testCountInvokeInterfaceArgs(final String descriptor, final int expectedCount) {
        assertEquals(expectedCount, SegmentUtils.countInvokeInterfaceArgs(descriptor));
    }
}

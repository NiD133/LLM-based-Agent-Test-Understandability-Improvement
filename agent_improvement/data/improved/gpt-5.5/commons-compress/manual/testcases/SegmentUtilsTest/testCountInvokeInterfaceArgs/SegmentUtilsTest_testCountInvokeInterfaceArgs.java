package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SegmentUtilsTest_testCountInvokeInterfaceArgs {

    private static final String VOID_RETURN = "V";

    static Stream<Arguments> countInvokeInterfaceArgs() {
        return Stream.of(
                invokeInterfaceArgumentCount("(Z)" + VOID_RETURN, 1),
                invokeInterfaceArgumentCount("(D)" + VOID_RETURN, 2),
                invokeInterfaceArgumentCount("(J)" + VOID_RETURN, 2),
                invokeInterfaceArgumentCount("([D)" + VOID_RETURN, 1),
                invokeInterfaceArgumentCount("([[D)" + VOID_RETURN, 1),
                invokeInterfaceArgumentCount("(DD)" + VOID_RETURN, 4),
                invokeInterfaceArgumentCount("(Lblah/blah;D)" + VOID_RETURN, 3),
                invokeInterfaceArgumentCount("(Lblah/blah;DLbLah;)" + VOID_RETURN, 4),
                invokeInterfaceArgumentCount("([Lblah/blah;DLbLah;)" + VOID_RETURN, 4));
    }

    private static Arguments invokeInterfaceArgumentCount(final String descriptor, final int expectedCount) {
        return Arguments.of(descriptor, expectedCount);
    }

    @ParameterizedTest
    @MethodSource("countInvokeInterfaceArgs")
    void testCountInvokeInterfaceArgs(final String descriptor, final int expectedCountInvokeInterfaceArgs) {
        assertEquals(expectedCountInvokeInterfaceArgs, SegmentUtils.countInvokeInterfaceArgs(descriptor));
    }
}

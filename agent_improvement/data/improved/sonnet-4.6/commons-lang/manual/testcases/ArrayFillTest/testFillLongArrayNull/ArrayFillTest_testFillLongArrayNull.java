package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillLongArrayNull extends AbstractLangTest {

    @Test
    @DisplayName("fill(long[], long) returns null unchanged when given a null array")
    void testFillLongArrayNull() {
        // ArrayFill.fill is a null-safe fluent helper: when the input array is null,
        // it must return null rather than throwing a NullPointerException.
        final long[] nullArray = null;
        final long fillValue = 1L;

        final long[] result = ArrayFill.fill(nullArray, fillValue);

        // The returned reference must be the exact same null that was passed in.
        assertSame(nullArray, result);
    }
}

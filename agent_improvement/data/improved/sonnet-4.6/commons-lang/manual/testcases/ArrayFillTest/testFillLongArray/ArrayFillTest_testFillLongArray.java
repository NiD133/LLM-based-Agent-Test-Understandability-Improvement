package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillLongArray extends AbstractLangTest {

    /**
     * Verifies that ArrayFill.fill returns the same array instance after filling
     * every element with the specified long value.
     */
    @Test
    void testFillLongArray() {
        final long fillValue = 1L;
        final int arrayLength = 3;
        final long[] inputArray = new long[arrayLength];

        final long[] resultArray = ArrayFill.fill(inputArray, fillValue);

        // The method must return the exact same array reference (fluent/in-place contract).
        assertSame(inputArray, resultArray,
                "ArrayFill.fill should return the same array instance it received");

        // Every element must equal the fill value.
        final long[] expectedArray = {fillValue, fillValue, fillValue};
        assertArrayEquals(expectedArray, resultArray,
                "Every element in the array should be set to " + fillValue);
    }
}

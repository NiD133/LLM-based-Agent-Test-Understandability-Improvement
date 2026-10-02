package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillByteArray extends AbstractLangTest {

    @Test
    @DisplayName("fill(byte[], byte) fills every element and returns the same array instance")
    void testFillByteArray() {
        final byte fillValue = 1;
        final byte[] inputArray = new byte[3];

        final byte[] result = ArrayFill.fill(inputArray, fillValue);

        // The method must return the exact same array object (fluent/in-place contract)
        assertSame(inputArray, result);

        // Every element must equal the fill value
        assertArrayEquals(new byte[]{fillValue, fillValue, fillValue}, result);
    }
}

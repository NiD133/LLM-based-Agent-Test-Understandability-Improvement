package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillByteArray extends AbstractLangTest {

    @Test
    void testFillByteArray() {
        final byte[] bytesToFill = new byte[3];
        final byte fillValue = 1;

        final byte[] returnedArray = ArrayFill.fill(bytesToFill, fillValue);

        assertSame(bytesToFill, returnedArray);
        for (final byte filledValue : returnedArray) {
            assertEquals(fillValue, filledValue);
        }
    }
}

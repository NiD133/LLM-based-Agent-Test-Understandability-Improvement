package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillByteArrayNull extends AbstractLangTest {

    @Test
    void testFillByteArrayNull() {
        final byte[] nullByteArray = null;
        final byte fillValue = 1;

        final byte[] returnedArray = ArrayFill.fill(nullByteArray, fillValue);

        assertSame(nullByteArray, returnedArray);
    }
}

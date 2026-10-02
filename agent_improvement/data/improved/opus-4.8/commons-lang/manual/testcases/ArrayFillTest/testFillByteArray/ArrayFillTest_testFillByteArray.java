package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillByteArray extends AbstractLangTest {

    @Test
    void testFillByteArray() {
        // Given a byte array and the value to fill it with.
        final byte[] array = new byte[3];
        final byte fillValue = 1;

        // When filling the array.
        final byte[] result = ArrayFill.fill(array, fillValue);

        // Then the same array instance is returned (fluent style, filled in place).
        assertSame(array, result);

        // And every element now holds the fill value.
        for (final byte element : result) {
            assertEquals(fillValue, element);
        }
    }
}

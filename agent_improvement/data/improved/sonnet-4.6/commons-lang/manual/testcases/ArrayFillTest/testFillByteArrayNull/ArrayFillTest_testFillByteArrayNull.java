package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillByteArrayNull extends AbstractLangTest {

    @Test
    @DisplayName("fill(byte[], byte) returns the same null reference when the input array is null")
    void testFillByteArrayNull() {
        // Arrange: a null array with an arbitrary fill value
        final byte[] nullArray = null;
        final byte fillValue = 1;

        // Act: fill() must tolerate null input without throwing
        final byte[] result = ArrayFill.fill(nullArray, fillValue);

        // Assert: the exact same null reference must be returned
        assertSame(nullArray, result, "fill() should return the original null reference unchanged");
    }
}

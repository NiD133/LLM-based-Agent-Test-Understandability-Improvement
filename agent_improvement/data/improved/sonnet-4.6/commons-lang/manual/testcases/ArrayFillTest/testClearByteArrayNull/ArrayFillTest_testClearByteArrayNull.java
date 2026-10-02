package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearByteArrayNull extends AbstractLangTest {

    @Test
    @DisplayName("clear(byte[]) returns null without throwing when the input array is null")
    void testClearByteArrayNull() {
        // ArrayFill.clear() guarantees null-safety: a null input must be returned as-is
        final byte[] nullArray = null;
        final byte[] result = ArrayFill.clear(nullArray);
        assertSame(nullArray, result, "clear(null) should return the same null reference, not throw");
    }
}

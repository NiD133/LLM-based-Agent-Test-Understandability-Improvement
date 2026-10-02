package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayNull extends AbstractLangTest {

    @Test
    @DisplayName("clear(char[]) returns the same null reference when input is null")
    void testClearCharArrayNull() {
        // ArrayFill.clear must tolerate null input and return null (same reference)
        final char[] nullArray = null;
        final char[] result = ArrayFill.clear(nullArray);
        assertSame(nullArray, result);
    }
}

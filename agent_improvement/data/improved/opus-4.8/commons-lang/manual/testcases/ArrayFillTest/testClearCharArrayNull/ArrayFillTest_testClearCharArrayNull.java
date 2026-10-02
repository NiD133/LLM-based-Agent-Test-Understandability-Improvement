package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ArrayFill#clear(char[])} tolerates a {@code null} input
 * by returning the same {@code null} reference instead of throwing.
 */
public class ArrayFillTest_testClearCharArrayNull extends AbstractLangTest {

    @Test
    void testClearCharArrayNull() {
        final char[] nullArray = null;

        final char[] result = ArrayFill.clear(nullArray);

        // clear() should pass the null reference straight back to the caller.
        assertSame(nullArray, result);
    }
}

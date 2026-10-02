package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(int[], int)} with a {@code null} array argument.
 */
public class ArrayFillTest_testFillIntArrayNull extends AbstractLangTest {

    @Test
    void testFillIntArrayNull() {
        // ArrayFill.fill should tolerate a null array and return it unchanged,
        // rather than throwing or allocating a new array.
        final int[] nullArray = null;
        final int fillValue = 1;

        final int[] result = ArrayFill.fill(nullArray, fillValue);

        // The exact same (null) reference is expected back.
        assertSame(nullArray, result);
    }
}

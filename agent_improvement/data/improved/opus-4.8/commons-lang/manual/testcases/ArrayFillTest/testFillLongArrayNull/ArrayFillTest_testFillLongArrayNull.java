package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(long[], long)} when the supplied array is null.
 */
public class ArrayFillTest_testFillLongArrayNull extends AbstractLangTest {

    /**
     * When the input {@code long[]} is null, {@link ArrayFill#fill(long[], long)}
     * should perform no work and return the very same null reference it was given.
     */
    @Test
    void testFillLongArrayNull() {
        final long[] nullArray = null;
        final long fillValue = 1;

        final long[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "Filling a null array should return the same null reference");
    }
}

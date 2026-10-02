package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(char[], char)} when the input array is {@code null}.
 */
public class ArrayFillTest_testFillCharArrayNull extends AbstractLangTest {

    /**
     * Filling a {@code null} char array should be a no-op that simply returns the
     * same {@code null} reference back, rather than throwing or allocating a new array.
     */
    @Test
    void testFillCharArrayNull() {
        final char[] nullArray = null;
        final char fillValue = 1;

        final char[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "fill(null, ...) should return the same null reference");
    }
}

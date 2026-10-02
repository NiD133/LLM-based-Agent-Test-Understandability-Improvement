package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(char[], int, int, char)}, the ranged char-array
 * overload that fills a sub-range {@code [fromIndex, toIndex)} of the array.
 */
public class ArrayFillTest_testFillCharArrayRange extends AbstractLangTest {

    @Test
    void testFillCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };
        final char fillValue = 'Z';
        final int fromIndexInclusive = 1;
        final int toIndexExclusive = 4;

        final char[] result = ArrayFill.fill(array, fromIndexInclusive, toIndexExclusive, fillValue);

        // The method fills the array in place and returns the same instance.
        assertSame(array, result);

        // Index 0 is before the range, so it keeps its original value.
        assertEquals('A', result[0]);

        // Indices 1, 2 and 3 lie within [1, 4) and are overwritten with 'Z'.
        assertEquals('Z', result[1]);
        assertEquals('Z', result[2]);
        assertEquals('Z', result[3]);

        // Index 4 equals toIndex (exclusive), so it keeps its original value.
        assertEquals('E', result[4]);
    }
}

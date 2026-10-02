package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(char[], int, int, char)} when the target range is empty.
 */
public class ArrayFillTest_testFillCharArrayRangeEmpty extends AbstractLangTest {

    @Test
    void testFillCharArrayRangeEmpty() {
        // An empty range has fromIndex == toIndex, so no element should be overwritten.
        final char[] array = { 'A', 'B', 'C' };
        final int fromIndexInclusive = 1;
        final int toIndexExclusive = 1;
        final char fillValue = 'Z';

        final char[] result = ArrayFill.fill(array, fromIndexInclusive, toIndexExclusive, fillValue);

        // fill returns the same array instance it was given.
        assertSame(array, result);
        // The empty range leaves the original contents untouched.
        assertArrayEquals(new char[] { 'A', 'B', 'C' }, result);
    }
}

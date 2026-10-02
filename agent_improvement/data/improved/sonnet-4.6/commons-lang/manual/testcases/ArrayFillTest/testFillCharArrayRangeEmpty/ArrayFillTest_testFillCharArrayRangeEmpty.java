package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRangeEmpty extends AbstractLangTest {

    @Test
    void testFillCharArrayRangeEmpty() {
        // fromIndex == toIndex defines an empty range, so no elements should be overwritten
        final char[] array = {'A', 'B', 'C'};
        final int fromIndex = 1;
        final int toIndex = 1; // same as fromIndex → empty range

        final char[] actual = ArrayFill.fill(array, fromIndex, toIndex, 'Z');

        assertSame(array, actual, "fill should return the same array instance");
        assertArrayEquals(new char[]{'A', 'B', 'C'}, actual, "array contents should be unchanged when range is empty");
    }
}

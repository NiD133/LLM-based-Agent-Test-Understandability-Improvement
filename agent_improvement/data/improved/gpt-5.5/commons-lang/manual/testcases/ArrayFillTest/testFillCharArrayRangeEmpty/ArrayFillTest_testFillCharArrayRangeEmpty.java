package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRangeEmpty extends AbstractLangTest {

    private static final int EMPTY_RANGE_INDEX = 1;
    private static final char FILL_VALUE = 'Z';

    @Test
    void testFillCharArrayRangeEmpty() {
        final char[] array = { 'A', 'B', 'C' };

        final char[] actual = ArrayFill.fill(array, EMPTY_RANGE_INDEX, EMPTY_RANGE_INDEX, FILL_VALUE);

        assertSame(array, actual);
        assertArrayEquals(new char[] { 'A', 'B', 'C' }, actual);
    }
}

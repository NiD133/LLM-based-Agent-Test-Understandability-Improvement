package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    private static final int RANGE_START_INCLUSIVE = 1;
    private static final int RANGE_END_EXCLUSIVE = 4;

    @Test
    void testClearCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };

        final char[] actual = ArrayFill.clear(array, RANGE_START_INCLUSIVE, RANGE_END_EXCLUSIVE);

        assertSame(array, actual);
        assertRangeWasCleared(actual);
    }

    private static void assertRangeWasCleared(final char[] actual) {
        assertEquals('A', actual[0]);
        assertEquals('\0', actual[1]);
        assertEquals('\0', actual[2]);
        assertEquals('\0', actual[3]);
        assertEquals('E', actual[4]);
    }
}

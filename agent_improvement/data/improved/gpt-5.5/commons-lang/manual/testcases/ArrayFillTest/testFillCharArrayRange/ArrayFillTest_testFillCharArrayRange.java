package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRange extends AbstractLangTest {

    @Test
    void testFillCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };
        final char[] expected = { 'A', 'Z', 'Z', 'Z', 'E' };
        final char valueToFill = 'Z';

        final char[] actual = ArrayFill.fill(array, 1, 4, valueToFill);

        assertSame(array, actual);
        assertArrayEquals(expected, actual);
    }
}

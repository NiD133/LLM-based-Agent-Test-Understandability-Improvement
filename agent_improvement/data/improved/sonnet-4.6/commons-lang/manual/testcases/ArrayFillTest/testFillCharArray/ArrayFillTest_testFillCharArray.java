package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArray extends AbstractLangTest {

    @Test
    void testFillCharArray() {
        final char fillValue = 'A';
        final char[] inputArray = new char[3];

        final char[] result = ArrayFill.fill(inputArray, fillValue);

        // fill() must return the exact same array instance (not a copy)
        assertSame(inputArray, result);
        // every element must equal the fill value
        assertArrayEquals(new char[] {'A', 'A', 'A'}, result);
    }
}

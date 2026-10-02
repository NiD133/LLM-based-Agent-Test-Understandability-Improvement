package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRange extends AbstractLangTest {

    @Test
    void testFillCharArrayRange() {
        // Array indices: 0='A', 1='B', 2='C', 3='D', 4='E'
        // fill(array, fromIndex=1, toIndex=4, val) fills indices 1, 2, 3 (toIndex is exclusive)
        final char[] array = {'A', 'B', 'C', 'D', 'E'};
        final char fillValue = 'Z';

        final char[] result = ArrayFill.fill(array, 1, 4, fillValue);

        // fill() returns the same array instance (fluent/in-place mutation)
        assertSame(array, result, "fill should return the same array instance");

        // Index 0 is before fromIndex=1, so it must remain unchanged
        assertEquals('A', result[0], "element before fill range should be unchanged");

        // Indices 1, 2, 3 are within [fromIndex=1, toIndex=4), so they must be filled with 'Z'
        assertEquals('Z', result[1], "first element in fill range should be 'Z'");
        assertEquals('Z', result[2], "middle element in fill range should be 'Z'");
        assertEquals('Z', result[3], "last element in fill range should be 'Z'");

        // Index 4 is at toIndex (exclusive), so it must remain unchanged
        assertEquals('E', result[4], "element at toIndex (exclusive) should be unchanged");
    }
}

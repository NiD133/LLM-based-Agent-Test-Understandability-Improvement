package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRange extends AbstractLangTest {

    @Test
    void testFillCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };
        final char val = 'Z';
        final char[] actual = ArrayFill.fill(array, 1, 4, val);
        assertSame(array, actual);
        assertEquals('A', actual[0]);
        assertEquals('Z', actual[1]);
        assertEquals('Z', actual[2]);
        assertEquals('Z', actual[3]);
        assertEquals('E', actual[4]);
    }
}

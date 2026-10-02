package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    @Test
    void testClearCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };
        final char[] actual = ArrayFill.clear(array, 1, 4);
        assertSame(array, actual);
        assertEquals('A', actual[0]);
        assertEquals('\0', actual[1]);
        assertEquals('\0', actual[2]);
        assertEquals('\0', actual[3]);
        assertEquals('E', actual[4]);
    }
}

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRangeEmpty extends AbstractLangTest {

    @Test
    void testFillCharArrayRangeEmpty() {
        final char[] array = { 'A', 'B', 'C' };
        final char[] actual = ArrayFill.fill(array, 1, 1, 'Z');
        assertSame(array, actual);
        assertArrayEquals(new char[] { 'A', 'B', 'C' }, actual);
    }
}

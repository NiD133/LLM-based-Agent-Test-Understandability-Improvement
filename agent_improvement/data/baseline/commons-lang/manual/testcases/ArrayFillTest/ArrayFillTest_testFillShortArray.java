package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillShortArray extends AbstractLangTest {

    @Test
    void testFillShortArray() {
        final short[] array = new short[3];
        final short val = 1;
        final short[] actual = ArrayFill.fill(array, val);
        assertSame(array, actual);
        for (final short v : actual) {
            assertEquals(val, v);
        }
    }
}

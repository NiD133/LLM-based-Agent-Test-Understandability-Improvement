package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearByteArray extends AbstractLangTest {

    @Test
    void testClearByteArray() {
        final byte[] array = new byte[3];
        final byte val = 0;
        final byte[] actual = ArrayFill.clear(array);
        assertSame(array, actual);
        for (final byte v : actual) {
            assertEquals(val, v);
        }
    }
}

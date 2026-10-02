package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearByteArray extends AbstractLangTest {

    @Test
    void testClearByteArray() {
        final byte[] array = new byte[3];
        final byte[] actual = ArrayFill.clear(array);

        assertSame(array, actual, "clear() should return the same array instance");
        assertArrayEquals(new byte[]{0, 0, 0}, actual, "clear() should set all bytes to zero");
    }
}

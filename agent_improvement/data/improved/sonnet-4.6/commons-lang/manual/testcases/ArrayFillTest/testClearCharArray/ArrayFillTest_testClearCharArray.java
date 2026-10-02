package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArray extends AbstractLangTest {

    @Test
    void testClearCharArray() {
        final char[] array = new char[3];
        final char[] result = ArrayFill.clear(array);

        assertSame(array, result);
        assertArrayEquals(new char[]{'\0', '\0', '\0'}, result);
    }
}

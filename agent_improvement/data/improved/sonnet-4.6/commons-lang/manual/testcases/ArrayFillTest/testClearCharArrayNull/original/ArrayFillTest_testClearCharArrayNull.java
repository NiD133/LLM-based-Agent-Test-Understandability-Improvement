package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayNull extends AbstractLangTest {

    @Test
    void testClearCharArrayNull() {
        final char[] array = null;
        final char[] actual = ArrayFill.clear(array);
        assertSame(array, actual);
    }
}

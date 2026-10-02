package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayNull extends AbstractLangTest {

    @Test
    void testClearCharArrayNull() {
        final char[] nullCharArray = null;

        final char[] clearedArray = ArrayFill.clear(nullCharArray);

        assertSame(nullCharArray, clearedArray);
    }
}

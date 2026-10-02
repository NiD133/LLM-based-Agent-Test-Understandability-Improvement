package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArray extends AbstractLangTest {

    @Test
    void testClearCharArray() {
        final char[] array = new char[3];
        final char expectedClearedValue = 0;

        final char[] actual = ArrayFill.clear(array);

        assertSame(array, actual);
        for (final char actualValue : actual) {
            assertEquals(expectedClearedValue, actualValue);
        }
    }
}

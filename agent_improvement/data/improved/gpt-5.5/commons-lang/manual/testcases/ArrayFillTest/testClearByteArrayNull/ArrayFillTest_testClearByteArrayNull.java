package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearByteArrayNull extends AbstractLangTest {

    @Test
    void testClearByteArrayNull() {
        final byte[] nullByteArray = null;

        final byte[] clearedArray = ArrayFill.clear(nullByteArray);

        assertSame(nullByteArray, clearedArray);
    }
}

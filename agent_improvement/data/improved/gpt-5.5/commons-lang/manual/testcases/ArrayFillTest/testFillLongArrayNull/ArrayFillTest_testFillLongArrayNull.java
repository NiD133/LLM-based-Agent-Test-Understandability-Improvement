package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillLongArrayNull extends AbstractLangTest {

    @Test
    void testFillLongArrayNull() {
        final long[] nullLongArray = null;
        final long fillValue = 1L;

        final long[] filledArray = ArrayFill.fill(nullLongArray, fillValue);

        assertSame(nullLongArray, filledArray);
    }
}

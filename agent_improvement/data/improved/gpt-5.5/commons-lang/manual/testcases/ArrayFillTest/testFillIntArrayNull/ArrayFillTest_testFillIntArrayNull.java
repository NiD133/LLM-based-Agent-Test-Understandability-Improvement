package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillIntArrayNull extends AbstractLangTest {

    @Test
    void testFillIntArrayNull() {
        final int[] nullArray = null;
        final int fillValue = 1;

        final int[] filledArray = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, filledArray);
    }
}

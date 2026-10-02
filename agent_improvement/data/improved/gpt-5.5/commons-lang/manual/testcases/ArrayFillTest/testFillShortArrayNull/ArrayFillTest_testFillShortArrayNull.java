package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillShortArrayNull extends AbstractLangTest {

    @Test
    void testFillShortArrayNull() {
        final short[] inputArray = null;
        final short fillValue = 1;

        final short[] filledArray = ArrayFill.fill(inputArray, fillValue);

        assertSame(inputArray, filledArray);
    }
}

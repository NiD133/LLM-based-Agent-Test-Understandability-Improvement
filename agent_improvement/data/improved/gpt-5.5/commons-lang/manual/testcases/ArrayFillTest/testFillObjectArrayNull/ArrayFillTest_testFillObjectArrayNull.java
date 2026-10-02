package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillObjectArrayNull extends AbstractLangTest {

    @Test
    void testFillObjectArrayNull() {
        final Object[] nullArray = null;
        final Object fillValue = 1;

        final Object[] filledArray = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, filledArray);
    }
}

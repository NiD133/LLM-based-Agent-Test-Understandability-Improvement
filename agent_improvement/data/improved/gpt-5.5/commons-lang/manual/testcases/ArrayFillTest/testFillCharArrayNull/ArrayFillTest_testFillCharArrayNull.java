package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayNull extends AbstractLangTest {

    @Test
    void testFillCharArrayNull() {
        final char[] nullArray = null;
        final char fillValue = 1;

        final char[] filledArray = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, filledArray);
    }
}

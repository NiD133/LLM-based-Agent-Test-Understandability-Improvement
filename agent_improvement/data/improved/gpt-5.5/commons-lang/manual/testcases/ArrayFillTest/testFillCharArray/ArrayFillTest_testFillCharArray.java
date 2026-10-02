package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArray extends AbstractLangTest {

    @Test
    void testFillCharArray() {
        final char[] arrayToFill = new char[3];
        final char fillValue = 1;

        final char[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        assertSame(arrayToFill, filledArray);
        for (final char element : filledArray) {
            assertEquals(fillValue, element);
        }
    }
}

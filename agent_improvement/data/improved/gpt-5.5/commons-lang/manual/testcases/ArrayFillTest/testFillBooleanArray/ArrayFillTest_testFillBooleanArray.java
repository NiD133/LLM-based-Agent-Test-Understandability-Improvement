package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillBooleanArray extends AbstractLangTest {

    @Test
    void testFillBooleanArray() {
        final boolean[] arrayToFill = new boolean[3];
        final boolean fillValue = true;

        final boolean[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        assertSame(arrayToFill, filledArray);
        assertArrayEquals(new boolean[] {true, true, true}, filledArray);
    }
}

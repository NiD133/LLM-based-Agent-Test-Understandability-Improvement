package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillObjectArray extends AbstractLangTest {

    @Test
    void testFillObjectArray() {
        final String[] arrayToFill = new String[3];
        final String fillValue = "A";

        final String[] returnedArray = ArrayFill.fill(arrayToFill, fillValue);

        assertSame(arrayToFill, returnedArray);
        for (final String filledElement : returnedArray) {
            assertEquals(fillValue, filledElement);
        }
    }
}

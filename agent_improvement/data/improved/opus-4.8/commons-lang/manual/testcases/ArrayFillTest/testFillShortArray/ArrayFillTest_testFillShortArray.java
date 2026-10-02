package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(short[], short)}.
 */
public class ArrayFillTest_testFillShortArray extends AbstractLangTest {

    @Test
    void testFillShortArray() {
        final short[] inputArray = new short[3];
        final short fillValue = 1;

        final short[] returnedArray = ArrayFill.fill(inputArray, fillValue);

        // fill should return the very same array instance it was given.
        assertSame(inputArray, returnedArray);
        // Every element of the array should now hold the fill value.
        for (final short element : returnedArray) {
            assertEquals(fillValue, element);
        }
    }
}

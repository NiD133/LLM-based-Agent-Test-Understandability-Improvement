package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(float[], float)}.
 */
public class ArrayFillTest_testFillFloatArray extends AbstractLangTest {

    @Test
    void testFillFloatArray() {
        final float fillValue = 1;
        final float[] arrayToFill = new float[3];

        final float[] returnedArray = ArrayFill.fill(arrayToFill, fillValue);

        // fill returns the same array instance it was given (fluent style).
        assertSame(arrayToFill, returnedArray);
        // Every element should now equal the fill value.
        for (final float element : returnedArray) {
            assertEquals(fillValue, element);
        }
    }
}

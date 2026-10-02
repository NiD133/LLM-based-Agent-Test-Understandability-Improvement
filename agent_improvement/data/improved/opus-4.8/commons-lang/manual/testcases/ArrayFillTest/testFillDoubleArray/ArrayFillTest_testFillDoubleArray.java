package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(double[], double)}.
 */
public class ArrayFillTest_testFillDoubleArray extends AbstractLangTest {

    @Test
    void testFillDoubleArray() {
        final double[] arrayToFill = new double[3];
        final double fillValue = 1;

        final double[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        // fill returns the same array instance it was given, not a copy.
        assertSame(arrayToFill, filledArray);
        // Every element should now hold the fill value.
        for (final double element : filledArray) {
            assertEquals(fillValue, element);
        }
    }
}

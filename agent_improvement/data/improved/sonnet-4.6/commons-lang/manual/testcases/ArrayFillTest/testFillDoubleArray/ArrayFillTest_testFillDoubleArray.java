package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillDoubleArray extends AbstractLangTest {

    @Test
    void testFillDoubleArray() {
        final double fillValue = 1.0;
        final double[] array = new double[3];

        final double[] result = ArrayFill.fill(array, fillValue);

        // fill() must return the same array instance (fluent API contract)
        assertSame(array, result);
        // every element must equal the fill value
        assertArrayEquals(new double[]{fillValue, fillValue, fillValue}, result);
    }
}

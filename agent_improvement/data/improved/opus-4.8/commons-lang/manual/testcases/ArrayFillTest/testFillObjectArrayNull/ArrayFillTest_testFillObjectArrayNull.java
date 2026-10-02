package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(Object[], Object)} when the supplied array is {@code null}.
 */
public class ArrayFillTest_testFillObjectArrayNull extends AbstractLangTest {

    /**
     * A {@code null} array cannot be filled, so {@code fill} should simply return the
     * same {@code null} reference it was given rather than throwing.
     */
    @Test
    void testFillObjectArrayNull() {
        final Object[] nullArray = null;
        final Object fillValue = 1;

        final Object[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "fill(null, value) should return the original null array");
    }
}

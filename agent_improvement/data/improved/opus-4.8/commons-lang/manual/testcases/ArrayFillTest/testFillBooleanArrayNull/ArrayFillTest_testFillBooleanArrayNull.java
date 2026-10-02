package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(boolean[], boolean)} when the input array is {@code null}.
 */
public class ArrayFillTest_testFillBooleanArrayNull extends AbstractLangTest {

    /**
     * Filling a {@code null} boolean array should be a no-op: the method must
     * return the very same {@code null} reference it was given, without throwing.
     */
    @Test
    void testFillBooleanArrayNull() {
        final boolean[] nullArray = null;
        final boolean fillValue = true;

        final boolean[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "fill(null, ...) should return the same null reference");
    }
}

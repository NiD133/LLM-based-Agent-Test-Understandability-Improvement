package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(short[], short)} when the supplied array is {@code null}.
 */
public class ArrayFillTest_testFillShortArrayNull extends AbstractLangTest {

    /**
     * When the input {@code short[]} is {@code null}, {@code fill} should be a no-op
     * and return the same {@code null} reference rather than throwing.
     */
    @Test
    void testFillShortArrayNull() {
        final short[] nullArray = null;
        final short fillValue = 1;

        final short[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "Filling a null short[] should return the same null reference");
    }
}

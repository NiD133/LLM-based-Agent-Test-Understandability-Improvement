package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillIntArrayNull extends AbstractLangTest {

    // fill() must return the same null reference when given a null array, leaving it unchanged
    @Test
    void testFillIntArrayNull() {
        final int[] array = null;
        final int[] actual = ArrayFill.fill(array, 1);
        assertSame(array, actual);
    }
}

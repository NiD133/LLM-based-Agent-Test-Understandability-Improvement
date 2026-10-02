package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillBooleanArrayNull extends AbstractLangTest {

    @Test
    @DisplayName("fill(boolean[], boolean) returns null when the input array is null")
    void testFillBooleanArrayNull() {
        final boolean[] nullArray = null;
        final boolean[] result = ArrayFill.fill(nullArray, true);
        assertNull(result);
    }
}

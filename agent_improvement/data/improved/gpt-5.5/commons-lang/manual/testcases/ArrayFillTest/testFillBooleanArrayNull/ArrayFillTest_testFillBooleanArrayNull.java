package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillBooleanArrayNull extends AbstractLangTest {

    @Test
    void testFillBooleanArrayNull() {
        final boolean[] nullBooleanArray = null;
        final boolean fillValue = true;

        final boolean[] filledArray = ArrayFill.fill(nullBooleanArray, fillValue);

        assertSame(nullBooleanArray, filledArray);
    }
}

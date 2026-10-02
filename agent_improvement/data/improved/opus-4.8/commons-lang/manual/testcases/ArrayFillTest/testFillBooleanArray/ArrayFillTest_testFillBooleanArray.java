package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(boolean[], boolean)}.
 */
public class ArrayFillTest_testFillBooleanArray extends AbstractLangTest {

    @Test
    void testFillBooleanArray() {
        final boolean[] arrayToFill = new boolean[3];
        final boolean fillValue = true;

        final boolean[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        // fill should return the very same array instance it was given.
        assertSame(arrayToFill, filledArray);
        // every element should now hold the fill value.
        for (final boolean element : filledArray) {
            assertEquals(fillValue, element);
        }
    }
}

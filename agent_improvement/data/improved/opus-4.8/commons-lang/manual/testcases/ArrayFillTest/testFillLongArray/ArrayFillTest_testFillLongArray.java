package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(long[], long)}.
 */
public class ArrayFillTest_testFillLongArray extends AbstractLangTest {

    @Test
    void testFillLongArray() {
        final long[] arrayToFill = new long[3];
        final long fillValue = 1L;

        final long[] returnedArray = ArrayFill.fill(arrayToFill, fillValue);

        // fill returns the same array instance it was given (fluent style).
        assertSame(arrayToFill, returnedArray);
        // Every element should now hold the fill value.
        for (final long element : returnedArray) {
            assertEquals(fillValue, element);
        }
    }
}

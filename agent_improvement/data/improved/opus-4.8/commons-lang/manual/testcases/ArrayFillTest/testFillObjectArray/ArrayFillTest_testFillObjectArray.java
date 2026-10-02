package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(Object[], Object)}.
 */
public class ArrayFillTest_testFillObjectArray extends AbstractLangTest {

    @Test
    void testFillObjectArray() {
        // Given an empty (null-filled) object array and a value to populate it with.
        final String[] arrayToFill = new String[3];
        final String fillValue = "A";

        // When filling the array.
        final String[] returnedArray = ArrayFill.fill(arrayToFill, fillValue);

        // Then fill returns the same array instance it was given (fluent style).
        assertSame(arrayToFill, returnedArray);

        // And every element now holds the fill value.
        for (final String element : returnedArray) {
            assertEquals(fillValue, element);
        }
    }
}

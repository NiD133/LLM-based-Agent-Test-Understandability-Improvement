package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayNull extends AbstractLangTest {

    /**
     * ArrayFill.fill is null-safe: when the input array is null it must return null
     * without throwing any exception.
     */
    @Test
    @DisplayName("fill(char[], char) returns null when the input array is null")
    void testFillCharArrayNull() {
        // Arrange: a null array and an arbitrary fill value
        final char[] nullArray = null;
        final char fillValue = 1;

        // Act: fill should tolerate a null array gracefully
        final char[] result = ArrayFill.fill(nullArray, fillValue);

        // Assert: the original null reference is returned unchanged
        assertNull(result);
    }
}

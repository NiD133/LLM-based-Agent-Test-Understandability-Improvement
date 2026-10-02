package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillObjectArray extends AbstractLangTest {

    @Test
    @DisplayName("fill(T[], T) fills every element with the given value and returns the same array instance")
    void testFillObjectArray() {
        // Arrange: a 3-element String array and the value to fill it with
        final String[] array = new String[3];
        final String val = "A";

        // Act: fill the array in place
        final String[] actual = ArrayFill.fill(array, val);

        // Assert: the original array reference is returned and every slot holds val
        assertSame(array, actual);
        assertArrayEquals(new String[] {"A", "A", "A"}, actual);
    }
}

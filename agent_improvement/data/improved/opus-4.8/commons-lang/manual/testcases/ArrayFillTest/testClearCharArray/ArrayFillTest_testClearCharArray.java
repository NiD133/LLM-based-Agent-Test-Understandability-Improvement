package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#clear(char[])}.
 */
public class ArrayFillTest_testClearCharArray extends AbstractLangTest {

    @Test
    void testClearCharArray() {
        // clear(char[]) should set every element to the NUL character '\0'.
        final char[] input = new char[3];
        final char[] expected = {'\0', '\0', '\0'};

        final char[] result = ArrayFill.clear(input);

        // The method fills in place and returns the very same array instance.
        assertSame(input, result, "clear should return the same array instance");
        assertArrayEquals(expected, result, "every element should be the NUL character");
    }
}

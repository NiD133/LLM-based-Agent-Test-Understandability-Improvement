package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#clear(char[], int, int)}, which resets a slice of a
 * char array to the NUL character ('\0').
 */
public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    /** The NUL character that {@code clear} writes into each cleared element. */
    private static final char NUL = '\0';

    @Test
    void testClearCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };

        // Clear indices 1..3 (fromIndex=1 inclusive, toIndex=4 exclusive),
        // leaving the first and last elements untouched.
        final char[] cleared = ArrayFill.clear(array, 1, 4);

        // clear() fills in place and returns the same array instance.
        assertSame(array, cleared);

        // Elements outside the [1, 4) range keep their original values.
        assertEquals('A', cleared[0]);
        assertEquals('E', cleared[4]);

        // Elements inside the [1, 4) range are reset to NUL.
        assertEquals(NUL, cleared[1]);
        assertEquals(NUL, cleared[2]);
        assertEquals(NUL, cleared[3]);
    }
}

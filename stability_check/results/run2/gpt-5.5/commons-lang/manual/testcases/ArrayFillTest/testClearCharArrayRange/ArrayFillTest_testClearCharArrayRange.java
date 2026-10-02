package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    @Test
    void testClearCharArrayRange() {
        final int fromIndex = 1;
        final int toIndex = 4;
        final char[] characters = { 'A', 'B', 'C', 'D', 'E' };

        final char[] clearedCharacters = ArrayFill.clear(characters, fromIndex, toIndex);

        assertSame(characters, clearedCharacters);
        assertEquals('A', clearedCharacters[0]);
        assertEquals('\0', clearedCharacters[1]);
        assertEquals('\0', clearedCharacters[2]);
        assertEquals('\0', clearedCharacters[3]);
        assertEquals('E', clearedCharacters[4]);
    }
}

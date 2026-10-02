package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_combo extends AbstractLangTest {

    @Test
    void testConstructor_String_combo() {
        assertRanges("literal characters are stored separately", "abc", 3, ranges -> {
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.is('b')));
            assertTrue(ranges.contains(CharRange.is('c')));
        });

        assertRanges("adjacent ranges are parsed from left to right", "a-ce-f", 2, ranges -> {
            assertTrue(ranges.contains(CharRange.isIn('a', 'c')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
        });

        assertRanges("a literal may precede a range", "ae-f", 2, ranges -> {
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
        });

        assertRanges("a literal may follow a range", "e-fa", 2, ranges -> {
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
        });

        assertRanges("literals and ranges can be combined", "ae-fm-pz", 4, ranges -> {
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
            assertTrue(ranges.contains(CharRange.isIn('m', 'p')));
            assertTrue(ranges.contains(CharRange.is('z')));
        });
    }

    private void assertRanges(
            final String description,
            final String setDefinition,
            final int expectedRangeCount,
            final Consumer<Set<CharRange>> expectedRanges) {
        final CharSet set = CharSet.getInstance(setDefinition);
        final Set<CharRange> ranges = set.getCharRanges();

        assertEquals(expectedRangeCount, ranges.size(), description);
        expectedRanges.accept(ranges);
    }
}

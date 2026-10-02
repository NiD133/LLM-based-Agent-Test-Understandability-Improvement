package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_simple extends AbstractLangTest {

    @Test
    void testConstructor_String_simple() {
        assertCharSet((String) null, "[]", 0, null);
        assertCharSet("", "[]", 0, null);
        assertCharSet("a", "[a]", 1, "a");
        assertCharSet("^a", "[^a]", 1, "^a");
        assertCharSet("a-e", "[a-e]", 1, "a-e");
        assertCharSet("^a-e", "[^a-e]", 1, "^a-e");
    }

    private void assertCharSet(final String setDefinition, final String expectedSetText,
            final int expectedRangeCount, final String expectedRangeText) {
        final CharSet set = CharSet.getInstance(setDefinition);
        final Set<CharRange> ranges = set.getCharRanges();

        assertEquals(expectedSetText, set.toString());
        assertEquals(expectedRangeCount, ranges.size());
        if (expectedRangeText != null) {
            assertEquals(expectedRangeText, ranges.iterator().next().toString());
        }
    }
}

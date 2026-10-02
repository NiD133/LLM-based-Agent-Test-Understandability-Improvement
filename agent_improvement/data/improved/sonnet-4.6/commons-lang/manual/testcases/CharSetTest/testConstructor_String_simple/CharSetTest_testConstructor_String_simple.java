package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_simple extends AbstractLangTest {

    /**
     * Asserts that a CharSet constructed from the given string descriptor contains
     * exactly one CharRange whose string form matches {@code expectedRangeStr}, and
     * that the overall set string matches {@code expectedSetStr}.
     */
    private void assertSingleRange(CharSet set, String expectedSetStr, String expectedRangeStr) {
        Set<CharRange> ranges = set.getCharRanges();
        assertEquals(expectedSetStr, set.toString());
        assertEquals(1, ranges.size());
        assertEquals(expectedRangeStr, ranges.iterator().next().toString());
    }

    @Test
    void testGetInstance_nullInput_producesEmptySet() {
        CharSet set = CharSet.getInstance((String) null);
        Set<CharRange> ranges = set.getCharRanges();
        assertEquals("[]", set.toString());
        assertEquals(0, ranges.size());
    }

    @Test
    void testGetInstance_emptyString_producesEmptySet() {
        CharSet set = CharSet.getInstance("");
        Set<CharRange> ranges = set.getCharRanges();
        assertEquals("[]", set.toString());
        assertEquals(0, ranges.size());
    }

    @Test
    void testGetInstance_singleChar_producesSingleCharRange() {
        CharSet set = CharSet.getInstance("a");
        assertSingleRange(set, "[a]", "a");
    }

    @Test
    void testGetInstance_negatedSingleChar_producesNegatedCharRange() {
        CharSet set = CharSet.getInstance("^a");
        assertSingleRange(set, "[^a]", "^a");
    }

    @Test
    void testGetInstance_charRange_producesRangeEntry() {
        CharSet set = CharSet.getInstance("a-e");
        assertSingleRange(set, "[a-e]", "a-e");
    }

    @Test
    void testGetInstance_negatedCharRange_producesNegatedRangeEntry() {
        CharSet set = CharSet.getInstance("^a-e");
        assertSingleRange(set, "[^a-e]", "^a-e");
    }
}

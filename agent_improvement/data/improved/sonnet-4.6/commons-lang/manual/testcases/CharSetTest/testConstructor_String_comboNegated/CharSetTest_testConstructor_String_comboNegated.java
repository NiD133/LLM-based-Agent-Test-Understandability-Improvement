package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests CharSet.getInstance() with combination patterns that include negated characters ('^').
 * A '^' before a character or range means "all characters except this one/range".
 * When combined with regular characters, the resulting CharSet contains multiple CharRange entries.
 */
public class CharSetTest_testConstructor_String_comboNegated extends AbstractLangTest {

    /** Asserts that a CharRange set has the expected size and contains the given range. */
    private void assertContains(Set<CharRange> ranges, int expectedSize, CharRange... expectedRanges) {
        assertEquals(expectedSize, ranges.size());
        for (CharRange range : expectedRanges) {
            assertTrue(ranges.contains(range));
        }
    }

    @Test
    void testConstructor_String_comboNegated() {
        CharSet set;
        Set<CharRange> ranges;

        // Pattern "^abc": '^' negates only the immediately following character 'a';
        // 'b' and 'c' are treated as regular (non-negated) single-character ranges.
        set = CharSet.getInstance("^abc");
        ranges = set.getCharRanges();
        assertContains(ranges, 3,
                CharRange.isNot('a'),
                CharRange.is('b'),
                CharRange.is('c'));

        // Pattern "b^ac": '^' in the middle negates 'a'; 'b' and 'c' are regular ranges.
        set = CharSet.getInstance("b^ac");
        ranges = set.getCharRanges();
        assertContains(ranges, 3,
                CharRange.is('b'),
                CharRange.isNot('a'),
                CharRange.is('c'));

        // Pattern "db^ac": four-character combo where '^' negates 'a';
        // 'd', 'b', and 'c' are regular ranges, giving four total ranges.
        set = CharSet.getInstance("db^ac");
        ranges = set.getCharRanges();
        assertContains(ranges, 4,
                CharRange.is('d'),
                CharRange.is('b'),
                CharRange.isNot('a'),
                CharRange.is('c'));

        // Pattern "^b^a": two negated single-character ranges with no regular characters.
        set = CharSet.getInstance("^b^a");
        ranges = set.getCharRanges();
        assertContains(ranges, 2,
                CharRange.isNot('b'),
                CharRange.isNot('a'));

        // Pattern "b^a-c^z": 'b' is a regular range, '^a-c' is a negated range (a through c),
        // and '^z' is a negated single character — three ranges in total.
        set = CharSet.getInstance("b^a-c^z");
        ranges = set.getCharRanges();
        assertContains(ranges, 3,
                CharRange.is('b'),
                CharRange.isNotIn('a', 'c'),
                CharRange.isNot('z'));
    }
}

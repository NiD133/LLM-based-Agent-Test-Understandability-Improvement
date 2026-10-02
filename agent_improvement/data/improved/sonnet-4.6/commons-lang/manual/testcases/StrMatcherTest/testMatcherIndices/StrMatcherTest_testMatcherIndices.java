package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testMatcherIndices extends AbstractLangTest {

    // "abcdef" — positions: a=0, b=1, c=2, d=3, e=4, f=5
    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testMatcherIndices() {
        // The API contract for isMatch() places all responsibility on the caller.
        // bufferStart and bufferEnd define which slice of the buffer is "active";
        // the matcher may not access positions outside [bufferStart, bufferEnd).
        // Invalid inputs (e.g. bufferEnd smaller than required) are the caller's
        // problem — the matcher simply reports 0 (no match) when the target
        // string cannot fit within the active window.
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");

        // Case 1: standard match — "bc" starts at index 1 and fits fully within
        // the active window [1, 6), so the matcher returns 2 (length of "bc").
        int pos = 1;
        int bufferStart = 1;
        int bufferEnd = BUFFER2.length; // 6
        assertEquals(2, matcher.isMatch(BUFFER2, pos, bufferStart, bufferEnd),
                "Should match 'bc' at index 1 when the full buffer is active");

        // Case 2: match with a wider active window starting before pos — "bc"
        // occupies indices 1..2, which fits within [0, 3), so the match succeeds.
        bufferStart = 0;
        bufferEnd = 3;
        assertEquals(2, matcher.isMatch(BUFFER2, pos, bufferStart, bufferEnd),
                "Should match 'bc' at index 1 when active window [0, 3) still covers it");

        // Case 3: match blocked by a tight bufferEnd — "bc" would need indices
        // 1..2 but bufferEnd=2 means only index 1 is accessible, so pos+len=3
        // exceeds bufferEnd=2 and the matcher returns 0 (no match).
        bufferEnd = 2;
        assertEquals(0, matcher.isMatch(BUFFER2, pos, bufferStart, bufferEnd),
                "Should not match 'bc' at index 1 when bufferEnd=2 cuts off the second character");
    }
}

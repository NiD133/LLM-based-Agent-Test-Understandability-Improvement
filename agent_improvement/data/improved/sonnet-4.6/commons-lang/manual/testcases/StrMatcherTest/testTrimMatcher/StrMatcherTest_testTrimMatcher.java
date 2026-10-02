package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StrMatcher#trimMatcher()}.
 * <p>
 * The trim matcher considers a character to match if its code point is &lt;= 32,
 * covering all ASCII control characters and the space character — the same set
 * that {@link String#trim()} strips.  It returns 1 for a match and 0 otherwise.
 * </p>
 */
@Deprecated
public class StrMatcherTest_testTrimMatcher extends AbstractLangTest {

    // Mixed buffer: printable characters interleaved with whitespace/control characters.
    // Index:  0    1    2     3     4    5    6    7     8     9     10       11    12
    // Char:  '0'  ','  '1'  '\t'  '2'  ' '  '3'  '\n'  '\r'  '\f'  '\0'  '\''  '"'
    private static final char[] BUFFER1 = {
        '0', ',', '1', '\t', '2', ' ', '3', '\n', '\r', '\f', '\0', '\'', '"'
    };

    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testTrimMatcher() {
        final StrMatcher matcher = StrMatcher.trimMatcher();

        // trimMatcher() always returns the same singleton instance
        assertSame(matcher, StrMatcher.trimMatcher());

        // Printable characters (code point > 32) do not match
        assertEquals(0, matcher.isMatch(BUFFER1, 2));  // '1'   (code 49)  → no match
        assertEquals(1, matcher.isMatch(BUFFER1, 3));  // '\t'  (code  9)  → match (tab)
        assertEquals(0, matcher.isMatch(BUFFER1, 4));  // '2'   (code 50)  → no match
        assertEquals(1, matcher.isMatch(BUFFER1, 5));  // ' '   (code 32)  → match (space)
        assertEquals(0, matcher.isMatch(BUFFER1, 6));  // '3'   (code 51)  → no match

        // Control characters (code point <= 32) each match with length 1
        assertEquals(1, matcher.isMatch(BUFFER1, 7));  // '\n'  (code 10)  → match (newline)
        assertEquals(1, matcher.isMatch(BUFFER1, 8));  // '\r'  (code 13)  → match (carriage return)
        assertEquals(1, matcher.isMatch(BUFFER1, 9));  // '\f'  (code 12)  → match (form feed)
        assertEquals(1, matcher.isMatch(BUFFER1, 10)); // '\0'  (code  0)  → match (null character)
    }
}

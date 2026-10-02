package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#noneMatcher()}, the matcher that never matches
 * any character regardless of the buffer contents or position.
 */
@Deprecated
public class StrMatcherTest_testNoneMatcher extends AbstractLangTest {

    /** The NUL control character (U+0000) included in the test buffer. */
    private static final char NUL = (char) 0;

    /**
     * A buffer holding a deliberately varied mix of characters: digits,
     * separators, every kind of whitespace, a NUL control character and
     * quotes. The none-matcher must report "no match" at every position.
     */
    private static final char[] MIXED_CONTENT = {
        '0', ',', '1', '\t', '2', ' ', '3', '\n', '\r', '\f', NUL, '\'', '"',
    };

    /** The number of matching characters reported when nothing matches. */
    private static final int NO_MATCH = 0;

    @Test
    void testNoneMatcher() {
        final StrMatcher noneMatcher = StrMatcher.noneMatcher();

        // noneMatcher() always hands back the same shared singleton instance.
        assertSame(noneMatcher, StrMatcher.noneMatcher());

        // No position in the buffer should ever match.
        for (int pos = 0; pos < MIXED_CONTENT.length; pos++) {
            assertEquals(NO_MATCH, noneMatcher.isMatch(MIXED_CONTENT, pos),
                    "noneMatcher should not match at position " + pos);
        }
    }
}

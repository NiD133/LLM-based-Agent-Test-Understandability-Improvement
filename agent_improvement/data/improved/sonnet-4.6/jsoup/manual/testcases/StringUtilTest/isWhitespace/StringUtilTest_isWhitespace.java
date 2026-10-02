package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link StringUtil#isWhitespace(int)}, which recognises the five
 * ASCII whitespace characters defined by the HTML specification.
 */
public class StringUtilTest_isWhitespace {

    // --- characters that must be recognised as whitespace ---

    @Test
    public void spaceIsWhitespace() {
        assertTrue(StringUtil.isWhitespace(' '));
    }

    @Test
    public void tabIsWhitespace() {
        assertTrue(StringUtil.isWhitespace('\t'));
    }

    @Test
    public void newlineIsWhitespace() {
        assertTrue(StringUtil.isWhitespace('\n'));
    }

    @Test
    public void carriageReturnIsWhitespace() {
        assertTrue(StringUtil.isWhitespace('\r'));
    }

    @Test
    public void formFeedIsWhitespace() {
        assertTrue(StringUtil.isWhitespace('\f'));
    }

    // --- Unicode space-like characters that must NOT be treated as whitespace ---

    @Test
    public void nonBreakingSpaceIsNotWhitespace() {
        // U+00A0 NON-BREAKING SPACE: visually space-like but absent from the HTML spec whitespace set
        assertFalse(StringUtil.isWhitespace(0x00a0));
    }

    @Test
    public void enQuadIsNotWhitespace() {
        // U+2000 EN QUAD: a Unicode typographic space, absent from the HTML spec whitespace set
        assertFalse(StringUtil.isWhitespace(0x2000));
    }

    @Test
    public void ideographicSpaceIsNotWhitespace() {
        // U+3000 IDEOGRAPHIC SPACE: CJK full-width space, absent from the HTML spec whitespace set
        assertFalse(StringUtil.isWhitespace(0x3000));
    }
}

package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link StringUtil#normaliseWhitespace(String)}, which collapses runs of whitespace
 * into a single space and converts every whitespace character (newline, tab, etc.) into a space.
 */
public class StringUtilTest_normaliseWhiteSpace {

    @Test
    public void normaliseWhiteSpace() {
        // A string made up entirely of whitespace collapses to a single space.
        assertEquals(" ", normaliseWhitespace("    \r \n \r\n"));

        // Internal runs of mixed whitespace each collapse to one space; leading/trailing whitespace is kept as one space.
        assertEquals(" hello there ", normaliseWhitespace("   hello   \r \n  there    \n"));

        // A string with no whitespace is returned unchanged.
        assertEquals("hello", normaliseWhitespace("hello"));

        // A single newline between words becomes a single space.
        assertEquals("hello there", normaliseWhitespace("hello\nthere"));
    }
}

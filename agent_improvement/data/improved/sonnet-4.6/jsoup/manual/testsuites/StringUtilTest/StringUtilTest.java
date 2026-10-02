package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {

    /** Returns a string of {@code count} space characters, for readable expected-value construction. */
    private static String spaces(int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }

    @Test
    void join() {
        assertEquals("", StringUtil.join(Collections.singletonList(""), " "));
        assertEquals("one", StringUtil.join(Collections.singletonList("one"), " "));
        assertEquals("one two three", StringUtil.join(Arrays.asList("one", "two", "three"), " "));
    }

    @Test
    void paddingWithDefaultMax() {
        // Default max is 30; requests above 30 are silently capped to 30 spaces
        assertEquals("", StringUtil.padding(0));
        assertEquals(spaces(1), StringUtil.padding(1));
        assertEquals(spaces(2), StringUtil.padding(2));
        assertEquals(spaces(15), StringUtil.padding(15));
        assertEquals(spaces(30), StringUtil.padding(45)); // 45 requested, capped at 30
    }

    @Test
    void paddingWithUnlimitedMax() {
        // maxPaddingWidth == -1 removes the cap entirely
        // Requests within the memoized range (0-20) are served from the cache
        assertEquals("", StringUtil.padding(0, -1));
        assertEquals(spaces(20), StringUtil.padding(20, -1));

        // Requests beyond the memoized range (> 20) are computed on the fly
        assertEquals(spaces(21), StringUtil.padding(21, -1));
        assertEquals(spaces(30), StringUtil.padding(30, -1));
        assertEquals(spaces(45), StringUtil.padding(45, -1));
    }

    @Test
    void paddingWithZeroMax() {
        // maxPaddingWidth == 0 caps every request to an empty string
        assertEquals("", StringUtil.padding(0, 0));
        assertEquals("", StringUtil.padding(21, 0)); // beyond memoized range, still capped to 0
    }

    @Test
    void paddingWithExplicitMax() {
        // maxPaddingWidth == 30 caps requests above 30, even within the memoized range
        assertEquals("", StringUtil.padding(0, 30));
        assertEquals(spaces(1), StringUtil.padding(1, 30));
        assertEquals(spaces(2), StringUtil.padding(2, 30));
        assertEquals(spaces(15), StringUtil.padding(15, 30));
        assertEquals(spaces(30), StringUtil.padding(45, 30)); // 45 requested, capped at 30

        // maxPaddingWidth also limits values in the memoized range
        assertEquals(5, StringUtil.padding(20, 5).length());
    }

    @Test
    void paddingMemoizationTable() {
        // The static memoized array covers exactly 21 entries: 0 spaces through 20 spaces
        String[] memoized = StringUtil.padding;
        assertEquals(21, memoized.length);
        for (int i = 0; i < memoized.length; i++) {
            assertEquals(i, memoized[i].length());
        }
    }

    @Test
    void isBlank() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank("      "));
        assertTrue(StringUtil.isBlank("   \r\n  "));

        assertFalse(StringUtil.isBlank("hello"));
        assertFalse(StringUtil.isBlank("   hello   "));
    }

    @Test
    void isNumeric() {
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric(" "));
        assertFalse(StringUtil.isNumeric("123 546")); // space in the middle
        assertFalse(StringUtil.isNumeric("hello"));
        assertFalse(StringUtil.isNumeric("123.334")); // decimal point is not a digit

        assertTrue(StringUtil.isNumeric("1"));
        assertTrue(StringUtil.isNumeric("1234"));
    }

    @Test
    void isWhitespace() {
        // HTML-spec whitespace characters
        assertTrue(StringUtil.isWhitespace('\t'));
        assertTrue(StringUtil.isWhitespace('\n'));
        assertTrue(StringUtil.isWhitespace('\r'));
        assertTrue(StringUtil.isWhitespace('\f'));
        assertTrue(StringUtil.isWhitespace(' '));

        // Non-breaking and ideographic spaces are not considered HTML-spec whitespace
        assertFalse(StringUtil.isWhitespace(' ')); // non-breaking space
        assertFalse(StringUtil.isWhitespace(' ')); // en quad
        assertFalse(StringUtil.isWhitespace('　')); // ideographic space
    }

    @Test
    void normaliseWhiteSpace() {
        // Sequences of mixed whitespace characters collapse to a single space
        assertEquals(" ", normaliseWhitespace("    \r \n \r\n"));
        assertEquals(" hello there ", normaliseWhitespace("   hello   \r \n  there    \n"));
        // Non-whitespace input passes through unchanged
        assertEquals("hello", normaliseWhitespace("hello"));
        assertEquals("hello there", normaliseWhitespace("hello\nthere"));
    }

    @Test
    void normaliseWhiteSpaceHandlesHighSurrogates() {
        // Supplementary characters expressed as surrogate pairs must be preserved;
        // only the adjacent spaces should collapse to a single space
        String inputWithSurrogatePair = "𪚲か゚  1";
        String expectedWithSingleSpace = "𪚲か゚ 1";

        assertEquals(expectedWithSingleSpace, normaliseWhitespace(inputWithSurrogatePair));
        assertEquals(expectedWithSingleSpace, Jsoup.parse(inputWithSurrogatePair).text());
    }

    @Test
    void resolvesRelativeUrls() {
        // Path-relative resolution
        assertEquals("http://example.com/one/two?three", resolve("http://example.com", "./one/two?three"));
        assertEquals("http://example.com/one/two?three", resolve("http://example.com?one", "./one/two?three"));
        assertEquals("http://example.com/one/two?three#four", resolve("http://example.com", "./one/two?three#four"));

        // Absolute URL in relUrl supersedes the base completely
        assertEquals("https://example.com/one", resolve("http://example.com/", "https://example.com/one"));
        assertEquals("https://example2.com/one", resolve("http://example.com/", "https://example2.com/one"));

        // Protocol-relative URL inherits the scheme from the base
        assertEquals("https://example2.com/one", resolve("https://example.com/", "//example2.com/one"));

        // Parent-directory traversal with ".."
        assertEquals("http://example.com/one/two.html", resolve("http://example.com/two/", "../one/two.html"));

        // Port number in the base is preserved during resolution
        assertEquals("https://example.com:8080/one", resolve("https://example.com:8080", "./one"));

        // Invalid base: fall back to relUrl if it is itself absolute
        assertEquals("https://example.com/one", resolve("wrong", "https://example.com/one"));

        // Empty relUrl returns the base URL unchanged
        assertEquals("https://example.com/one", resolve("https://example.com/one", ""));

        // Both base and relUrl are invalid -> empty string
        assertEquals("", resolve("wrong", "also wrong"));

        // FTP scheme is supported
        assertEquals("ftp://example.com/one", resolve("ftp://example.com/two/", "../one"));
        assertEquals("ftp://example.com/one/two.c", resolve("ftp://example.com/one/", "./two.c"));
        assertEquals("ftp://example.com/one/two.c", resolve("ftp://example.com/one/", "two.c"));
    }

    @Test
    void resolvesRfc3986AbnormalRelativePaths() {
        // Examples taken from RFC 3986 section 5.4.2 -- abnormal path-segment resolution
        String base = "http://example.com/b/c/d;p?q";

        // Excess ".." segments beyond the root are collapsed to "/"
        assertEquals("http://example.com/g", resolve(base, "../../../g"));
        assertEquals("http://example.com/g", resolve(base, "../../../../g"));

        // Absolute references starting with "/." or "/.." are normalised
        assertEquals("http://example.com/g", resolve(base, "/./g"));
        assertEquals("http://example.com/g", resolve(base, "/../g"));

        // Trailing dots are literal characters, not path-traversal segments
        assertEquals("http://example.com/b/c/g.", resolve(base, "g."));
        assertEquals("http://example.com/b/c/.g", resolve(base, ".g"));
        assertEquals("http://example.com/b/c/g..", resolve(base, "g.."));
        assertEquals("http://example.com/b/c/..g", resolve(base, "..g"));

        // Mixed single-dot and double-dot traversal
        assertEquals("http://example.com/b/g", resolve(base, "./../g"));
        assertEquals("http://example.com/b/c/g/", resolve(base, "./g/."));
        assertEquals("http://example.com/b/c/g/h", resolve(base, "g/./h"));
        assertEquals("http://example.com/b/c/h", resolve(base, "g/../h"));

        // Path parameters combined with traversal segments
        assertEquals("http://example.com/b/c/g;x=1/y", resolve(base, "g;x=1/./y"));
        assertEquals("http://example.com/b/c/y", resolve(base, "g;x=1/../y"));

        // Dot-segments inside query strings are not resolved as path traversal
        assertEquals("http://example.com/b/c/g?y/./x", resolve(base, "g?y/./x"));
        assertEquals("http://example.com/b/c/g?y/../x", resolve(base, "g?y/../x"));

        // Dot-segments inside fragment identifiers are not resolved as path traversal
        assertEquals("http://example.com/b/c/g#s/./x", resolve(base, "g#s/./x"));
        assertEquals("http://example.com/b/c/g#s/../x", resolve(base, "g#s/../x"));
    }

    @Test
    void stripsControlCharsFromUrls() {
        // Control characters (U+0000-U+001F: tab, newline, carriage return, backspace, etc.)
        // are stripped from both base and relative URLs before resolution
        assertEquals("foo:bar", resolve("\nhttps://\texample.com/", "\r\nfo\to:ba\br"));
    }

    @Test
    void allowsSpaceInUrl() {
        assertEquals("https://example.com/foo bar/", resolve("HTTPS://example.com/example/", "../foo bar/"));
    }

    @Test
    void isAscii() {
        assertTrue(StringUtil.isAscii(""));
        assertTrue(StringUtil.isAscii("example.com"));
        assertTrue(StringUtil.isAscii("One Two"));
        assertFalse(StringUtil.isAscii("🧔")); // emoji (U+1F9D4, outside ASCII range)
        assertFalse(StringUtil.isAscii("测试")); // Chinese characters (ce shi)
        assertFalse(StringUtil.isAscii("测试.com")); // mixed non-ASCII and ASCII
    }

    @Test
    void isAsciiLetter() {
        // Lowercase a-z
        assertTrue(StringUtil.isAsciiLetter('a'));
        assertTrue(StringUtil.isAsciiLetter('n'));
        assertTrue(StringUtil.isAsciiLetter('z'));

        // Uppercase A-Z
        assertTrue(StringUtil.isAsciiLetter('A'));
        assertTrue(StringUtil.isAsciiLetter('N'));
        assertTrue(StringUtil.isAsciiLetter('Z'));

        // Not letters: space, hyphen, digit, and extended/accented characters
        assertFalse(StringUtil.isAsciiLetter(' '));
        assertFalse(StringUtil.isAsciiLetter('-'));
        assertFalse(StringUtil.isAsciiLetter('0'));
        assertFalse(StringUtil.isAsciiLetter('ß')); // sharp s (ss ligature)
        assertFalse(StringUtil.isAsciiLetter('Ě')); // E with caron
    }

    @Test
    void isDigit() {
        // All ten ASCII decimal digits must be recognised
        for (char d = '0'; d <= '9'; d++) {
            assertTrue(StringUtil.isDigit(d), "expected '" + d + "' to be a digit");
        }

        // Letters and non-ASCII digit forms must be rejected
        assertFalse(StringUtil.isDigit('a'));
        assertFalse(StringUtil.isDigit('A'));
        assertFalse(StringUtil.isDigit('ä')); // Latin small a with diaeresis
        assertFalse(StringUtil.isDigit('Ä')); // Latin capital A with diaeresis
        assertFalse(StringUtil.isDigit('١')); // Arabic-Indic digit one
        assertFalse(StringUtil.isDigit('୳')); // Oriya digit three
    }

    @Test
    void isHexDigit() {
        // Decimal digits 0-9 are valid hex digits
        for (char d = '0'; d <= '9'; d++) {
            assertTrue(StringUtil.isHexDigit(d), "expected '" + d + "' to be a hex digit");
        }
        // Lowercase hex letters a-f
        for (char d = 'a'; d <= 'f'; d++) {
            assertTrue(StringUtil.isHexDigit(d), "expected '" + d + "' to be a hex digit");
        }
        // Uppercase hex letters A-F
        for (char d = 'A'; d <= 'F'; d++) {
            assertTrue(StringUtil.isHexDigit(d), "expected '" + d + "' to be a hex digit");
        }

        // Letters beyond 'f'/'F' and non-ASCII digit forms must be rejected
        assertFalse(StringUtil.isHexDigit('g'));
        assertFalse(StringUtil.isHexDigit('G'));
        assertFalse(StringUtil.isHexDigit('ä')); // Latin small a with diaeresis
        assertFalse(StringUtil.isHexDigit('Ä')); // Latin capital A with diaeresis
        assertFalse(StringUtil.isHexDigit('١')); // Arabic-Indic digit one
        assertFalse(StringUtil.isHexDigit('୳')); // Oriya digit three
    }
}

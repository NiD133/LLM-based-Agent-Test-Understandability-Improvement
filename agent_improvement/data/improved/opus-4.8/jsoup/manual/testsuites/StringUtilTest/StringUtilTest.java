package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {

    @Test
    public void join() {
        // A single empty element joins to the empty string.
        assertEquals("", StringUtil.join(Collections.singletonList(""), " "));
        // A single element is returned as-is, with no separator added.
        assertEquals("one", StringUtil.join(Collections.singletonList("one"), " "));
        // Multiple elements are concatenated with the separator between each.
        assertEquals("one two three", StringUtil.join(Arrays.asList("one", "two", "three"), " "));
    }

    @Test public void padding() {
        // padding(width) uses a default maximum width of 30.
        assertEquals("", StringUtil.padding(0));
        assertEquals(" ", StringUtil.padding(1));
        assertEquals("  ", StringUtil.padding(2));
        assertEquals("               ", StringUtil.padding(15));
        assertEquals("                              ", StringUtil.padding(45)); // we default to tap out at 30

        // memoization is up to 21 blocks (0 to 20 spaces) and exits early before min checks making maxPaddingWidth unused
        assertEquals("", StringUtil.padding(0, -1));
        assertEquals("                    ", StringUtil.padding(20, -1));

        // this test escapes memoization and continues through
        assertEquals("                     ", StringUtil.padding(21, -1));

        // this test escapes memoization and using unlimited length (-1) will allow requested spaces
        assertEquals("                              ", StringUtil.padding(30, -1));
        assertEquals("                                             ", StringUtil.padding(45, -1));

        // we tap out at 0 for this test
        assertEquals("", StringUtil.padding(0, 0));

        // as memoization is escaped, setting zero for max padding will not allow any requested width
        assertEquals("", StringUtil.padding(21, 0));

        // we tap out at 30 for these tests making > 30 use 30
        assertEquals("", StringUtil.padding(0, 30));
        assertEquals(" ", StringUtil.padding(1, 30));
        assertEquals("  ", StringUtil.padding(2, 30));
        assertEquals("               ", StringUtil.padding(15, 30));
        assertEquals("                              ", StringUtil.padding(45, 30));

        // max applies regardless of memoized
        assertEquals(5, StringUtil.padding(20, 5).length());
    }

    @Test public void paddingInACan() {
        // The memoised padding table holds blocks of 0 to 20 spaces (21 entries),
        // where entry[i] is a string of exactly i spaces.
        String[] padding = StringUtil.padding;
        assertEquals(21, padding.length);
        for (int i = 0; i < padding.length; i++) {
            assertEquals(i, padding[i].length());
        }
    }

    @Test public void isBlank() {
        // Blank: null, empty, or whitespace-only (including \r\n).
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank("      "));
        assertTrue(StringUtil.isBlank("   \r\n  "));

        // Not blank: contains at least one non-whitespace character.
        assertFalse(StringUtil.isBlank("hello"));
        assertFalse(StringUtil.isBlank("   hello   "));
    }

    @Test public void isNumeric() {
        // Not numeric: null, whitespace, embedded spaces, letters, or a decimal point.
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric(" "));
        assertFalse(StringUtil.isNumeric("123 546"));
        assertFalse(StringUtil.isNumeric("hello"));
        assertFalse(StringUtil.isNumeric("123.334"));

        // Numeric: only ASCII digit characters.
        assertTrue(StringUtil.isNumeric("1"));
        assertTrue(StringUtil.isNumeric("1234"));
    }

    @Test public void isWhitespace() {
        // The five characters treated as whitespace by the HTML spec.
        assertTrue(StringUtil.isWhitespace('\t'));
        assertTrue(StringUtil.isWhitespace('\n'));
        assertTrue(StringUtil.isWhitespace('\r'));
        assertTrue(StringUtil.isWhitespace('\f'));
        assertTrue(StringUtil.isWhitespace(' '));

        // Unicode spaces that are NOT HTML whitespace (escapes kept to stay unambiguous in source).
        assertFalse(StringUtil.isWhitespace(' ')); // non-breaking space
        assertFalse(StringUtil.isWhitespace(' ')); // en quad
        assertFalse(StringUtil.isWhitespace('　')); // ideographic space
    }

    @Test public void normaliseWhiteSpace() {
        // Runs of whitespace collapse to a single space; each whitespace char becomes a plain space.
        assertEquals(" ", normaliseWhitespace("    \r \n \r\n"));
        assertEquals(" hello there ", normaliseWhitespace("   hello   \r \n  there    \n"));
        assertEquals("hello", normaliseWhitespace("hello"));
        assertEquals("hello there", normaliseWhitespace("hello\nthere"));
    }

    @Test public void normaliseWhiteSpaceHandlesHighSurrogates() {
        // Input mixes a supplementary (surrogate-pair) code point with a combining mark, then two spaces and a digit.
        // Normalisation must preserve the multi-char code points and collapse the two spaces into one.
        // Escapes kept (instead of literal glyphs) so the surrogate pair stays unambiguous in source.
        String test71540chars = "𪚲か゚  1";
        String test71540charsExpectedSingleWhitespace = "𪚲か゚ 1";

        assertEquals(test71540charsExpectedSingleWhitespace, normaliseWhitespace(test71540chars));
        String extractedText = Jsoup.parse(test71540chars).text();
        assertEquals(test71540charsExpectedSingleWhitespace, extractedText);
    }

    @Test public void resolvesRelativeUrls() {
        // Basic relative resolution against an absolute base.
        assertEquals("http://example.com/one/two?three", resolve("http://example.com", "./one/two?three"));
        assertEquals("http://example.com/one/two?three", resolve("http://example.com?one", "./one/two?three"));
        assertEquals("http://example.com/one/two?three#four", resolve("http://example.com", "./one/two?three#four"));

        // An already-absolute relUrl is returned unchanged (possibly with a different scheme).
        assertEquals("https://example.com/one", resolve("http://example.com/", "https://example.com/one"));
        assertEquals("http://example.com/one/two.html", resolve("http://example.com/two/", "../one/two.html"));
        assertEquals("https://example2.com/one", resolve("https://example.com/", "//example2.com/one"));
        assertEquals("https://example.com:8080/one", resolve("https://example.com:8080", "./one"));
        assertEquals("https://example2.com/one", resolve("http://example.com/", "https://example2.com/one"));

        // When the base is unusable, an absolute relUrl still resolves on its own.
        assertEquals("https://example.com/one", resolve("wrong", "https://example.com/one"));
        // An empty relUrl resolves to the base.
        assertEquals("https://example.com/one", resolve("https://example.com/one", ""));
        // Neither base nor relUrl is a valid/absolute URL: empty result.
        assertEquals("", resolve("wrong", "also wrong"));

        // Non-http schemes (ftp) resolve the same way.
        assertEquals("ftp://example.com/one", resolve("ftp://example.com/two/", "../one"));
        assertEquals("ftp://example.com/one/two.c", resolve("ftp://example.com/one/", "./two.c"));
        assertEquals("ftp://example.com/one/two.c", resolve("ftp://example.com/one/", "two.c"));

        // examples taken from rfc3986 section 5.4.2
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "../../../g"));
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "../../../../g"));
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "/./g"));
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "/../g"));
        assertEquals("http://example.com/b/c/g.", resolve("http://example.com/b/c/d;p?q", "g."));
        assertEquals("http://example.com/b/c/.g", resolve("http://example.com/b/c/d;p?q", ".g"));
        assertEquals("http://example.com/b/c/g..", resolve("http://example.com/b/c/d;p?q", "g.."));
        assertEquals("http://example.com/b/c/..g", resolve("http://example.com/b/c/d;p?q", "..g"));
        assertEquals("http://example.com/b/g", resolve("http://example.com/b/c/d;p?q", "./../g"));
        assertEquals("http://example.com/b/c/g/", resolve("http://example.com/b/c/d;p?q", "./g/."));
        assertEquals("http://example.com/b/c/g/h", resolve("http://example.com/b/c/d;p?q", "g/./h"));
        assertEquals("http://example.com/b/c/h", resolve("http://example.com/b/c/d;p?q", "g/../h"));
        assertEquals("http://example.com/b/c/g;x=1/y", resolve("http://example.com/b/c/d;p?q", "g;x=1/./y"));
        assertEquals("http://example.com/b/c/y", resolve("http://example.com/b/c/d;p?q", "g;x=1/../y"));
        assertEquals("http://example.com/b/c/g?y/./x", resolve("http://example.com/b/c/d;p?q", "g?y/./x"));
        assertEquals("http://example.com/b/c/g?y/../x", resolve("http://example.com/b/c/d;p?q", "g?y/../x"));
        assertEquals("http://example.com/b/c/g#s/./x", resolve("http://example.com/b/c/d;p?q", "g#s/./x"));
        assertEquals("http://example.com/b/c/g#s/../x", resolve("http://example.com/b/c/d;p?q", "g#s/../x"));
    }

    @Test void stripsControlCharsFromUrls() {
        // Control chars (\n, \t, \r, \b) are stripped from both base and relUrl before resolution.
        assertEquals("foo:bar", resolve("\nhttps://\texample.com/", "\r\nfo\to:ba\br"));
    }

    @Test void allowsSpaceInUrl() {
        // A literal space in the path is preserved through resolution.
        assertEquals("https://example.com/foo bar/", resolve("HTTPS://example.com/example/", "../foo bar/"));
    }

    @Test
    void isAscii() {
        // ASCII: every character is in range 0-127.
        assertTrue(StringUtil.isAscii(""));
        assertTrue(StringUtil.isAscii("example.com"));
        assertTrue(StringUtil.isAscii("One Two"));

        // Non-ASCII: emoji and CJK characters fall outside the ASCII range.
        assertFalse(StringUtil.isAscii("🧔"));
        assertFalse(StringUtil.isAscii("测试"));
        assertFalse(StringUtil.isAscii("测试.com"));
    }

    @Test void isAsciiLetter() {
        // ASCII letters: a-z and A-Z (boundaries of each range checked).
        assertTrue(StringUtil.isAsciiLetter('a'));
        assertTrue(StringUtil.isAsciiLetter('n'));
        assertTrue(StringUtil.isAsciiLetter('z'));
        assertTrue(StringUtil.isAsciiLetter('A'));
        assertTrue(StringUtil.isAsciiLetter('N'));
        assertTrue(StringUtil.isAsciiLetter('Z'));

        // Not ASCII letters: space, punctuation, digits, and accented/non-ASCII letters.
        assertFalse(StringUtil.isAsciiLetter(' '));
        assertFalse(StringUtil.isAsciiLetter('-'));
        assertFalse(StringUtil.isAsciiLetter('0'));
        assertFalse(StringUtil.isAsciiLetter('ß')); // German sharp s (ß)
        assertFalse(StringUtil.isAsciiLetter('Ě')); // Latin E with caron (Ě)
    }

    @Test void isDigit() {
        // Each ASCII digit 0-9 is recognised.
        assertTrue(StringUtil.isDigit('0'));
        assertTrue(StringUtil.isDigit('1'));
        assertTrue(StringUtil.isDigit('2'));
        assertTrue(StringUtil.isDigit('3'));
        assertTrue(StringUtil.isDigit('4'));
        assertTrue(StringUtil.isDigit('5'));
        assertTrue(StringUtil.isDigit('6'));
        assertTrue(StringUtil.isDigit('7'));
        assertTrue(StringUtil.isDigit('8'));
        assertTrue(StringUtil.isDigit('9'));

        // Letters and non-ASCII digit characters are not ASCII digits.
        assertFalse(StringUtil.isDigit('a'));
        assertFalse(StringUtil.isDigit('A'));
        assertFalse(StringUtil.isDigit('ä')); // Latin a with diaeresis (ä)
        assertFalse(StringUtil.isDigit('Ä')); // Latin A with diaeresis (Ä)
        assertFalse(StringUtil.isDigit('١')); // Arabic-Indic digit one
        assertFalse(StringUtil.isDigit('୳')); // Oriya fraction
    }

    @Test void isHexDigit() {
        // ASCII digits 0-9 are hex digits.
        assertTrue(StringUtil.isHexDigit('0'));
        assertTrue(StringUtil.isHexDigit('1'));
        assertTrue(StringUtil.isHexDigit('2'));
        assertTrue(StringUtil.isHexDigit('3'));
        assertTrue(StringUtil.isHexDigit('4'));
        assertTrue(StringUtil.isHexDigit('5'));
        assertTrue(StringUtil.isHexDigit('6'));
        assertTrue(StringUtil.isHexDigit('7'));
        assertTrue(StringUtil.isHexDigit('8'));
        assertTrue(StringUtil.isHexDigit('9'));
        // Lower-case hex letters a-f.
        assertTrue(StringUtil.isHexDigit('a'));
        assertTrue(StringUtil.isHexDigit('b'));
        assertTrue(StringUtil.isHexDigit('c'));
        assertTrue(StringUtil.isHexDigit('d'));
        assertTrue(StringUtil.isHexDigit('e'));
        assertTrue(StringUtil.isHexDigit('f'));
        // Upper-case hex letters A-F.
        assertTrue(StringUtil.isHexDigit('A'));
        assertTrue(StringUtil.isHexDigit('B'));
        assertTrue(StringUtil.isHexDigit('C'));
        assertTrue(StringUtil.isHexDigit('D'));
        assertTrue(StringUtil.isHexDigit('E'));
        assertTrue(StringUtil.isHexDigit('F'));

        // Letters beyond f/F and non-ASCII digit characters are not hex digits.
        assertFalse(StringUtil.isHexDigit('g'));
        assertFalse(StringUtil.isHexDigit('G'));
        assertFalse(StringUtil.isHexDigit('ä')); // Latin a with diaeresis (ä)
        assertFalse(StringUtil.isHexDigit('Ä')); // Latin A with diaeresis (Ä)
        assertFalse(StringUtil.isHexDigit('١')); // Arabic-Indic digit one
        assertFalse(StringUtil.isHexDigit('୳')); // Oriya fraction
    }
}

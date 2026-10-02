package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {
    private static final String FIFTEEN_SPACES = "               ";
    private static final String TWENTY_SPACES = "                    ";
    private static final String TWENTY_ONE_SPACES = "                     ";
    private static final String THIRTY_SPACES = "                              ";
    private static final String FORTY_FIVE_SPACES = "                                             ";

    @Test
    public void join() {
        assertEquals("", StringUtil.join(Collections.singletonList(""), " "));
        assertEquals("one", StringUtil.join(Collections.singletonList("one"), " "));
        assertEquals("one two three", StringUtil.join(Arrays.asList("one", "two", "three"), " "));
    }

    @Test
    public void padding() {
        assertDefaultPadding("", 0);
        assertDefaultPadding(" ", 1);
        assertDefaultPadding("  ", 2);
        assertDefaultPadding(FIFTEEN_SPACES, 15);
        assertDefaultPadding(THIRTY_SPACES, 45);

        // Memoized padding covers widths 0 through 20 before maxPaddingWidth is applied.
        assertPadding("", 0, -1);
        assertPadding(TWENTY_SPACES, 20, -1);

        // Widths outside the memoized range continue through maxPaddingWidth handling.
        assertPadding(TWENTY_ONE_SPACES, 21, -1);
        assertPadding(THIRTY_SPACES, 30, -1);
        assertPadding(FORTY_FIVE_SPACES, 45, -1);

        assertPadding("", 0, 0);
        assertPadding("", 21, 0);

        assertPadding("", 0, 30);
        assertPadding(" ", 1, 30);
        assertPadding("  ", 2, 30);
        assertPadding(FIFTEEN_SPACES, 15, 30);
        assertPadding(THIRTY_SPACES, 45, 30);

        assertEquals(5, StringUtil.padding(20, 5).length());
    }

    @Test
    public void paddingInACan() {
        String[] padding = StringUtil.padding;
        assertEquals(21, padding.length);
        for (int i = 0; i < padding.length; i++) {
            assertEquals(i, padding[i].length());
        }
    }

    @Test
    public void isBlank() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank("      "));
        assertTrue(StringUtil.isBlank("   \r\n  "));

        assertFalse(StringUtil.isBlank("hello"));
        assertFalse(StringUtil.isBlank("   hello   "));
    }

    @Test
    public void isNumeric() {
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric(" "));
        assertFalse(StringUtil.isNumeric("123 546"));
        assertFalse(StringUtil.isNumeric("hello"));
        assertFalse(StringUtil.isNumeric("123.334"));

        assertTrue(StringUtil.isNumeric("1"));
        assertTrue(StringUtil.isNumeric("1234"));
    }

    @Test
    public void isWhitespace() {
        assertWhitespaceCharactersAreTrue('\t', '\n', '\r', '\f', ' ');
        assertWhitespaceCharactersAreFalse('\u00a0', '\u2000', '\u3000');
    }

    @Test
    public void normaliseWhiteSpace() {
        assertEquals(" ", normaliseWhitespace("    \r \n \r\n"));
        assertEquals(" hello there ", normaliseWhitespace("   hello   \r \n  there    \n"));
        assertEquals("hello", normaliseWhitespace("hello"));
        assertEquals("hello there", normaliseWhitespace("hello\nthere"));
    }

    @Test
    public void normaliseWhiteSpaceHandlesHighSurrogates() {
        String test71540chars = "\ud869\udeb2\u304b\u309a  1";
        String test71540charsExpectedSingleWhitespace = "\ud869\udeb2\u304b\u309a 1";

        assertEquals(test71540charsExpectedSingleWhitespace, normaliseWhitespace(test71540chars));
        String extractedText = Jsoup.parse(test71540chars).text();
        assertEquals(test71540charsExpectedSingleWhitespace, extractedText);
    }

    @Test
    public void resolvesRelativeUrls() {
        assertResolvedUrl("http://example.com/one/two?three", "http://example.com", "./one/two?three");
        assertResolvedUrl("http://example.com/one/two?three", "http://example.com?one", "./one/two?three");
        assertResolvedUrl("http://example.com/one/two?three#four", "http://example.com", "./one/two?three#four");
        assertResolvedUrl("https://example.com/one", "http://example.com/", "https://example.com/one");
        assertResolvedUrl("http://example.com/one/two.html", "http://example.com/two/", "../one/two.html");
        assertResolvedUrl("https://example2.com/one", "https://example.com/", "//example2.com/one");
        assertResolvedUrl("https://example.com:8080/one", "https://example.com:8080", "./one");
        assertResolvedUrl("https://example2.com/one", "http://example.com/", "https://example2.com/one");
        assertResolvedUrl("https://example.com/one", "wrong", "https://example.com/one");
        assertResolvedUrl("https://example.com/one", "https://example.com/one", "");
        assertResolvedUrl("", "wrong", "also wrong");
        assertResolvedUrl("ftp://example.com/one", "ftp://example.com/two/", "../one");
        assertResolvedUrl("ftp://example.com/one/two.c", "ftp://example.com/one/", "./two.c");
        assertResolvedUrl("ftp://example.com/one/two.c", "ftp://example.com/one/", "two.c");

        // Examples taken from RFC 3986 section 5.4.2.
        assertResolvedUrl("http://example.com/g", "http://example.com/b/c/d;p?q", "../../../g");
        assertResolvedUrl("http://example.com/g", "http://example.com/b/c/d;p?q", "../../../../g");
        assertResolvedUrl("http://example.com/g", "http://example.com/b/c/d;p?q", "/./g");
        assertResolvedUrl("http://example.com/g", "http://example.com/b/c/d;p?q", "/../g");
        assertResolvedUrl("http://example.com/b/c/g.", "http://example.com/b/c/d;p?q", "g.");
        assertResolvedUrl("http://example.com/b/c/.g", "http://example.com/b/c/d;p?q", ".g");
        assertResolvedUrl("http://example.com/b/c/g..", "http://example.com/b/c/d;p?q", "g..");
        assertResolvedUrl("http://example.com/b/c/..g", "http://example.com/b/c/d;p?q", "..g");
        assertResolvedUrl("http://example.com/b/g", "http://example.com/b/c/d;p?q", "./../g");
        assertResolvedUrl("http://example.com/b/c/g/", "http://example.com/b/c/d;p?q", "./g/.");
        assertResolvedUrl("http://example.com/b/c/g/h", "http://example.com/b/c/d;p?q", "g/./h");
        assertResolvedUrl("http://example.com/b/c/h", "http://example.com/b/c/d;p?q", "g/../h");
        assertResolvedUrl("http://example.com/b/c/g;x=1/y", "http://example.com/b/c/d;p?q", "g;x=1/./y");
        assertResolvedUrl("http://example.com/b/c/y", "http://example.com/b/c/d;p?q", "g;x=1/../y");
        assertResolvedUrl("http://example.com/b/c/g?y/./x", "http://example.com/b/c/d;p?q", "g?y/./x");
        assertResolvedUrl("http://example.com/b/c/g?y/../x", "http://example.com/b/c/d;p?q", "g?y/../x");
        assertResolvedUrl("http://example.com/b/c/g#s/./x", "http://example.com/b/c/d;p?q", "g#s/./x");
        assertResolvedUrl("http://example.com/b/c/g#s/../x", "http://example.com/b/c/d;p?q", "g#s/../x");
    }

    @Test
    void stripsControlCharsFromUrls() {
        assertResolvedUrl("foo:bar", "\nhttps://\texample.com/", "\r\nfo\to:ba\br");
    }

    @Test
    void allowsSpaceInUrl() {
        assertResolvedUrl("https://example.com/foo bar/", "HTTPS://example.com/example/", "../foo bar/");
    }

    @Test
    void isAscii() {
        assertTrue(StringUtil.isAscii(""));
        assertTrue(StringUtil.isAscii("example.com"));
        assertTrue(StringUtil.isAscii("One Two"));
        assertFalse(StringUtil.isAscii("🧔"));
        assertFalse(StringUtil.isAscii("测试"));
        assertFalse(StringUtil.isAscii("测试.com"));
    }

    @Test
    void isAsciiLetter() {
        assertAsciiLettersAreTrue('a', 'n', 'z', 'A', 'N', 'Z');
        assertAsciiLettersAreFalse(' ', '-', '0', 'ß', 'Ě');
    }

    @Test
    void isDigit() {
        assertDigitsAreTrue('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        assertDigitsAreFalse('a', 'A', 'ä', 'Ä', '١', '୳');
    }

    @Test
    void isHexDigit() {
        assertHexDigitsAreTrue(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f',
            'A', 'B', 'C', 'D', 'E', 'F'
        );

        assertHexDigitsAreFalse('g', 'G', 'ä', 'Ä', '١', '୳');
    }

    private static void assertDefaultPadding(String expected, int width) {
        assertEquals(expected, StringUtil.padding(width));
    }

    private static void assertPadding(String expected, int width, int maxPaddingWidth) {
        assertEquals(expected, StringUtil.padding(width, maxPaddingWidth));
    }

    private static void assertResolvedUrl(String expected, String baseUrl, String relativeUrl) {
        assertEquals(expected, resolve(baseUrl, relativeUrl));
    }

    private static void assertWhitespaceCharactersAreTrue(char... characters) {
        for (char character : characters) {
            assertTrue(StringUtil.isWhitespace(character));
        }
    }

    private static void assertWhitespaceCharactersAreFalse(char... characters) {
        for (char character : characters) {
            assertFalse(StringUtil.isWhitespace(character));
        }
    }

    private static void assertAsciiLettersAreTrue(char... characters) {
        for (char character : characters) {
            assertTrue(StringUtil.isAsciiLetter(character));
        }
    }

    private static void assertAsciiLettersAreFalse(char... characters) {
        for (char character : characters) {
            assertFalse(StringUtil.isAsciiLetter(character));
        }
    }

    private static void assertDigitsAreTrue(char... characters) {
        for (char character : characters) {
            assertTrue(StringUtil.isDigit(character));
        }
    }

    private static void assertDigitsAreFalse(char... characters) {
        for (char character : characters) {
            assertFalse(StringUtil.isDigit(character));
        }
    }

    private static void assertHexDigitsAreTrue(char... characters) {
        for (char character : characters) {
            assertTrue(StringUtil.isHexDigit(character));
        }
    }

    private static void assertHexDigitsAreFalse(char... characters) {
        for (char character : characters) {
            assertFalse(StringUtil.isHexDigit(character));
        }
    }
}

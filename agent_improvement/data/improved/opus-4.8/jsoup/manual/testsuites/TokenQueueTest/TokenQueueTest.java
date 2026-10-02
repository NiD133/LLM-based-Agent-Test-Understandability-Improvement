package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TokenQueue}, the character reader jsoup uses to parse CSS selectors.
 */
public class TokenQueueTest {

    // ------------------------------------------------------------------
    // chompBalanced: pull a balanced (open .. close) section off the queue
    // ------------------------------------------------------------------

    @Test public void chompBalanced() {
        TokenQueue queue = new TokenQueue(":contains(one (two) three) four");

        String beforeOpener = queue.consumeTo("(");
        String balancedContent = queue.chompBalanced('(', ')');
        String remainder = queue.remainder();

        assertEquals(":contains", beforeOpener);
        assertEquals("one (two) three", balancedContent); // inner parens are kept, outermost pair stripped
        assertEquals(" four", remainder);
    }

    @Test public void chompEscapedBalanced() {
        TokenQueue queue = new TokenQueue(":contains(one (two) \\( \\) \\) three) four");

        String beforeOpener = queue.consumeTo("(");
        String balancedContent = queue.chompBalanced('(', ')');
        String remainder = queue.remainder();

        assertEquals(":contains", beforeOpener);
        // Escaped parens are preserved verbatim (suitable for regexes) ...
        assertEquals("one (two) \\( \\) \\) three", balancedContent);
        // ... and can be turned back into plain text via unescape.
        assertEquals("one (two) ( ) ) three", TokenQueue.unescape(balancedContent));
        assertEquals(" four", remainder);
    }

    @Test public void chompBalancedMatchesAsMuchAsPossible() {
        TokenQueue queue = new TokenQueue("unbalanced(something(or another)) else");
        queue.consumeTo("(");

        String balancedContent = queue.chompBalanced('(', ')');

        assertEquals("something(or another)", balancedContent);
    }

    @Test
    public void chompBalancedThrowIllegalArgumentException() {
        TokenQueue queue = new TokenQueue("unbalanced(something(or another)) else");
        queue.consumeTo("(");

        // The closer '+' never appears, so no balanced section can be found.
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> queue.chompBalanced('(', '+'));
        assertEquals("Did not find balanced marker at 'something(or another)) else'", thrown.getMessage());
    }

    // ------------------------------------------------------------------
    // unescape: strip backslash escapes from a string
    // ------------------------------------------------------------------

    @Test public void unescape() {
        assertEquals("one ( ) \\", TokenQueue.unescape("one \\( \\) \\\\"));
    }

    @Test public void unescape_2() {
        assertEquals("\\&", TokenQueue.unescape("\\\\\\&"));
    }

    // ------------------------------------------------------------------
    // escapeCssIdentifier: serialize a string as a valid CSS identifier
    // ------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("escapeCssIdentifier_WebPlatformTestParameters")
    @MethodSource("escapeCssIdentifier_additionalParameters")
    public void escapeCssIdentifier(String expected, String input) {
        assertEquals(expected, TokenQueue.escapeCssIdentifier(input));
    }

    // https://github.com/web-platform-tests/wpt/blob/328fa1c67bf5dfa6f24571d4c41dd10224b6d247/css/cssom/escape.html
    private static Stream<Arguments> escapeCssIdentifier_WebPlatformTestParameters() {
        return Stream.of(
            Arguments.of("", ""),

            // Null bytes
            Arguments.of("�", "\0"),
            Arguments.of("a�", "a\0"),
            Arguments.of("�b", "\0b"),
            Arguments.of("a�b", "a\0b"),

            // Replacement character
            Arguments.of("�", "�"),
            Arguments.of("a�", "a�"),
            Arguments.of("�b", "�b"),
            Arguments.of("a�b", "a�b"),

            // Number prefix
            Arguments.of("\\30 a", "0a"),
            Arguments.of("\\31 a", "1a"),
            Arguments.of("\\32 a", "2a"),
            Arguments.of("\\33 a", "3a"),
            Arguments.of("\\34 a", "4a"),
            Arguments.of("\\35 a", "5a"),
            Arguments.of("\\36 a", "6a"),
            Arguments.of("\\37 a", "7a"),
            Arguments.of("\\38 a", "8a"),
            Arguments.of("\\39 a", "9a"),

            // Letter number prefix
            Arguments.of("a0b", "a0b"),
            Arguments.of("a1b", "a1b"),
            Arguments.of("a2b", "a2b"),
            Arguments.of("a3b", "a3b"),
            Arguments.of("a4b", "a4b"),
            Arguments.of("a5b", "a5b"),
            Arguments.of("a6b", "a6b"),
            Arguments.of("a7b", "a7b"),
            Arguments.of("a8b", "a8b"),
            Arguments.of("a9b", "a9b"),

            // Dash number prefix
            Arguments.of("-\\30 a", "-0a"),
            Arguments.of("-\\31 a", "-1a"),
            Arguments.of("-\\32 a", "-2a"),
            Arguments.of("-\\33 a", "-3a"),
            Arguments.of("-\\34 a", "-4a"),
            Arguments.of("-\\35 a", "-5a"),
            Arguments.of("-\\36 a", "-6a"),
            Arguments.of("-\\37 a", "-7a"),
            Arguments.of("-\\38 a", "-8a"),
            Arguments.of("-\\39 a", "-9a"),

            // Double dash prefix
            Arguments.of("--a", "--a"),

            // Various tests
            Arguments.of("\\1 \\2 \\1e \\1f ", ""),
            Arguments.of("-_©", "-_©"),
            Arguments.of("\\7f ", ""),
            Arguments.of(" ¡¢", " ¡¢"),
            Arguments.of("a0123456789b", "a0123456789b"),
            Arguments.of("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz"),
            Arguments.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

            Arguments.of("hello\\\\world", "hello\\world"), // Backslashes get backslash-escaped
            Arguments.of("helloሴworld", "helloሴworld"), // Code points greater than U+0080 are preserved
            Arguments.of("\\-", "-"), // CSS.escape: Single dash escaped

            Arguments.of("\\ \\!xy", " !xy"),

            // astral symbol (U+1D306 TETRAGRAM FOR CENTRE)
            Arguments.of("𝌆", "𝌆"),

            // lone surrogates
            Arguments.of("\uDF06", "\uDF06"),
            Arguments.of("\uD834", "\uD834")
        );
    }

    private static Stream<Arguments> escapeCssIdentifier_additionalParameters() {
        return Stream.of(
            Arguments.of("one\\#two\\.three\\/four\\\\five", "one#two.three/four\\five"),
            Arguments.of("-a", "-a"),
            Arguments.of("--", "--")
        );
    }

    // ------------------------------------------------------------------
    // Selector parsing with nested / quoted patterns (integration via Jsoup)
    // ------------------------------------------------------------------

    @Test public void testNestedQuotes() {
        // Each selector contains nested quotes inside the [attr*=...] value; all should resolve to #identifier.
        assertSelectorResolvesToIdentifier("<html><body><a id=\"identifier\" onclick=\"func('arg')\" /></body></html>", "a[onclick*=\"('arg\"]");
        assertSelectorResolvesToIdentifier("<html><body><a id=\"identifier\" onclick=func('arg') /></body></html>", "a[onclick*=\"('arg\"]");
        assertSelectorResolvesToIdentifier("<html><body><a id=\"identifier\" onclick='func(\"arg\")' /></body></html>", "a[onclick*='(\"arg']");
        assertSelectorResolvesToIdentifier("<html><body><a id=\"identifier\" onclick=func(\"arg\") /></body></html>", "a[onclick*='(\"arg']");
    }

    private static void assertSelectorResolvesToIdentifier(String html, String selector) {
        String actualCssSelector = Jsoup.parse(html).select(selector).first().cssSelector();
        assertEquals("#identifier", actualCssSelector);
    }

    @Test
    public void testQuotedPattern() {
        Document doc = Jsoup.parse("<div>\\) foo1</div><div>( foo2</div><div>1) foo3</div>");

        assertEquals("\\) foo1", doc.select("div:matches(" + Pattern.quote("\\)") + ")").get(0).childNode(0).toString());
        assertEquals("( foo2", doc.select("div:matches(" + Pattern.quote("(") + ")").get(0).childNode(0).toString());
        assertEquals("1) foo3", doc.select("div:matches(" + Pattern.quote("1)") + ")").get(0).childNode(0).toString());
    }

    // ------------------------------------------------------------------
    // consumeElementSelector: read an (escaped) tag-name style selector
    // ------------------------------------------------------------------

    @Test public void consumeEscapedTag() {
        TokenQueue queue = new TokenQueue("p\\\\p p\\.p p\\:p p\\!p");

        assertEquals("p\\p", queue.consumeElementSelector());
        assertTrue(queue.consumeWhitespace());

        assertEquals("p.p", queue.consumeElementSelector());
        assertTrue(queue.consumeWhitespace());

        assertEquals("p:p", queue.consumeElementSelector());
        assertTrue(queue.consumeWhitespace());

        assertEquals("p!p", queue.consumeElementSelector());
        assertTrue(queue.isEmpty());
    }

    @Test void escapeAtEof() {
        TokenQueue queue = new TokenQueue("Foo\\");

        String selector = queue.consumeElementSelector();

        // A trailing backslash with no following char is dropped: just "Foo".
        assertEquals("Foo", selector);
    }

    // ------------------------------------------------------------------
    // consumeCssIdentifier: read an (escaped) CSS ID / class
    // ------------------------------------------------------------------

    @Test public void consumeEscapedId() {
        TokenQueue queue = new TokenQueue("i\\.d i\\\\d");

        assertEquals("i.d", queue.consumeCssIdentifier());
        assertTrue(queue.consumeWhitespace());

        assertEquals("i\\d", queue.consumeCssIdentifier());
        assertTrue(queue.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("cssIdentifiers")
    @MethodSource("cssAdditionalIdentifiers")
    void consumeCssIdentifier_WebPlatformTests(String expected, String cssIdentifier) {
        assertParsedCssIdentifierEquals(expected, cssIdentifier);
    }

    private static Stream<Arguments> cssIdentifiers() {
        return Stream.of(
            // https://github.com/web-platform-tests/wpt/blob/36036fb5212a3fc15fc5750cecb1923ba4071668/dom/nodes/ParentNode-querySelector-escapes.html
            // - escape hex digit
            Arguments.of("0nextIsWhiteSpace", "\\30 nextIsWhiteSpace"),
            Arguments.of("0nextIsNotHexLetters", "\\30nextIsNotHexLetters"),
            Arguments.of("0connectHexMoreThan6Hex", "\\000030connectHexMoreThan6Hex"),
            Arguments.of("0spaceMoreThan6Hex", "\\000030 spaceMoreThan6Hex"),

            // - hex digit special replacement
            // 1. zero points
            Arguments.of("zero�", "zero\\0"),
            Arguments.of("zero�", "zero\\000000"),
            // 2. surrogate points
            Arguments.of("�surrogateFirst", "\\d83d surrogateFirst"),
            Arguments.of("surrogateSecond�", "surrogateSecond\\dd11"),
            Arguments.of("surrogatePair��", "surrogatePair\\d83d\\dd11"),
            // 3. out of range points
            Arguments.of("outOfRange�", "outOfRange\\110000"),
            Arguments.of("outOfRange�", "outOfRange\\110030"),
            Arguments.of("outOfRange�", "outOfRange\\555555"),
            Arguments.of("outOfRange�", "outOfRange\\ffffff"),

            // - escape anything else
            Arguments.of(".comma", "\\.comma"),
            Arguments.of("-minus", "\\-minus"),
            Arguments.of("g", "\\g"),

            // non edge cases
            Arguments.of("aBMPRegular", "\\61 BMPRegular"),
            Arguments.of("🔑nonBMP", "\\1f511 nonBMP"),
            Arguments.of("00continueEscapes", "\\30\\30 continueEscapes"),
            Arguments.of("00continueEscapes", "\\30 \\30 continueEscapes"),
            Arguments.of("continueEscapes00", "continueEscapes\\30 \\30 "),
            Arguments.of("continueEscapes00", "continueEscapes\\30 \\30"),
            Arguments.of("continueEscapes00", "continueEscapes\\30\\30 "),
            Arguments.of("continueEscapes00", "continueEscapes\\30\\30"),

            // ident tests case from CSS tests of chromium source: https://goo.gl/3Cxdov
            Arguments.of("hello", "hel\\6Co"),
            Arguments.of("&B", "\\26 B"),
            Arguments.of("hello", "hel\\6C o"),
            Arguments.of("spaces", "spac\\65\r\ns"),
            Arguments.of("spaces", "sp\\61\tc\\65\fs"),
            Arguments.of("test힙", "test\\D799"),
            Arguments.of("", "\\E000"),
            Arguments.of("test", "te\\s\\t"),
            Arguments.of("spaces in\tident", "spaces\\ in\\\tident"),
            Arguments.of(".,:!", "\\.\\,\\:\\!"),
            Arguments.of("null�", "null\\0"),
            Arguments.of("null�", "null\\0000"),
            Arguments.of("large�", "large\\110000"),
            Arguments.of("large�", "large\\23456a"),
            Arguments.of("surrogate�", "surrogate\\D800"),
            Arguments.of("surrogate�", "surrogate\\0DBAC"),
            Arguments.of("�surrogate", "\\00DFFFsurrogate"),
            Arguments.of("􏿿", "\\10fFfF"),
            Arguments.of("􏿿0", "\\10fFfF0"),
            Arguments.of("􀀀00", "\\10000000"),
            Arguments.of("eof�", "eof\\"),

            Arguments.of("simple-ident", "simple-ident"),
            Arguments.of("testing123", "testing123"),
            Arguments.of("_underscore", "_underscore"),
            Arguments.of("-text", "-text"),
            Arguments.of("-m", "-\\6d"),
            Arguments.of("--abc", "--abc"),
            Arguments.of("--", "--"),
            Arguments.of("--11", "--11"),
            Arguments.of("---", "---"),
            Arguments.of(" ", " "),
            Arguments.of(" ", " "),
            Arguments.of("ሴ", "ሴ"),
            Arguments.of("𒍅", "𒍅"),
            Arguments.of("�", " "),
            Arguments.of("ab�c", "ab c")
        );
    }

    private static Stream<Arguments> cssAdditionalIdentifiers() {
        return Stream.of(
            Arguments.of("1st", "\\31\r\nst"),
            Arguments.of("1", "\\31\r"),
            Arguments.of("1a", "\\31\ra"),
            Arguments.of("1", "\\031"),
            Arguments.of("1", "\\0031"),
            Arguments.of("1", "\\00031"),
            Arguments.of("1", "\\000031"),
            Arguments.of("1", "\\000031"),
            Arguments.of("a", "a\\\nb")
        );
    }

    @Test void consumeCssIdentifierWithEmptyInput() {
        TokenQueue emptyQueue = new TokenQueue("");

        Exception exception = assertThrows(IllegalArgumentException.class, emptyQueue::consumeCssIdentifier);
        assertEquals("CSS identifier expected, but end of input found", exception.getMessage());
    }

    // Some of jsoup's tests depend on this behavior
    @Test public void consumeCssIdentifier_invalidButSupportedForBackwardsCompatibility() {
        assertParsedCssIdentifierEquals("1", "1");
        assertParsedCssIdentifierEquals("-", "-");
        assertParsedCssIdentifierEquals("-1", "-1");
    }

    // ------------------------------------------------------------------
    // Shared helpers
    // ------------------------------------------------------------------

    private static String parseCssIdentifier(String text) {
        TokenQueue queue = new TokenQueue(text);
        return queue.consumeCssIdentifier();
    }

    private void assertParsedCssIdentifierEquals(String expected, String cssIdentifier) {
        assertEquals(expected, parseCssIdentifier(cssIdentifier));
    }
}

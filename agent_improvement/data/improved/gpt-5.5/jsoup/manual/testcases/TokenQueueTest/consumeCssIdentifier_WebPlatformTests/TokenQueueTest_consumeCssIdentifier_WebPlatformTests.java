package org.jsoup.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_consumeCssIdentifier_WebPlatformTests {

    private static Stream<Arguments> cssIdentifiers() {
        return Stream.of(
            // Web Platform Tests:
            // https://github.com/web-platform-tests/wpt/blob/36036fb5212a3fc15fc5750cecb1923ba4071668/dom/nodes/ParentNode-querySelector-escapes.html

            // Escape hex digit.
            Arguments.of("0nextIsWhiteSpace", "\\30 nextIsWhiteSpace"),
            Arguments.of("0nextIsNotHexLetters", "\\30nextIsNotHexLetters"),
            Arguments.of("0connectHexMoreThan6Hex", "\\000030connectHexMoreThan6Hex"),
            Arguments.of("0spaceMoreThan6Hex", "\\000030 spaceMoreThan6Hex"),

            // Hex digit special replacement: zero code points.
            Arguments.of("zero\uFFFD", "zero\\0"),
            Arguments.of("zero\uFFFD", "zero\\000000"),

            // Hex digit special replacement: surrogate code points.
            Arguments.of("\uFFFDsurrogateFirst", "\\d83d surrogateFirst"),
            Arguments.of("surrogateSecond\uFFFd", "surrogateSecond\\dd11"),
            Arguments.of("surrogatePair\uFFFD\uFFFD", "surrogatePair\\d83d\\dd11"),

            // Hex digit special replacement: out-of-range code points.
            Arguments.of("outOfRange\uFFFD", "outOfRange\\110000"),
            Arguments.of("outOfRange\uFFFD", "outOfRange\\110030"),
            Arguments.of("outOfRange\uFFFD", "outOfRange\\555555"),
            Arguments.of("outOfRange\uFFFD", "outOfRange\\ffffff"),

            // Escape anything else.
            Arguments.of(".comma", "\\.comma"),
            Arguments.of("-minus", "\\-minus"),
            Arguments.of("g", "\\g"),

            // Non-edge cases.
            Arguments.of("aBMPRegular", "\\61 BMPRegular"),
            Arguments.of("\uD83D\uDD11nonBMP", "\\1f511 nonBMP"),
            Arguments.of("00continueEscapes", "\\30\\30 continueEscapes"),
            Arguments.of("00continueEscapes", "\\30 \\30 continueEscapes"),
            Arguments.of("continueEscapes00", "continueEscapes\\30 \\30 "),
            Arguments.of("continueEscapes00", "continueEscapes\\30 \\30"),
            Arguments.of("continueEscapes00", "continueEscapes\\30\\30 "),
            Arguments.of("continueEscapes00", "continueEscapes\\30\\30"),

            // Ident tests from Chromium CSS tests: https://goo.gl/3Cxdov
            Arguments.of("hello", "hel\\6Co"),
            Arguments.of("&B", "\\26 B"),
            Arguments.of("hello", "hel\\6C o"),
            Arguments.of("spaces", "spac\\65\r\ns"),
            Arguments.of("spaces", "sp\\61\tc\\65\fs"),
            Arguments.of("test\uD799", "test\\D799"),
            Arguments.of("\uE000", "\\E000"),
            Arguments.of("test", "te\\s\\t"),
            Arguments.of("spaces in\tident", "spaces\\ in\\\tident"),
            Arguments.of(".,:!", "\\.\\,\\:\\!"),
            Arguments.of("null\uFFFD", "null\\0"),
            Arguments.of("null\uFFFD", "null\\0000"),
            Arguments.of("large\uFFFD", "large\\110000"),
            Arguments.of("large\uFFFD", "large\\23456a"),
            Arguments.of("surrogate\uFFFD", "surrogate\\D800"),
            Arguments.of("surrogate\uFFFD", "surrogate\\0DBAC"),
            Arguments.of("\uFFFDsurrogate", "\\00DFFFsurrogate"),
            Arguments.of("\uDBFF\uDFFF", "\\10fFfF"),
            Arguments.of("\uDBFF\uDFFF0", "\\10fFfF0"),
            Arguments.of("\uDBC0\uDC0000", "\\10000000"),
            Arguments.of("eof\uFFFD", "eof\\"),

            // Simple identifiers.
            Arguments.of("simple-ident", "simple-ident"),
            Arguments.of("testing123", "testing123"),
            Arguments.of("_underscore", "_underscore"),
            Arguments.of("-text", "-text"),
            Arguments.of("-m", "-\\6d"),
            Arguments.of("--abc", "--abc"),
            Arguments.of("--", "--"),
            Arguments.of("--11", "--11"),
            Arguments.of("---", "---"),
            Arguments.of("\u2003", "\u2003"),
            Arguments.of("\u00A0", "\u00A0"),
            Arguments.of("\u1234", "\u1234"),
            Arguments.of("\uD808\uDF45", "\uD808\uDF45"),
            Arguments.of("\uFFFD", "\u0000"),
            Arguments.of("ab\uFFFDc", "ab\u0000c")
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

    private static String parseCssIdentifier(String text) {
        TokenQueue queue = new TokenQueue(text);
        return queue.consumeCssIdentifier();
    }

    private static void assertParsedCssIdentifierEquals(String expected, String cssIdentifier) {
        assertEquals(expected, parseCssIdentifier(cssIdentifier));
    }

    @ParameterizedTest
    @MethodSource("cssIdentifiers")
    @MethodSource("cssAdditionalIdentifiers")
    void consumeCssIdentifier_WebPlatformTests(String expected, String cssIdentifier) {
        assertParsedCssIdentifierEquals(expected, cssIdentifier);
    }
}

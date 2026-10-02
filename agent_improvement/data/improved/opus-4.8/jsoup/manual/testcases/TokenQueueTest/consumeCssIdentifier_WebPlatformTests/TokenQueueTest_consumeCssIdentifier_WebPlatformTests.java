package org.jsoup.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link TokenQueue#consumeCssIdentifier()} against the CSS-syntax escape/unescape cases from the
 * web-platform-tests suite and the Chromium CSS parser tests.
 *
 * <p>Each case is an {@code (expected, input)} pair: {@code input} is a (possibly escaped) CSS identifier fed to the
 * queue, and {@code expected} is the unescaped identifier that {@code consumeCssIdentifier()} should return.</p>
 *
 * <p>Non-ASCII and control characters in the data are built from their Unicode code points via {@link #unit(int)} and
 * {@link #codePoint(int)} so the test data stays exact and easy to read (no invisible glyphs in the source).</p>
 */
public class TokenQueueTest_consumeCssIdentifier_WebPlatformTests {

    /** A String holding the single UTF-16 code unit {@code u} (for BMP / control characters). */
    private static String unit(int u) {
        return String.valueOf((char) u);
    }

    /** A String holding the Unicode code point {@code cp} (encodes astral characters as a surrogate pair). */
    private static String codePoint(int cp) {
        return new String(Character.toChars(cp));
    }

    /** U+FFFD REPLACEMENT CHARACTER: what an escape decodes to when the code point is null, surrogate, or out of range. */
    private static final String REPLACEMENT = unit(0xFFFD);

    /** A NUL (U+0000) input character, which CSS input preprocessing maps to the replacement character. */
    private static final String NUL = unit(0x0000);

    /**
     * Cases from the web-platform-tests and Chromium CSS parser test suites.
     * See https://github.com/web-platform-tests/wpt/blob/36036fb5212a3fc15fc5750cecb1923ba4071668/dom/nodes/ParentNode-querySelector-escapes.html
     * and https://goo.gl/3Cxdov
     */
    private static Stream<Arguments> cssIdentifiers() {
        return Stream.of(
            // Escaped hex digit: the escape ends at a space, at a non-hex char, or after 6 hex digits.
            Arguments.of("0nextIsWhiteSpace",       "\\30 nextIsWhiteSpace"),
            Arguments.of("0nextIsNotHexLetters",    "\\30nextIsNotHexLetters"),
            Arguments.of("0connectHexMoreThan6Hex", "\\000030connectHexMoreThan6Hex"),
            Arguments.of("0spaceMoreThan6Hex",      "\\000030 spaceMoreThan6Hex"),

            // Code point zero decodes to the replacement character.
            Arguments.of("zero" + REPLACEMENT, "zero\\0"),
            Arguments.of("zero" + REPLACEMENT, "zero\\000000"),

            // Lone surrogate code points decode to the replacement character.
            Arguments.of(REPLACEMENT + "surrogateFirst",          "\\d83d surrogateFirst"),
            Arguments.of("surrogateSecond" + REPLACEMENT,         "surrogateSecond\\dd11"),
            Arguments.of("surrogatePair" + REPLACEMENT + REPLACEMENT, "surrogatePair\\d83d\\dd11"),

            // Out-of-range code points decode to the replacement character.
            Arguments.of("outOfRange" + REPLACEMENT, "outOfRange\\110000"),
            Arguments.of("outOfRange" + REPLACEMENT, "outOfRange\\110030"),
            Arguments.of("outOfRange" + REPLACEMENT, "outOfRange\\555555"),
            Arguments.of("outOfRange" + REPLACEMENT, "outOfRange\\ffffff"),

            // Escaping any other (non-hex) character yields that character literally.
            Arguments.of(".comma", "\\.comma"),
            Arguments.of("-minus", "\\-minus"),
            Arguments.of("g",      "\\g"),

            // Regular hex escapes and chains of consecutive escapes.
            Arguments.of("aBMPRegular",               "\\61 BMPRegular"),
            Arguments.of(codePoint(0x1F511) + "nonBMP", "\\1f511 nonBMP"),
            Arguments.of("00continueEscapes",         "\\30\\30 continueEscapes"),
            Arguments.of("00continueEscapes",         "\\30 \\30 continueEscapes"),
            Arguments.of("continueEscapes00",         "continueEscapes\\30 \\30 "),
            Arguments.of("continueEscapes00",         "continueEscapes\\30 \\30"),
            Arguments.of("continueEscapes00",         "continueEscapes\\30\\30 "),
            Arguments.of("continueEscapes00",         "continueEscapes\\30\\30"),

            // Identifier cases from the Chromium CSS tests.
            Arguments.of("hello",                "hel\\6Co"),
            Arguments.of("&B",                   "\\26 B"),
            Arguments.of("hello",                "hel\\6C o"),
            Arguments.of("spaces",               "spac\\65\r\ns"),
            Arguments.of("spaces",               "sp\\61\tc\\65\fs"),
            Arguments.of("test" + unit(0xD799),  "test\\D799"),
            Arguments.of(unit(0xE000),           "\\E000"),
            Arguments.of("test",                 "te\\s\\t"),
            Arguments.of("spaces in\tident",     "spaces\\ in\\\tident"),
            Arguments.of(".,:!",                 "\\.\\,\\:\\!"),
            Arguments.of("null" + REPLACEMENT,      "null\\0"),
            Arguments.of("null" + REPLACEMENT,      "null\\0000"),
            Arguments.of("large" + REPLACEMENT,     "large\\110000"),
            Arguments.of("large" + REPLACEMENT,     "large\\23456a"),
            Arguments.of("surrogate" + REPLACEMENT, "surrogate\\D800"),
            Arguments.of("surrogate" + REPLACEMENT, "surrogate\\0DBAC"),
            Arguments.of(REPLACEMENT + "surrogate", "\\00DFFFsurrogate"),
            Arguments.of(codePoint(0x10FFFF),       "\\10fFfF"),
            Arguments.of(codePoint(0x10FFFF) + "0", "\\10fFfF0"),
            Arguments.of(codePoint(0x100000) + "00", "\\10000000"),
            Arguments.of("eof" + REPLACEMENT,       "eof\\"),
            Arguments.of("simple-ident",         "simple-ident"),
            Arguments.of("testing123",           "testing123"),
            Arguments.of("_underscore",          "_underscore"),
            Arguments.of("-text",                "-text"),
            Arguments.of("-m",                   "-\\6d"),
            Arguments.of("--abc",                "--abc"),
            Arguments.of("--",                   "--"),
            Arguments.of("--11",                 "--11"),
            Arguments.of("---",                  "---"),
            Arguments.of(unit(0x2003),           unit(0x2003)),
            Arguments.of(unit(0x00A0),           unit(0x00A0)),
            Arguments.of(unit(0x1234),           unit(0x1234)),
            Arguments.of(codePoint(0x12345),     codePoint(0x12345)),
            Arguments.of(REPLACEMENT,            NUL),
            Arguments.of("ab" + REPLACEMENT + "c", "ab" + NUL + "c")
        );
    }

    /** Additional cases covering CR / CRLF / escaped-newline handling around an escape sequence. */
    private static Stream<Arguments> cssAdditionalIdentifiers() {
        return Stream.of(
            Arguments.of("1st", "\\31\r\nst"),
            Arguments.of("1",   "\\31\r"),
            Arguments.of("1a",  "\\31\ra"),
            Arguments.of("1",   "\\031"),
            Arguments.of("1",   "\\0031"),
            Arguments.of("1",   "\\00031"),
            Arguments.of("1",   "\\000031"),
            Arguments.of("1",   "\\000031"),
            Arguments.of("a",   "a\\\nb")
        );
    }

    private static String parseCssIdentifier(String input) {
        TokenQueue queue = new TokenQueue(input);
        return queue.consumeCssIdentifier();
    }

    @ParameterizedTest
    @MethodSource("cssIdentifiers")
    @MethodSource("cssAdditionalIdentifiers")
    void consumeCssIdentifier_WebPlatformTests(String expected, String input) {
        assertEquals(expected, parseCssIdentifier(input));
    }
}

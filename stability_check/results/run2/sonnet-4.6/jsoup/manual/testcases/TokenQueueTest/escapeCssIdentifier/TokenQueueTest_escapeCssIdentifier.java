package org.jsoup.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link TokenQueue#escapeCssIdentifier(String)}.
 *
 * <p>The expected values follow the CSS Object Model serialization spec:
 * https://www.w3.org/TR/cssom-1/#serialize-an-identifier
 *
 * <p>Web-platform test cases sourced from:
 * https://github.com/web-platform-tests/wpt/blob/328fa1c67bf5dfa6f24571d4c41dd10224b6d247/css/cssom/escape.html
 */
public class TokenQueueTest_escapeCssIdentifier {

    // -----------------------------------------------------------------------
    // Parameter sources -- each entry is (expectedEscapedOutput, rawInput)
    // -----------------------------------------------------------------------

    /**
     * Web Platform Tests (WPT) -- CSS.escape() specification cases.
     */
    private static Stream<Arguments> escapeCssIdentifier_WebPlatformTestParameters() {
        return Stream.of(

            // Empty input produces empty output
            Arguments.of("", ""),

            // Null bytes (U+0000) must be replaced with the REPLACEMENT CHARACTER (U+FFFD)
            Arguments.of("�",    "\0"),
            Arguments.of("a�",  "a\0"),
            Arguments.of("�b",  "\0b"),
            Arguments.of("a�b", "a\0b"),

            // The replacement character (U+FFFD) itself is kept as-is
            Arguments.of("�",    "�"),
            Arguments.of("a�",  "a�"),
            Arguments.of("�b",  "�b"),
            Arguments.of("a�b", "a�b"),

            // A digit as the first character must be escaped as a CSS code-point escape
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

            // A digit in the middle of an identifier requires no escaping
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

            // When a dash is first and a digit is second, the digit is escaped as a code point
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

            // A double-dash prefix (e.g. CSS custom properties) is valid and preserved
            Arguments.of("--a", "--a"),

            // Control characters U+0001-U+001F are escaped as CSS code points
            Arguments.of("\\1 \\2 \\1e \\1f ", ""),

            // Non-ASCII characters at U+0080 and above are preserved as-is
            Arguments.of("-_©", "-_©"),

            // U+007F is escaped as a CSS code point; U+0080-U+009F are preserved as-is
            Arguments.of(
                "\\7f ",
                ""
            ),

            // Characters at U+00A0 and above are preserved
            Arguments.of(" ¡¢", " ¡¢"),

            // Pure alphanumeric sequences require no escaping
            Arguments.of("a0123456789b",              "a0123456789b"),
            Arguments.of("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz"),
            Arguments.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

            // A backslash is escaped as "\\" (doubled)
            Arguments.of("hello\\\\world", "hello\\world"),

            // Non-ASCII code points above U+0080 are preserved without escaping
            Arguments.of("helloሴworld", "helloሴworld"),

            // A single dash (the only character) is escaped
            Arguments.of("\\-", "-"),

            // ASCII space (U+0020) and exclamation mark (U+0021) are escaped
            Arguments.of("\\ \\!xy", " !xy"),

            // Astral symbol U+1D306 (TETRAGRAM FOR CENTRE), stored as surrogate pair -- preserved
            Arguments.of("𝌆", "𝌆"),

            // Lone surrogates are preserved as-is
            Arguments.of("\uDF06", "\uDF06"),
            Arguments.of("\uD834", "\uD834")
        );
    }

    /**
     * Additional escape cases that supplement the WPT suite.
     */
    private static Stream<Arguments> escapeCssIdentifier_additionalParameters() {
        return Stream.of(
            // Special characters inside an identifier are individually escaped
            Arguments.of("one\\#two\\.three\\/four\\\\five", "one#two.three/four\\five"),

            // A leading dash followed by a letter is a valid identifier start; no escaping needed
            Arguments.of("-a", "-a"),

            // A double dash with no following character is preserved
            Arguments.of("--", "--")
        );
    }

    // -----------------------------------------------------------------------
    // Test method
    // -----------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("escapeCssIdentifier_WebPlatformTestParameters")
    @MethodSource("escapeCssIdentifier_additionalParameters")
    public void escapeCssIdentifier(String expected, String input) {
        assertEquals(expected, TokenQueue.escapeCssIdentifier(input));
    }
}

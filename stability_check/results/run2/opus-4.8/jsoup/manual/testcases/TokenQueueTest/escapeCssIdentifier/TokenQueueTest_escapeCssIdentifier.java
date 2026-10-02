package org.jsoup.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link TokenQueue#escapeCssIdentifier(String)}, which serializes a raw string into a CSS identifier,
 * escaping any characters that would otherwise be invalid in a selector.
 *
 * <p>Each test case is a pair {@code (expectedEscaped, rawInput)}: {@code rawInput} is the unescaped identifier
 * handed to the method, and {@code expectedEscaped} is the CSS-safe form it should produce. Character values are
 * written with {@code \\uXXXX} escapes so the exact code points stay unambiguous.</p>
 */
public class TokenQueueTest_escapeCssIdentifier {

    /**
     * Cases mirrored from the Web Platform Tests for {@code CSS.escape}.
     *
     * @see <a href="https://github.com/web-platform-tests/wpt/blob/328fa1c67bf5dfa6f24571d4c41dd10224b6d247/css/cssom/escape.html">wpt escape.html</a>
     */
    private static Stream<Arguments> webPlatformCases() {
        return Stream.of(
            // Empty input is returned unchanged.
            Arguments.of("", ""),

            // Null bytes become the replacement character (U+FFFD).
            Arguments.of("�", "\0"),
            Arguments.of("a�", "a\0"),
            Arguments.of("�b", "\0b"),
            Arguments.of("a�b", "a\0b"),

            // A literal replacement character is preserved.
            Arguments.of("�", "�"),
            Arguments.of("a�", "a�"),
            Arguments.of("�b", "�b"),
            Arguments.of("a�b", "a�b"),

            // A leading digit is escaped as a hex code point.
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

            // Digits that are not the first character need no escaping.
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

            // A dash followed by a digit: the digit is escaped as a hex code point.
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

            // A double-dash prefix is left as-is.
            Arguments.of("--a", "--a"),

            // Control characters (U+0001..U+001F and U+007F) are escaped as hex code points;
            // code points at or above U+0080 pass through unchanged.
            Arguments.of("\\1 \\2 \\1e \\1f ", ""),
            Arguments.of("-_©", "-_©"),
            Arguments.of(
                "\\7f ",
                ""),
            Arguments.of(" ¡¢", " ¡¢"),
            Arguments.of("a0123456789b", "a0123456789b"),
            Arguments.of("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz"),
            Arguments.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

            // Backslashes are backslash-escaped.
            Arguments.of("hello\\\\world", "hello\\world"),

            // Code points at or above U+0080 are preserved.
            Arguments.of("helloሴworld", "helloሴworld"),

            // A single dash is backslash-escaped; space and '!' are backslash-escaped too.
            Arguments.of("\\-", "-"),
            Arguments.of("\\ \\!xy", " !xy"),

            // Astral symbol (U+1D306 TETRAGRAM FOR CENTRE) is preserved.
            Arguments.of("𝌆", "𝌆"),

            // Lone surrogates are preserved.
            Arguments.of("\uDF06", "\uDF06"),
            Arguments.of("\uD834", "\uD834"));
    }

    /** Extra jsoup-specific cases beyond the Web Platform Tests. */
    private static Stream<Arguments> additionalCases() {
        return Stream.of(
            Arguments.of("one\\#two\\.three\\/four\\\\five", "one#two.three/four\\five"),
            Arguments.of("-a", "-a"),
            Arguments.of("--", "--"));
    }

    @ParameterizedTest(name = "escape [{1}] -> [{0}]")
    @MethodSource("webPlatformCases")
    @MethodSource("additionalCases")
    public void escapesRawStringIntoCssIdentifier(String expectedEscaped, String rawInput) {
        assertEquals(expectedEscaped, TokenQueue.escapeCssIdentifier(rawInput));
    }
}

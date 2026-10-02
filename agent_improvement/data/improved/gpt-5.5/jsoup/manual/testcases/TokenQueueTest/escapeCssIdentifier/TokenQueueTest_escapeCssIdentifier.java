package org.jsoup.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenQueueTest_escapeCssIdentifier {
    // https://github.com/web-platform-tests/wpt/blob/328fa1c67bf5dfa6f24571d4c41dd10224b6d247/css/cssom/escape.html
    private static Stream<Arguments> escapeCssIdentifier_WebPlatformTestParameters() {
        return Stream.of(
            // Null bytes
            Arguments.of("", ""),
            Arguments.of("\uFFFD", "\0"),
            Arguments.of("a\uFFFD", "a\0"),
            Arguments.of("\uFFFDb", "\0b"),
            Arguments.of("a\uFFFDb", "a\0b"),

            // Replacement character
            Arguments.of("\uFFFD", "\uFFFD"),
            Arguments.of("a\uFFFD", "a\uFFFD"),
            Arguments.of("\uFFFDb", "\uFFFDb"),
            Arguments.of("a\uFFFDb", "a\uFFFDb"),

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
            Arguments.of("\\1 \\2 \\1e \\1f ", "\u0001\u0002\u001E\u001F"),
            Arguments.of("\u0080\u002D\u005F\u00A9", "\u0080\u002D\u005F\u00A9"),
            Arguments.of(
                "\\7f \u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F"
                    + "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F",
                "\u007F\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F"
                    + "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F"
            ),
            Arguments.of("\u00A0\u00A1\u00A2", "\u00A0\u00A1\u00A2"),
            Arguments.of("a0123456789b", "a0123456789b"),
            Arguments.of("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz"),
            Arguments.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

            // Backslashes get backslash-escaped.
            Arguments.of("hello\\\\world", "hello\\world"),

            // Code points greater than U+0080 are preserved.
            Arguments.of("hello\u1234world", "hello\u1234world"),

            // CSS.escape: single dash escaped.
            Arguments.of("\\-", "-"),
            Arguments.of("\\ \\!xy", "\u0020\u0021\u0078\u0079"),

            // Astral symbol (U+1D306 TETRAGRAM FOR CENTRE)
            Arguments.of("\uD834\uDF06", "\uD834\uDF06"),

            // Lone surrogates
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

    @ParameterizedTest
    @MethodSource("escapeCssIdentifier_WebPlatformTestParameters")
    @MethodSource("escapeCssIdentifier_additionalParameters")
    public void escapeCssIdentifier(String expected, String input) {
        assertEquals(expected, TokenQueue.escapeCssIdentifier(input));
    }
}

package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link TextStyle#pad(boolean, CharSequence)}.
 *
 * <p>All cases use a base style with: indent=5, leftPad=5, minWidth=4, scalable=true.
 * Scenarios vary maxWidth and alignment to verify four behaviours:
 * <ol>
 *   <li>UNSET_MAX_WIDTH — no upper bound; indent is prepended as leading spaces</li>
 *   <li>maxWidth &lt; text length — text returned unchanged regardless of alignment or indent</li>
 *   <li>maxWidth &gt; text length + indent — indent fits within maxWidth</li>
 *   <li>maxWidth between text length and text length + indent — padding fills remaining width only</li>
 * </ol>
 */
public class TextStyleTest_testPad {

    /** Returns a pre-configured builder used as the common base for all test cases. */
    private static TextStyle.Builder baseBuilder() {
        return TextStyle.builder()
                .setIndent(5)
                .setLeftPad(5)
                .setMinWidth(4)
                .setScalable(true);
    }

    /**
     * Provides (TextStyle, expectedWithoutIndent, expectedWithIndent) triples.
     * Each triple is self-contained: the builder is freshly configured per case.
     */
    static Stream<Arguments> padTestData() {
        return Stream.of(
                // Scenario 1 — UNSET_MAX_WIDTH: indent added as leading spaces; no trailing pad
                Arguments.of(
                        baseBuilder().setMaxWidth(TextStyle.UNSET_MAX_WIDTH).setAlignment(TextStyle.Alignment.LEFT).get(),
                        "Hello world",
                        "     Hello world"),

                Arguments.of(
                        baseBuilder().setMaxWidth(TextStyle.UNSET_MAX_WIDTH).setAlignment(TextStyle.Alignment.RIGHT).get(),
                        "Hello world",
                        "     Hello world"),

                Arguments.of(
                        baseBuilder().setMaxWidth(TextStyle.UNSET_MAX_WIDTH).setAlignment(TextStyle.Alignment.CENTER).get(),
                        "Hello world",
                        "  Hello world   "),

                // Scenario 2 — maxWidth(5) < text length(11): text returned unchanged for all alignments
                Arguments.of(
                        baseBuilder().setMaxWidth(5).setAlignment(TextStyle.Alignment.LEFT).get(),
                        "Hello world",
                        "Hello world"),

                Arguments.of(
                        baseBuilder().setMaxWidth(5).setAlignment(TextStyle.Alignment.RIGHT).get(),
                        "Hello world",
                        "Hello world"),

                Arguments.of(
                        baseBuilder().setMaxWidth(5).setAlignment(TextStyle.Alignment.CENTER).get(),
                        "Hello world",
                        "Hello world"),

                // Scenario 3 — maxWidth(20) > text length(11) + indent(5): indent is included and fits within maxWidth
                Arguments.of(
                        baseBuilder().setMaxWidth(20).setAlignment(TextStyle.Alignment.LEFT).get(),
                        "Hello world         ",
                        "     Hello world    "),

                Arguments.of(
                        baseBuilder().setMaxWidth(20).setAlignment(TextStyle.Alignment.RIGHT).get(),
                        "         Hello world",
                        "         Hello world"),

                Arguments.of(
                        baseBuilder().setMaxWidth(20).setAlignment(TextStyle.Alignment.CENTER).get(),
                        "    Hello world     ",
                        "    Hello world     "),

                // Scenario 4 — maxWidth(14) between text length(11) and text length + indent(16):
                // remaining width is filled with padding but the full indent cannot be applied
                Arguments.of(
                        baseBuilder().setMaxWidth(14).setAlignment(TextStyle.Alignment.LEFT).get(),
                        "Hello world   ",
                        "Hello world   "),

                Arguments.of(
                        baseBuilder().setMaxWidth(14).setAlignment(TextStyle.Alignment.RIGHT).get(),
                        "   Hello world",
                        "   Hello world"),

                Arguments.of(
                        baseBuilder().setMaxWidth(14).setAlignment(TextStyle.Alignment.CENTER).get(),
                        " Hello world  ",
                        " Hello world  ")
        );
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("padTestData")
    void testPad(final TextStyle underTest, final String unindentedString, final String indentedString) {
        assertEquals(unindentedString, underTest.pad(false, "Hello world"), "Unindented string test failed");
        assertEquals(indentedString, underTest.pad(true, "Hello world"), "Indented string test failed");
    }
}

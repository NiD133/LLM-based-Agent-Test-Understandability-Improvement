package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.apache.commons.cli.help.TextStyle.Alignment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link TextStyle#pad(boolean, CharSequence)}.
 *
 * <p>Every case pads the same input, {@value #TEXT}, twice:</p>
 * <ul>
 *   <li>{@code pad(false, ...)} ignores the indent, so its expected value is the "unindented" column.</li>
 *   <li>{@code pad(true, ...)}  honours the indent, so its expected value is the "indented" column.</li>
 * </ul>
 *
 * <p>All styles share the same base settings (indent = 5, leftPad = 5, minWidth = 4, scalable),
 * so each case only varies the {@code maxWidth} and the {@link Alignment}.</p>
 */
public class TextStyleTest_testPad {

    /** The string padded in every case (length 11). */
    private static final String TEXT = "Hello world";

    /** Shared indent applied when {@code pad(true, ...)} is used (see {@link #styleWith}). */
    private static final int INDENT = 5;

    /**
     * Builds a {@link TextStyle} that varies only by {@code maxWidth} and {@code alignment};
     * all other settings are held constant across the test cases.
     */
    private static TextStyle styleWith(final int maxWidth, final Alignment alignment) {
        return TextStyle.builder()
                .setIndent(INDENT)
                .setLeftPad(5)
                .setMinWidth(4)
                .setScalable(true)
                .setMaxWidth(maxWidth)
                .setAlignment(alignment)
                .get();
    }

    /**
     * Bundles one expectation: the style to test plus the expected padded output
     * for the unindented ({@code addIndent == false}) and indented ({@code addIndent == true}) calls.
     */
    private static Arguments padCase(final int maxWidth, final Alignment alignment,
            final String expectedUnindented, final String expectedIndented) {
        return Arguments.of(styleWith(maxWidth, alignment), expectedUnindented, expectedIndented);
    }

    static Stream<Arguments> padTestData() {
        return Stream.of(
                // maxWidth unset: padding only ever adds the indent (when requested), never trailing pad.
                padCase(TextStyle.UNSET_MAX_WIDTH, Alignment.LEFT, "Hello world", "     Hello world"),
                padCase(TextStyle.UNSET_MAX_WIDTH, Alignment.RIGHT, "Hello world", "     Hello world"),
                padCase(TextStyle.UNSET_MAX_WIDTH, Alignment.CENTER, "Hello world", "  Hello world   "),

                // maxWidth (5) <= text length (11): text is returned unchanged, regardless of alignment or indent.
                padCase(5, Alignment.LEFT, "Hello world", "Hello world"),
                padCase(5, Alignment.RIGHT, "Hello world", "Hello world"),
                padCase(5, Alignment.CENTER, "Hello world", "Hello world"),

                // maxWidth (20) >= text length + indent: full padding to maxWidth, with room for the indent.
                padCase(20, Alignment.LEFT, "Hello world         ", "     Hello world    "),
                padCase(20, Alignment.RIGHT, "         Hello world", "         Hello world"),
                padCase(20, Alignment.CENTER, "    Hello world     ", "    Hello world     "),

                // text length < maxWidth (14) < text length + indent: padded to maxWidth, but no room for the indent.
                padCase(14, Alignment.LEFT, "Hello world   ", "Hello world   "),
                padCase(14, Alignment.RIGHT, "   Hello world", "   Hello world"),
                padCase(14, Alignment.CENTER, " Hello world  ", " Hello world  "));
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("padTestData")
    void testPad(final TextStyle underTest, final String expectedUnindented, final String expectedIndented) {
        assertEquals(expectedUnindented, underTest.pad(false, TEXT), "Unindented string test failed");
        assertEquals(expectedIndented, underTest.pad(true, TEXT), "Indented string test failed");
    }
}

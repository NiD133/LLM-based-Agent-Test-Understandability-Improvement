package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TextStyleTest_testPad {

    private static final String TEXT = "Hello world";

    static Stream<Arguments> padTestData() {
        final List<Arguments> arguments = new ArrayList<>();
        final TextStyle.Builder builder = TextStyle.builder();
        builder.setIndent(5);
        builder.setLeftPad(5);
        builder.setMinWidth(4);
        builder.setScalable(true);

        // Undefined max width pads only by the configured indent.
        builder.setMaxWidth(TextStyle.UNSET_MAX_WIDTH);
        addPadCase(arguments, builder, TextStyle.Alignment.LEFT, "     Hello world");
        addPadCase(arguments, builder, TextStyle.Alignment.RIGHT, "     Hello world");
        addPadCase(arguments, builder, TextStyle.Alignment.CENTER, "  Hello world   ");

        // Max width shorter than the text leaves the text unchanged.
        builder.setMaxWidth(5);
        addPadCase(arguments, builder, TextStyle.Alignment.LEFT, "Hello world");
        addPadCase(arguments, builder, TextStyle.Alignment.RIGHT, "Hello world");
        addPadCase(arguments, builder, TextStyle.Alignment.CENTER, "Hello world");

        // Max width can fit the text and the full indent.
        builder.setMaxWidth(20);
        addPadCase(arguments, builder, TextStyle.Alignment.LEFT, "Hello world         ", "     Hello world    ");
        addPadCase(arguments, builder, TextStyle.Alignment.RIGHT, "         Hello world", "         Hello world");
        addPadCase(arguments, builder, TextStyle.Alignment.CENTER, "    Hello world     ", "    Hello world     ");

        // Max width can fit the text but not the full indent.
        builder.setMaxWidth(14);
        addPadCase(arguments, builder, TextStyle.Alignment.LEFT, "Hello world   ", "Hello world   ");
        addPadCase(arguments, builder, TextStyle.Alignment.RIGHT, "   Hello world", "   Hello world");
        addPadCase(arguments, builder, TextStyle.Alignment.CENTER, " Hello world  ", " Hello world  ");
        return arguments.stream();
    }

    private static void addPadCase(final List<Arguments> arguments, final TextStyle.Builder builder, final TextStyle.Alignment alignment,
            final String expectedWithIndent) {
        addPadCase(arguments, builder, alignment, TEXT, expectedWithIndent);
    }

    private static void addPadCase(final List<Arguments> arguments, final TextStyle.Builder builder, final TextStyle.Alignment alignment,
            final String expectedWithoutIndent, final String expectedWithIndent) {
        builder.setAlignment(alignment);
        arguments.add(Arguments.of(builder.get(), expectedWithoutIndent, expectedWithIndent));
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("padTestData")
    void testPad(final TextStyle underTest, final String unindentedString, final String indentedString) {
        assertEquals(unindentedString, underTest.pad(false, TEXT), "Unindented string test failed");
        assertEquals(indentedString, underTest.pad(true, TEXT), "Indented string test failed");
    }
}
